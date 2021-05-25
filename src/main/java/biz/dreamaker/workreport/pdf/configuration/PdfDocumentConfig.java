package biz.dreamaker.workreport.pdf.configuration;

import com.lowagie.text.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PdfDocumentConfig {

    @Bean
    public Document document(){
        return new Document();
    }

}
