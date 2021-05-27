package biz.dreamaker.workreport.report.application;

import biz.dreamaker.workreport.account.domain.Account;
import biz.dreamaker.workreport.account.repository.AccountRepository;
import biz.dreamaker.workreport.email.application.EmailService;
import biz.dreamaker.workreport.pdf.application.PdfService;
import biz.dreamaker.workreport.report.dto.WorkReportInfoRequest;
import biz.dreamaker.workreport.report.dto.WorkReportInfoResponse;
import biz.dreamaker.workreport.report.entity.Picture;
import biz.dreamaker.workreport.report.entity.WorkReport;
import biz.dreamaker.workreport.report.repository.WorkReportRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
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

    public WorkReportInfoResponse signWorkReport(Long id, String file, String dispatcherEmail) throws IOException {
        WorkReport workReport = workReportRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 유저네임을 가진 아이디를 찾을 수 없습니다."));


        String pdfLocation = pdfService.generatePdf(generateMessage(workReport), file);

        workReport.sign(file, dispatcherEmail , pdfLocation);

        emailService.sendSimpleMessageUsingTemplate(workReport.getDispatcherEmail(), LocalDate.now() + "날짜 작업일보입니다.",
                workReport.getDispatcherName(), workReport.getWorkerName(), workReport.getWorkedAt() + "");

        return WorkReportInfoResponse.ofNew(workReport);
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
        return null;
    }

    public WorkReportInfoResponse findById(Long workReportId) {
        return WorkReportInfoResponse.ofNew(workReportRepository.findById(workReportId)
                .orElseThrow(() -> new NoSuchElementException("해당 유저네임을 가진 아이디를 찾을 수 없습니다.")));
    }
}
