package biz.dreamaker.workreport.pdf.application;

import biz.dreamaker.workreport.pdf.exception.PdfIOException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface PdfService {

    String generatePdf(String contents, String... imageUrls) throws IOException;

}
