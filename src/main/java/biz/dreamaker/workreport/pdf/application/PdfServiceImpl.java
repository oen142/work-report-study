package biz.dreamaker.workreport.pdf.application;

import biz.dreamaker.workreport.pdf.exception.PdfIOException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PdfServiceImpl implements PdfService {

    @Override
    public void generatePdf(String imageUrl) throws IOException {
        Document document = new Document();
        OutputStream outputStream = new FileOutputStream(
            "./pdf/" + UUID.randomUUID() + ".pdf");
        try {
            PdfWriter instance = PdfWriter
                .getInstance(document, outputStream);
            document.open();
            document.add(new Paragraph("test Picture"));

            Image jpg = Image.getInstance(imageUrl);
            document.add(jpg);

        } catch (DocumentException | IOException de) {
            System.err.println(de.getMessage());
        } finally {
            document.close();
            outputStream.close();
        }

    }
}
