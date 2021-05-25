package biz.dreamaker.workreport.account.ui;

import biz.dreamaker.workreport.account.application.AccountService;
import biz.dreamaker.workreport.account.domain.Account;
import biz.dreamaker.workreport.account.dto.AdminInfoResponse;
import biz.dreamaker.workreport.security.tokens.PostAuthorizationToken;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountEnrollController {

    private final AccountService accountService;

    public AccountEnrollController(
        AccountService accountService) {
        this.accountService = accountService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/api/admins/me")
    public ResponseEntity<AdminInfoResponse> findAdminMine(Authentication authentication) {
        String username = getLoginAccountUsername(authentication);
        AdminInfoResponse responses = accountService.findByUsername(username);
        return ResponseEntity.ok().body(responses);
    }

    @PostMapping("/enroll/admin")
    public ResponseEntity<AccountEnrollResponse> enrollAdminManagerAccount(
        @RequestBody AdminEnrollRequest request) {
        AccountEnrollResponse accountEnrollResponse = accountService.enrollManager(request);

        return ResponseEntity.created(URI.create("/accounts/" + accountEnrollResponse.getId()))
            .body(accountEnrollResponse);
    }

    @DeleteMapping("/api/admins/{id}")


    public String getLoginAccountUsername(Authentication authentication) {
        PostAuthorizationToken token = (PostAuthorizationToken) authentication;
        return token.getAccountContext().getUsername();
    }
}
