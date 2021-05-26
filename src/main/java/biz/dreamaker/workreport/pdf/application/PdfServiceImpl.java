package biz.dreamaker.workreport.pdf.application;

import biz.dreamaker.workreport.pdf.exception.PdfIOException;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.FontSelector;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class PdfServiceImpl implements PdfService {

    @Override
    public void generatePdf(String contents, String... imageUrls) throws IOException {
        Document document = new Document();
        OutputStream outputStream = new FileOutputStream(
                "./pdf/" + UUID.randomUUID() + ".pdf");
        try {
            PdfWriter instance = PdfWriter
                    .getInstance(document, outputStream);
            document.open();
            ClassPathResource resource = new ClassPathResource("/static/font/NanumBarunGothic.TTF");
            Font nanumBarunGothic = FontFactory.getFont(resource.getURL().toString(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12);
            FontSelector sel = new FontSelector();
            sel.addFont(new Font(Font.TIMES_ROMAN, 12));
            sel.addFont(new Font(Font.ZAPFDINGBATS, 12));
            sel.addFont(new Font(Font.SYMBOL, 12));
            Phrase ph = sel.process(contents);
            Font font24B = FontFactory.getFont(FontFactory.TIMES_ROMAN, 24, Font.BOLD);
            Font font10B = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD);
            document.add(new Paragraph("테스트입니다.test", nanumBarunGothic));
            document.add(new Paragraph(contents));

            for (String imageUrl : imageUrls) {
                Image jpg = Image.getInstance(imageUrl);
                document.add(jpg);
            }


        } catch (DocumentException | IOException de) {
            System.err.println(de.getMessage());
        } finally {
            document.close();
            outputStream.close();
        }

    }


    public String SimpleMailMessage(String... strings) {

        return String.format("\n" +
                "회사이름 : %s\n" +
                " \n" +
                "작업장 : %s\n" +
                "\n" +
                "작업자이름 : %s\n" +
                "\n" +
                "작업자 핸드폰 번호 : %s\n" +
                "\n" +
                "작업자 기기 : %s\n" +
                "\n" +
                "작업기기 일련번호 : %s\n" +
                "\n" +
                "작업 시작 날짜 : %s\n" +
                "\n" +
                "작업 종료 날짜 : %s\n" +
                "\n" +
                "작업 비용 : %s 원\n" +
                "\n" +
                "추가 비용 : %s 원\n" +
                "\n" +
                "입금 여부 : %s\n" +
                "\n" +
                "지급 날짜 : %s\n" +
                "\n" +
                "주유소 : %s\n" +
                "\n" +
                "주유 리터 : %s L\n" +
                "\n" +
                "담당자 이름 : %s\n" +
                "\n" +
                "담당자 번호 : %s\n" +
                "\n" +
                "담당자 회사 번호 : %s\n" +
                "\n" +
                "담당자 팩스 : %s\n" +
                "\n" +
                "배차자 이름 : %s\n" +
                "\n" +
                "배차자 핸드폰 번호 : %s\n" +
                "\n" +
                "작업 주소 : %s\n" +
                "\n" +
                "메모 : %s", strings);
    }
}
