package biz.dreamaker.workreport.report.entity;

import biz.dreamaker.workreport.account.domain.Account;
import biz.dreamaker.workreport.common.domain.BasicEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class WorkReport extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_report_id")
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

    private String gasStationName;

    private Long gasAmount;


    private String representativeName;

    private String representativePhoneNumber;
    private String representativeCompanyNumber;
    private String representativeFaxNumber;

    private String dispatcherName;
    private String dispatcherPhoneNumber;

    @Embedded
    private Address workAddress;

    private String memo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Account account;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Picture> pictures = new ArrayList<>();

    private String picture;

    @Builder(access = AccessLevel.PRIVATE)
    private WorkReport(Long id, LocalDate workedAt, String companyName, String workPlaceName, String workerName, String workerPhoneNumber, String workDevice, String workDeviceNumber, LocalDateTime workStartDateTime, LocalDateTime workEndDateTime, Long workPay, Long addedPay, boolean checked, LocalDate payedDate, String gasStationName, Long gasAmount, String representativeName, String representativePhoneNumber, String representativeCompanyNumber, String representativeFaxNumber, String dispatcherName, String dispatcherPhoneNumber, Address workAddress, String memo, Account account
            , List<Picture> pictures, String picture) {
        this.id = id;
        this.workedAt = workedAt;
        this.companyName = companyName;
        this.workPlaceName = workPlaceName;
        this.workerName = workerName;
        this.workerPhoneNumber = workerPhoneNumber;
        this.workDevice = workDevice;
        this.workDeviceNumber = workDeviceNumber;
        this.workStartDateTime = workStartDateTime;
        this.workEndDateTime = workEndDateTime;
        this.workPay = workPay;
        this.addedPay = addedPay;
        this.checked = checked;
        this.payedDate = payedDate;
        this.gasStationName = gasStationName;
        this.gasAmount = gasAmount;
        this.representativeName = representativeName;
        this.representativePhoneNumber = representativePhoneNumber;
        this.representativeCompanyNumber = representativeCompanyNumber;
        this.representativeFaxNumber = representativeFaxNumber;
        this.dispatcherName = dispatcherName;
        this.dispatcherPhoneNumber = dispatcherPhoneNumber;
        this.workAddress = workAddress;
        this.memo = memo;
        this.account = account;
        this.pictures.addAll(pictures);
        this.picture = picture;
    }

    public static WorkReport ofNew(LocalDate workedAt, String companyName, String workPlaceName, String workerName,
                                   String workerPhoneNumber, String workDevice, String workDeviceNumber,
                                   LocalDateTime workStartDateTime, LocalDateTime workEndDateTime, Long workPay,
                                   Long addedPay, boolean checked, LocalDate payedDate, String gasStationName,
                                   Long gasAmount, String representativeName, String representativePhoneNumber,
                                   String representativeCompanyNumber, String representativeFaxNumber, String dispatcherName,
                                   String dispatcherPhoneNumber, Address workAddress, String memo, Account account, List<Picture> pictures) {
        return WorkReport.builder()
                .id(null)
                .workedAt(workedAt)
                .companyName(companyName)
                .workPlaceName(workPlaceName)
                .workerName(workerName)
                .workerPhoneNumber(workerPhoneNumber)
                .workDevice(workDevice)
                .workDeviceNumber(workDeviceNumber)
                .workStartDateTime(workStartDateTime)
                .workEndDateTime(workEndDateTime)
                .workPay(workPay)
                .addedPay(addedPay)
                .checked(checked)
                .payedDate(payedDate)
                .gasStationName(gasStationName)
                .gasAmount(gasAmount)
                .representativeName(representativeName)
                .representativePhoneNumber(representativePhoneNumber)
                .representativeCompanyNumber(representativeCompanyNumber)
                .representativeFaxNumber(representativeFaxNumber)
                .dispatcherName(dispatcherName)
                .dispatcherPhoneNumber(dispatcherPhoneNumber)
                .workAddress(workAddress)
                .memo(memo)
                .account(account)
                .pictures(pictures)
                .picture(null)
                .build();
    }

    public void sign(String file) {
        this.checked = true;
        this.picture = file;
    }
}

