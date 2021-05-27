package biz.dreamaker.workreport.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdatePasswordRequest {

    private String prePassword;
    private String newPassword;

}
