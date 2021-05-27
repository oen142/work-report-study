package biz.dreamaker.workreport.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindPasswordRequest {

    private String username;
    private String name;
    private String phoneNumber;


}
