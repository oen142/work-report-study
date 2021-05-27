package biz.dreamaker.workreport.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PasswordResponse {

    private String password;

    public static PasswordResponse of(String password) {
        return new PasswordResponse(password);
    }
}
