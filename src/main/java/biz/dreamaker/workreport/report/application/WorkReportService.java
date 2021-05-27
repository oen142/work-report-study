package biz.dreamaker.workreport.report.application;

import biz.dreamaker.workreport.account.domain.Account;
import biz.dreamaker.workreport.account.repository.AccountRepository;
import biz.dreamaker.workreport.email.application.EmailService;
import biz.dreamaker.workreport.pdf.application.PdfGenerator;
import biz.dreamaker.workreport.pdf.application.PdfService;
import biz.dreamaker.workreport.pdf.application.ThymeleafParser;
import biz.dreamaker.workreport.report.dto.WorkReportInfoRequest;
import biz.dreamaker.workreport.report.dto.WorkReportInfoResponse;
import biz.dreamaker.workreport.report.entity.Picture;
import biz.dreamaker.workreport.report.entity.WorkReport;
import biz.dreamaker.workreport.report.repository.WorkReportRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkReportService {

    private final WorkReportRepository workReportRepository;
    private final AccountRepository accountRepository;
    private final EmailService emailService;
    private final PdfService pdfService;

    public WorkReportService(WorkReportRepository workReportRepository, AccountRepository accountRepository, EmailService emailService, PdfService pdfService) {
        this.workReportRepository = workReportRepository;
        this.accountRepository = accountRepository;
        this.emailService = emailService;
        this.pdfService = pdfService;
    }

    public WorkReportInfoResponse enrollWorkReport(String username, WorkReportInfoRequest request, List<String> uploadedFiles) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("해당 유저네임을 가진 아이디를 찾을 수 없습니다."));


        List<Picture> pictures = uploadedFiles.stream()
                .map(file -> Picture.ofNew(file))
                .collect(Collectors.toList());
        WorkReport workReport = request.toWorkReport(account, pictures);
        workReportRepository.save(workReport);
        return WorkReportInfoResponse.ofNew(workReport);

    }

    @Async
    public WorkReportInfoResponse signWorkReport(Long id, String file, String dispatcherEmail) throws IOException {
        WorkReport workReport = workReportRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 유저네임을 가진 아이디를 찾을 수 없습니다."));

        if (workReport.isChecked()) {
            throw new IllegalArgumentException("해당 문서는 이미 사인되었습니다.");
        }
        String pdfName = sendPdf(workReport, file);
        workReport.sign(file, dispatcherEmail, pdfName);

        emailService.sendSimpleMessageUsingTemplate(workReport.getDispatcherEmail(), workReport.getWorkerName() + "님의 " + LocalDate.now() + " 작업일보입니다.",
                workReport.getDispatcherName(), workReport.getWorkerName(), workReport.getWorkedAt() + "", pdfName);

        return WorkReportInfoResponse.ofNew(workReport);
    }

    private String sendPdf(WorkReport workReport, String imageUrl) throws IOException {
        Map<String, Object> name = new HashMap<>();
        name.put("companyName", workReport.getCompanyName());
        name.put("workPlaceName", workReport.getWorkPlaceName());
        name.put("workerName", workReport.getWorkerName());
        name.put("workerPhoneNumber", workReport.getWorkerPhoneNumber());
        name.put("workDevice", workReport.getWorkDevice());
        name.put("workDeviceNumber", workReport.getWorkDeviceNumber());
        name.put("workStartDateTime", workReport.getWorkStartDateTime());
        name.put("workEndDateTime", workReport.getWorkEndDateTime());
        name.put("workPay", workReport.getWorkPay());
        name.put("workAddedPay", workReport.getAddedPay());
        name.put("payedStatus", workReport.isPayedStatus() ? "지급됨" : "미지급");
        name.put("payedDate", workReport.getPayedDate());
        name.put("gasStationName", workReport.getGasStationName());
        name.put("gasAmount", workReport.getGasAmount());
        name.put("gasPrice", workReport.getGasPrice());
        name.put("representativeName", workReport.getRepresentativeName());
        name.put("representativePhoneNumber", workReport.getRepresentativePhoneNumber());
        name.put("representativeCompanyNumber", workReport.getRepresentativeCompanyNumber());
        name.put("representativeFaxNumber", workReport.getRepresentativeFaxNumber());
        name.put("dispatcherName", workReport.getDispatcherName());
        name.put("dispatcherPhoneNumber", workReport.getDispatcherPhoneNumber());
        name.put("workAddress", workReport.getWorkAddress().toStringAddress());
        name.put("memo", workReport.getMemo());
        name.put("signImage", imageUrl);

        String html = ThymeleafParser.parseHtmlFileToString("work", name);
        String pdfName = UUID.randomUUID() + ".pdf";
        PdfGenerator.generateFromHtml("./pdf/", pdfName, html, imageUrl);
        return pdfName;
    }

    private String generateMessage(WorkReport workReport) {
        return String.format("회사이름 : %s\n" +
                        " \n" +
                        "작업장 : %s\n" +
                        "\n" +
                        "작업자이름 : %s\n" +
                        "\n" +
                        "작업자 핸드폰 번호 : %s\n" +
                        "\n" +
                        "작업자 기기 : %s\n" +
                        "\n" +
                        "작업기기 일련번호 : %s\n" +
                        "\n" +
                        "작업 시작 날짜 : %s\n" +
                        "\n" +
                        "작업 종료 날짜 : %s\n" +
                        "\n" +
                        "작업 비용 : %s 원\n" +
                        "\n" +
                        "추가 비용 : %s 원\n" +
                        "\n" +
                        "입금 여부 : %b\n" +
                        "\n" +
                        "지급 날짜 : %s\n" +
                        "\n" +
                        "주유소 : %s\n" +
                        "\n" +
                        "주유 리터 : %s L\n" +
                        "\n" +
                        "담당자 이름 : %s\n" +
                        "\n" +
                        "담당자 번호 : %s\n" +
                        "\n" +
                        "담당자 회사 번호 : %s\n" +
                        "\n" +
                        "담당자 팩스 : %s\n" +
                        "\n" +
                        "배차자 이름 : %s\n" +
                        "\n" +
                        "배차자 핸드폰 번호 : %s\n" +
                        "\n" +
                        "작업 주소 : %s\n" +
                        "\n" +
                        "메모 : %s", workReport.getCompanyName(),
                workReport.getWorkPlaceName(),
                workReport.getWorkerName(),
                workReport.getWorkerPhoneNumber(),
                workReport.getWorkDevice(),
                workReport.getWorkDeviceNumber(),
                workReport.getWorkStartDateTime().toString(),
                workReport.getWorkEndDateTime().toString(),
                workReport.getWorkPay().toString(),
                workReport.getAddedPay().toString(),
                workReport.isPayedStatus() ? "지불" : "미지불",
                workReport.getPayedDate().toString(),
                workReport.getGasStationName(),
                workReport.getGasAmount(),
                workReport.getRepresentativeName(),
                workReport.getRepresentativePhoneNumber(),
                workReport.getRepresentativeCompanyNumber(),
                workReport.getRepresentativeFaxNumber(),
                workReport.getDispatcherName(),
                workReport.getDispatcherPhoneNumber(),
                workReport.getWorkAddress().toStringAddress(),
                workReport.getMemo());
    }

    public List<WorkReportInfoResponse> findAllByAccountId(Long accountId) {
        List<WorkReport> workReports = workReportRepository.findAllByAccountId(accountId);
        return workReports.stream()
                .map(WorkReportInfoResponse::ofNew)
                .collect(Collectors.toList());
    }

    public WorkReportInfoResponse findById(Long workReportId) {
        return WorkReportInfoResponse.ofNew(workReportRepository.findById(workReportId)
                .orElseThrow(() -> new NoSuchElementException("해당 유저네임을 가진 아이디를 찾을 수 없습니다.")));
    }

    public WorkReportInfoResponse updateWorkReport(Long id, WorkReportInfoRequest request, List<String> uploadedFiles) {
        WorkReport workReport = workReportRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 작업일보를 찾을 수 없습니다."));


        List<Picture> pictures = uploadedFiles.stream()
                .map(Picture::ofNew)
                .collect(Collectors.toList());
        workReport.update(request.getWorkedAt(),
                request.getCompanyName(),
                request.getWorkPlaceName(),
                request.getWorkerName(),
                request.getWorkerPhoneNumber(),
                request.getWorkDevice(),
                request.getWorkDeviceNumber(),
                request.getWorkStartDateTime(),
                request.getWorkEndDateTime(),
                request.getWorkPay(),
                request.getAddedPay(),
                request.isPayedStatus(),
                request.getPayedDate(),
                request.getGasStationName(),
                request.getGasAmount(),
                request.getGasPrice(),
                request.getRepresentativeName(),
                request.getRepresentativePhoneNumber(),
                request.getRepresentativeCompanyNumber(),
                request.getRepresentativeFaxNumber(),
                request.getDispatcherName(),
                request.getDispatcherPhoneNumber(),
                request.getWorkAddress().toAddress(),
                request.getMemo(), pictures);
        workReportRepository.save(workReport);
        return WorkReportInfoResponse.ofNew(workReport);
    }
}
