package biz.dreamaker.workreport.report.dto;

import biz.dreamaker.workreport.account.domain.Account;
import biz.dreamaker.workreport.report.entity.Picture;
import biz.dreamaker.workreport.report.entity.WorkReport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class WorkReportInfoRequest {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate workedAt;
    private String companyName;
    private String workPlaceName;
    private String workerName;
    private String workerPhoneNumber;
    private String workDevice;
    private String workDeviceNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime workStartDateTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime workEndDateTime;
    private Long workPay;
    private Long addedPay;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate payedDate;
    private boolean payedStatus;

    private String gasStationName;
    private Long gasAmount;

    private String representativeName;
    private String representativePhoneNumber;
    private String representativeCompanyNumber;
    private String representativeFaxNumber;

    private String dispatcherName;
    private String dispatcherPhoneNumber;

    private AddressRequest workAddress;
    private String memo;
    private List<MultipartFile> files;

    public WorkReport toWorkReport(Account account, List<Picture> pictures) {
        return WorkReport.ofNew(workedAt, companyName, workPlaceName, workerName, workerPhoneNumber, workDevice, workDeviceNumber,
                workStartDateTime, workEndDateTime, workPay, addedPay, payedDate, payedStatus, gasStationName, gasAmount,
                representativeName, representativePhoneNumber, representativeCompanyNumber, representativeFaxNumber, dispatcherName, dispatcherPhoneNumber, workAddress.toAddress(), memo, account, pictures);
    }
}
