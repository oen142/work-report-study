package biz.dreamaker.workreport.report.ui;

import biz.dreamaker.workreport.report.application.WorkReportService;
import biz.dreamaker.workreport.report.dto.WorkReportInfoRequest;
import biz.dreamaker.workreport.report.dto.WorkReportInfoResponse;
import biz.dreamaker.workreport.security.dto.ParseAuthenticationToAccount;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkReportController {

    private final WorkReportService workReportService;

    public WorkReportController(WorkReportService workReportService) {
        this.workReportService = workReportService;
    }

    @PostMapping("/api/work-report")
    public ResponseEntity<WorkReportInfoResponse> createWorkReport(
            WorkReportInfoRequest request,
            Authentication authentication
    ) {
        String username = ParseAuthenticationToAccount.getLoginAccountUsername(authentication);
        WorkReportInfoResponse response = workReportService.enrollWorkReport(username, request);
        return ResponseEntity.ok().body(response);
    }



}
