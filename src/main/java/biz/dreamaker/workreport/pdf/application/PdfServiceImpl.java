package biz.dreamaker.workreport.pdf.application;

import biz.dreamaker.workreport.pdf.exception.PdfIOException;

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
    public String generatePdf(String contents, String... imageUrls) throws IOException {
       return null;

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
