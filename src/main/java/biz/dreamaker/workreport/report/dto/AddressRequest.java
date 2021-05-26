package biz.dreamaker.workreport.report.dto;

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


}
