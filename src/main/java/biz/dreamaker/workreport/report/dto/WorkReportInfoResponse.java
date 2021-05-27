package biz.dreamaker.workreport.report.dto;

import biz.dreamaker.workreport.report.application.WorkReportService;
import biz.dreamaker.workreport.report.entity.Address;
import biz.dreamaker.workreport.report.entity.Picture;
import biz.dreamaker.workreport.report.entity.WorkReport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkReportInfoResponse {

    private Long id;
    private LocalDate workedAt;
    private String companyName;
    private String workPlaceName;
    private String workerName;
    private String workerPhoneNumber;
    private String workDevice;
    private String workDeviceNumber;

    private LocalDateTime workStartDateTime;
    private LocalDateTime workEndDateTime;

    private Long workPay;
    private Long addedPay;
    private boolean checked;
    private LocalDate payedDate;
    private boolean payedStatus;
    private String gasStationName;
    private Long gasAmount;
    private Long gasPrice;

    private String representativeName;
    private String representativePhoneNumber;
    private String representativeCompanyNumber;
    private String representativeFaxNumber;

    private String dispatcherName;
    private String dispatcherPhoneNumber;

    private AddressResponse workAddress;
    private String memo;

    private List<String> pictures;
    private String signPicture;

    public static WorkReportInfoResponse ofNew(WorkReport workReport) {
        return WorkReportInfoResponse.builder()
                .id(workReport.getId())
                .workedAt(workReport.getWorkedAt())
                .companyName(workReport.getCompanyName())
                .workPlaceName(workReport.getWorkPlaceName())
                .workerName(workReport.getWorkerName())
                .workerPhoneNumber(workReport.getWorkerPhoneNumber())
                .workDevice(workReport.getWorkDevice())
                .workDeviceNumber(workReport.getWorkDeviceNumber())
                .workStartDateTime(workReport.getWorkStartDateTime())
                .workEndDateTime(workReport.getWorkEndDateTime())
                .workPay(workReport.getWorkPay())
                .addedPay(workReport.getAddedPay())
                .checked(workReport.isChecked())
                .payedDate(workReport.getPayedDate())
                .payedStatus(workReport.isPayedStatus())
                .gasStationName(workReport.getGasStationName())
                .gasAmount(workReport.getGasAmount())
                .gasPrice(workReport.getGasPrice())
                .representativeName(workReport.getRepresentativeName())
                .representativePhoneNumber(workReport.getRepresentativePhoneNumber())
                .representativeCompanyNumber(workReport.getRepresentativeCompanyNumber())
                .representativeFaxNumber(workReport.getRepresentativeFaxNumber())
                .dispatcherName(workReport.getDispatcherName())
                .dispatcherPhoneNumber(workReport.getDispatcherPhoneNumber())
                .workAddress(AddressResponse.of(workReport.getWorkAddress()))
                .memo(workReport.getMemo())
                .pictures(workReport.getPictures().stream()
                        .map(Picture::getHref)
                        .collect(Collectors.toList()))
                .signPicture(workReport.getPicture())
                .build();
    }
}
