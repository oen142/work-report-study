package biz.dreamaker.workreport.pdf.application;

import biz.dreamaker.workreport.pdf.exception.PdfIOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface PdfService {

    void generatePdf(String imageUrl) throws IOException;

}
