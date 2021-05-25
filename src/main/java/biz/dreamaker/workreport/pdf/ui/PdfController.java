package biz.dreamaker.workreport.pdf.ui;

import biz.dreamaker.workreport.pdf.application.PdfFileService;
import biz.dreamaker.workreport.pdf.application.PdfService;
import biz.dreamaker.workreport.pdf.exception.PdfIOException;
import biz.dreamaker.workreport.storage.FileUploadController;
import biz.dreamaker.workreport.storage.StorageService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@RestController
public class PdfController {

    private final PdfService pdfService;
    private final PdfFileService pdfFileService;
    private final StorageService storageService;

    public PdfController(PdfService pdfService,
        PdfFileService pdfFileService,
        StorageService storageService) {
        this.pdfService = pdfService;
        this.pdfFileService = pdfFileService;
        this.storageService = storageService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_SUPER')")
    @PostMapping("/api/pdf")
    public ResponseEntity<String> createMail(
        @RequestParam("file") List<MultipartFile> files
    ) throws IOException {
        List<String> uploadedFiles = new ArrayList<>();
        files.forEach(f -> {
            String storeHref = storageService.store(f);
            Path path = storageService.load(storeHref);

            uploadedFiles.add(MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                "serveFile", path.getFileName().toString())
                .build()
                .toUri()
                .toString());
        });
        System.out.println("uploadedFiles = " + uploadedFiles.get(0));
        pdfService.generatePdf(uploadedFiles.get(0));

        return ResponseEntity.ok().body("success");
    }

    @GetMapping("/file")
    public ResponseEntity<List<String>> listUploadedFiles(Model model) throws IOException {

        List<String> serveFile = pdfFileService.loadAll().map(
            path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                "serveFile", path.getFileName().toString())
                .build()
                .toUri()
                .toString())
            .collect(Collectors.toList());

        return ResponseEntity.ok().body(serveFile);
    }

    @GetMapping(value = "/filess/{fileName}")
    public ResponseEntity<InputStreamResource> getTermsConditions(@PathVariable String fileName)
        throws FileNotFoundException {

        String filePath = "./pdf/";
        System.out.println("fileName = " + fileName);
        File file = new File(filePath + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-disposition", "inline;filename=" + fileName);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
            .headers(headers)
            .contentLength(file.length())
            .contentType(MediaType.parseMediaType("application/pdf"))
            .body(resource);
    }
}
