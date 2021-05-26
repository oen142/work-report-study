package biz.dreamaker.workreport.account.domain;

import biz.dreamaker.workreport.report.entity.Address;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Account account;
    @Enumerated(EnumType.STRING)
    private CompanyRole companyRole;

    private String companyNumber;

    private String bossName;

    @Embedded
    private Address address;

    private LocalDate openDate;

    private String companyBusiness;

    private String email;

    private String phoneNumber;

    private String faxNumber;


    @Builder(access = AccessLevel.PRIVATE)
    private Company(Long id, Account account, CompanyRole companyRole, String companyNumber, String bossName, Address address, LocalDate openDate, String companyBusiness, String email, String phoneNumber, String faxNumber) {
        this.id = id;
        this.account = account;
        this.companyRole = companyRole;
        this.companyNumber = companyNumber;
        this.bossName = bossName;
        this.address = address;
        this.openDate = openDate;
        this.companyBusiness = companyBusiness;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.faxNumber = faxNumber;
    }

    public static Company ofPersonal(Account account, String companyNumber, String bossName, Address address, LocalDate openDate,
                                     String companyBusiness, String email, String phoneNumber,
                                     String faxNumber) {

        account.updateCompany();

        return Company.builder()
                .account(account)
                .companyRole(CompanyRole.PERSONAL)
                .companyNumber(companyNumber)
                .bossName(bossName)
                .address(address)
                .openDate(openDate)
                .companyBusiness(companyBusiness)
                .email(email)
                .phoneNumber(phoneNumber)
                .faxNumber(faxNumber)
                .build();
    }

    public static Company ofGroup(Account account, String companyNumber, String bossName, Address address, LocalDate openDate,
                                  String companyBusiness, String email, String phoneNumber,
                                  String faxNumber) {

        account.updateCompany();

        return Company.builder()
                .account(account)
                .companyRole(CompanyRole.PERSONAL)
                .companyNumber(companyNumber)
                .bossName(bossName)
                .address(address)
                .openDate(openDate)
                .companyBusiness(companyBusiness)
                .email(email)
                .phoneNumber(phoneNumber)
                .faxNumber(faxNumber)
                .build();
    }

    public void update(CompanyRole companyRole, String companyNumber, String bossName, Address address, LocalDate openDate,
                       String companyBusiness, String email, String phoneNumber,
                       String faxNumber) {
        this.companyRole = companyRole;
        this.companyNumber = companyNumber;
        this.bossName = bossName;
        this.address = address;
        this.openDate = openDate;
        this.companyBusiness = companyBusiness;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.faxNumber = faxNumber;
    }
}
