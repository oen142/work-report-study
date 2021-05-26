package biz.dreamaker.workreport.account.dto;

import biz.dreamaker.workreport.account.domain.Company;
import biz.dreamaker.workreport.account.domain.CompanyRole;
import biz.dreamaker.workreport.report.dto.AddressResponse;
import biz.dreamaker.workreport.report.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class CompanyResponse {

    private AdminInfoResponse adminInfoResponse;

    private CompanyRole companyRole;

    private String companyNumber;

    private String bossName;

    private AddressResponse address;

    private LocalDate openDate;

    private String companyBusiness;

    private String email;

    private String phoneNumber;

    private String faxNumber;

    public static CompanyResponse of(Company company) {
        return CompanyResponse.builder()
                .adminInfoResponse(AdminInfoResponse.from(company.getAccount()))
                .companyRole(company.getCompanyRole())
                .companyNumber(company.getCompanyNumber())
                .bossName(company.getBossName())
                .address(AddressResponse.of(company.getAddress()))
                .openDate(company.getOpenDate())
                .companyBusiness(company.getCompanyBusiness())
                .email(company.getEmail())
                .phoneNumber(company.getPhoneNumber())
                .faxNumber(company.getFaxNumber())
                .build();
    }
}
