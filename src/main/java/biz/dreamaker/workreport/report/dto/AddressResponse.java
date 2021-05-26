package biz.dreamaker.workreport.report.dto;

import biz.dreamaker.workreport.report.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class AddressResponse {


    private String zipcode;
    private String basic;
    private String detail;

    public static AddressResponse of(Address workAddress) {
        return AddressResponse.builder()
                .basic(workAddress.getBasic())
                .detail(workAddress.getDetail())
                .zipcode(workAddress.getZipcode())
                .build();
    }
}
