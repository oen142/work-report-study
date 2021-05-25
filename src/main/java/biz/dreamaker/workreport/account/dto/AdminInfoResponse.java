package biz.dreamaker.workreport.account.dto;

import biz.dreamaker.workreport.account.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AdminInfoResponse {

    private Long id;
    private String username;

    public static AdminInfoResponse from(Account orElseThrow) {
        return null;
    }
}
