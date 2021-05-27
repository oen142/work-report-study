package biz.dreamaker.workreport.report.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    private String zipcode;
    private String basic;
    private String detail;

    public static Address of(String zipcode, String basic, String detail) {
        return Address.builder()
                .zipcode(zipcode)
                .basic(basic)
                .detail(detail)
                .build();
    }

    public String toStringAddress() {
        return basic + detail;
    }
}
