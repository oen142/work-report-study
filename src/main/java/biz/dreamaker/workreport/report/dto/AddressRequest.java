package biz.dreamaker.workreport.report.dto;

import biz.dreamaker.workreport.report.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {

    private String zipcode;
    private String basic;
    private String detail;


    public Address toAddress() {
        return Address.of(zipcode , basic , detail);
    }
}
