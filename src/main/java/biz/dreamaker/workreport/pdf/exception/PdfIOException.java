package biz.dreamaker.workreport.pdf.exception;

import java.io.FileNotFoundException;
import java.io.IOException;

public class PdfIOException extends IOException {

    public PdfIOException() {
    }

    public PdfIOException(String message) {
        super(message);
    }
}
