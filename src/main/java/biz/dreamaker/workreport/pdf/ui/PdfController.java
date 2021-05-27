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
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/api/pdf")
    public ResponseEntity<String> createMail(
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        String storeHref = storageService.store(file);
        Path path = storageService.load(storeHref);

        String serveFile = MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                "serveFile", path.getFileName().toString())
                .build()
                .toUri()
                .toString();
        pdfService.generatePdf("콘텐츠입니다.", serveFile);

        return ResponseEntity.ok().body("success");
    }

    @GetMapping("/file")
    public ResponseEntity<List<String>> listUploadedFiles(Model model) throws IOException {

        List<String> serveFile = pdfFileService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "servePdf", path.getFileName().toString())
                        .build()
                        .toUri()
                        .toString())
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(serveFile);
    }

    @GetMapping("/pdfs/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> servePdf(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"")
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(file);
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
