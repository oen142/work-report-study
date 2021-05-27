package biz.dreamaker.workreport.pdf.application;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;

import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.font.FontProvider;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PdfGenerator {

    public static String generateFromHtml(String filePath, String fileName, String html, String signImageUrl) throws IOException {
        String savedFilePath = filePath + "/" + fileName;
        try (FileOutputStream stream = new FileOutputStream(savedFilePath)) {
//ConverterProperties : htmlconverter의 property를 지정하는 메소드인듯
            ConverterProperties properties = new ConverterProperties();
            FontProvider fontProvider = new DefaultFontProvider(false, false, false);
            FontProgram fontProgram = FontProgramFactory.createFont(new ClassPathResource("/static/font/NanumBarunGothic.ttf").getURL().toString());
            fontProvider.addFont(fontProgram);
            properties.setFontProvider(fontProvider);


            //pdf 페이지 크기를 조정

            HtmlConverter.convertToPdf(html, stream, properties);
            System.out.println("PDF Created!");
        } catch (IOException e) {
        }
        return savedFilePath;
    }
}
