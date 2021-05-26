package biz.dreamaker.workreport.report.application;

import biz.dreamaker.workreport.account.domain.Account;
import biz.dreamaker.workreport.account.repository.AccountRepository;
import biz.dreamaker.workreport.report.dto.WorkReportInfoRequest;
import biz.dreamaker.workreport.report.dto.WorkReportInfoResponse;
import biz.dreamaker.workreport.report.entity.WorkReport;
import biz.dreamaker.workreport.report.repository.WorkReportRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@Transactional
public class WorkReportService {

    private final WorkReportRepository workReportRepository;
    private final AccountRepository accountRepository;

    public WorkReportService(WorkReportRepository workReportRepository, AccountRepository accountRepository) {
        this.workReportRepository = workReportRepository;
        this.accountRepository = accountRepository;
    }

    public WorkReportInfoResponse enrollWorkReport(String username, WorkReportInfoRequest request) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("해당 유저네임을 가진 아이디를 찾을 수 없습니다."));

        WorkReport workReport = request.toWorkReport(account);
        workReportRepository.save(workReport);
        return WorkReportInfoResponse.ofNew(workReport);

    }
}
