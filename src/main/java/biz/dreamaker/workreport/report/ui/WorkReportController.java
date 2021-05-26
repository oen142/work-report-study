package biz.dreamaker.workreport.report.ui;

import biz.dreamaker.workreport.report.application.WorkReportService;
import biz.dreamaker.workreport.report.dto.WorkReportInfoRequest;
import biz.dreamaker.workreport.report.dto.WorkReportInfoResponse;
import biz.dreamaker.workreport.security.dto.ParseAuthenticationToAccount;
import biz.dreamaker.workreport.storage.FileUploadController;
import biz.dreamaker.workreport.storage.StorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@RestController
public class WorkReportController {

    private final WorkReportService workReportService;
    private final StorageService storageService;

    public WorkReportController(WorkReportService workReportService, StorageService storageService) {
        this.workReportService = workReportService;
        this.storageService = storageService;
    }

    @GetMapping("/api/work-report/account/{id}")
    public ResponseEntity<List<WorkReportInfoResponse>> findWorkReports(
            @PathVariable("id") Long accountId
    ) {
        List<WorkReportInfoResponse> responses = workReportService.findAllByAccountId(accountId);
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/api/work-report/id}")
    public ResponseEntity<WorkReportInfoResponse> findWorkReport(@PathVariable("id") Long workReportId) {
        WorkReportInfoResponse response = workReportService.findById(workReportId);
        return ResponseEntity.ok().body(response);
    }


    @PostMapping("/api/work-report")
    public ResponseEntity<WorkReportInfoResponse> createWorkReport(
            WorkReportInfoRequest request,
            Authentication authentication
    ) {
        String username = ParseAuthenticationToAccount.getLoginAccountUsername(authentication);

        List<String> uploadedFiles = new ArrayList<>();

        request.getFiles().forEach(f -> {
            String storeHref = storageService.store(f);
            Path path = storageService.load(storeHref);

            uploadedFiles.add(MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                    "serveFile", path.getFileName().toString())
                    .build()
                    .toUri()
                    .toString());
        });

        WorkReportInfoResponse response = workReportService.enrollWorkReport(username, request, uploadedFiles);

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/api/work-report/{id}/sign")
    public ResponseEntity<WorkReportInfoResponse> signWorkReport(
            @PathVariable Long id,
            @RequestParam MultipartFile file
    ) {
        String storeHref = storageService.store(file);
        Path path = storageService.load(storeHref);

        String serveFile = MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                "serveFile", path.getFileName().toString())
                .build()
                .toUri()
                .toString();

        WorkReportInfoResponse response = workReportService.signWorkReport(id, serveFile);

        return ResponseEntity.ok().body(response);
    }


}
