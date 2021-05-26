package biz.dreamaker.workreport.account.dto;

import biz.dreamaker.workreport.account.domain.Account;
import biz.dreamaker.workreport.account.domain.Company;
import biz.dreamaker.workreport.account.domain.CompanyRole;
import biz.dreamaker.workreport.report.dto.AddressRequest;
import biz.dreamaker.workreport.report.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequest {

    private String companyNumber;

    private String bossName;

    private AddressRequest address;

    private LocalDate openDate;

    private String companyBusiness;

    private String email;

    private String phoneNumber;

    private String faxNumber;


    public Company ofPersonal(Account account) {

        return Company.ofPersonal(account, companyNumber,
                bossName, address.toAddress(), openDate, companyBusiness,
                email, phoneNumber, faxNumber);
    }

    public Company ofGroup(Account account) {

        return Company.ofPersonal(account, companyNumber,
                bossName, address.toAddress(), openDate, companyBusiness,
                email, phoneNumber, faxNumber);
    }
}
