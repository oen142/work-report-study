package biz.dreamaker.workreport.report.application;

import biz.dreamaker.workreport.account.domain.Account;
import biz.dreamaker.workreport.account.repository.AccountRepository;
import biz.dreamaker.workreport.report.dto.WorkReportInfoRequest;
import biz.dreamaker.workreport.report.dto.WorkReportInfoResponse;
import biz.dreamaker.workreport.report.entity.Picture;
import biz.dreamaker.workreport.report.entity.WorkReport;
import biz.dreamaker.workreport.report.repository.WorkReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkReportService {

    private final WorkReportRepository workReportRepository;
    private final AccountRepository accountRepository;

    public WorkReportService(WorkReportRepository workReportRepository, AccountRepository accountRepository) {
        this.workReportRepository = workReportRepository;
        this.accountRepository = accountRepository;
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

    public WorkReportInfoResponse signWorkReport(Long id, String file) {
        WorkReport workReport = workReportRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 유저네임을 가진 아이디를 찾을 수 없습니다."));

        workReport.sign(file);
        return WorkReportInfoResponse.ofNew(workReport);
    }

    public List<WorkReportInfoResponse> findAllByAccountId(Long accountId) {
        return null;
    }

    public WorkReportInfoResponse findById(Long workReportId) {
        return WorkReportInfoResponse.ofNew(workReportRepository.findById(workReportId)
                .orElseThrow(() -> new NoSuchElementException("해당 유저네임을 가진 아이디를 찾을 수 없습니다.")));
    }
}
