package biz.dreamaker.workreport.account.dto;

import biz.dreamaker.workreport.account.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class AdminInfoResponse {

    private Long id;
    private String username;
    private String name;
    private String phoneNumber;

    public static AdminInfoResponse from(Account account) {
        return AdminInfoResponse.builder()
                .id(account.getId())
                .username(account.getUsername())
                .name(account.getName())
                .phoneNumber(account.getPhoneNumber())
                .build();
    }
}
