package biz.dreamaker.workreport;

import biz.dreamaker.workreport.pdf.application.PdfGenerator;
import biz.dreamaker.workreport.pdf.application.ThymeleafParser;
import biz.dreamaker.workreport.report.entity.WorkReport;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PdfGeneratorTest {

    @Test
    void name() throws IOException {
        Map<String, Object> name = new HashMap<>();

/*
        WorkReport workReport = new WorkReport();
        name.put("companyName", workReport.getCompanyName());
        name.put("workPlaceName", workReport.getWorkPlaceName());
        name.put("workerName", workReport.getWorkerName());
        name.put("workerPhoneNumber", workReport.getWorkerPhoneNumber());
        name.put("workDevice", workReport.getWorkDevice());
        name.put("workDeviceNumber", workReport.getWorkDeviceNumber());
        name.put("workStartDateTime", workReport.getWorkStartDateTime());
        name.put("workEndDateTime", workReport.getWorkEndDateTime());
        name.put("workPay", workReport.getWorkPay());
        name.put("workAddedPay", workReport.getAddedPay());
        name.put("payedStatus", workReport.isPayedStatus() ? "지급됨" : "미지급");
        name.put("payedDate", workReport.getPayedDate());
        name.put("gasStationName", workReport.getGasStationName());
        name.put("gasAmount", workReport.getGasAmount());
        name.put("gasPrice", workReport.getGasPrice());
        name.put("representativeName", workReport.getRepresentativeName());
        name.put("representativePhoneNumber", workReport.getRepresentativePhoneNumber());
        name.put("representativeCompanyNumber", workReport.getRepresentativeCompanyNumber());
        name.put("representativeFaxNumber", workReport.getRepresentativeFaxNumber());
        name.put("dispatcherName", workReport.getDispatcherName());
        name.put("dispatcherPhoneNumber", workReport.getDispatcherPhoneNumber());
        name.put("workAddress", workReport.getWorkAddress().toStringAddress());
        name.put("memo", workReport.getMemo());

*/
        String html = ThymeleafParser.parseHtmlFileToString("work", name);
        PdfGenerator.generateFromHtml("./pdf/", UUID.randomUUID().toString(), html, "./pdf/cutty_peng.png");
    }
}
