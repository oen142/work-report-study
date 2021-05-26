package biz.dreamaker.workreport.report.application;

import biz.dreamaker.workreport.account.repository.AccountRepository;
import biz.dreamaker.workreport.report.dto.WorkReportInfoRequest;
import biz.dreamaker.workreport.report.dto.WorkReportInfoResponse;
import biz.dreamaker.workreport.report.repository.WorkReportRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

        return null;

    }
}
