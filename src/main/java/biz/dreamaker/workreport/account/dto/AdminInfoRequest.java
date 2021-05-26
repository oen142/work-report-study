package biz.dreamaker.workreport.account.dto;

import biz.dreamaker.workreport.account.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminInfoRequest {

    private String username;
    private String phoneNumber;
    private String password;

    public Account toPersonal(PasswordEncoder passwordEncoder) {
        return Account.ofUser(username, passwordEncoder.encode(password), phoneNumber);
    }
}
