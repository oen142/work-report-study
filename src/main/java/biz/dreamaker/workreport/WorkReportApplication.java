package biz.dreamaker.workreport;

import biz.dreamaker.workreport.account.domain.Account;
import biz.dreamaker.workreport.account.repository.AccountRepository;
import biz.dreamaker.workreport.pdf.application.PdfFileService;
import biz.dreamaker.workreport.storage.StorageService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@EnableAsync
public class WorkReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkReportApplication.class, args);
    }

    @Bean
    @Transactional
    CommandLineRunner bootstrapTestAccount(AccountRepository accountRepository,
                                           PasswordEncoder passwordEncoder) {

        return args -> {
            Account superAdmin = Account
                    .ofSuper("super", passwordEncoder.encode("admin"), "010-0000-0000");
            Account admin = Account
                    .ofAdmin("admin", passwordEncoder.encode("admin"), "010-0000-0000");
            Account admin1 = Account
                    .ofAdmin("admin1", passwordEncoder.encode("admin"), "010-0000-0000");

            accountRepository.save(superAdmin);
            accountRepository.save(admin);
            accountRepository.save(admin1);
        };
    }

    @Bean
    CommandLineRunner init(StorageService storageService, PdfFileService pdfFileService) {
        return (args) -> {
            storageService.init();
            pdfFileService.init();
        };
    }
}
