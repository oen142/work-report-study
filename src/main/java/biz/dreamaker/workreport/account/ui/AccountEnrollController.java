package biz.dreamaker.workreport.account.ui;

import biz.dreamaker.workreport.account.application.AccountService;
import biz.dreamaker.workreport.account.dto.AdminInfoRequest;
import biz.dreamaker.workreport.account.dto.AdminInfoResponse;
import biz.dreamaker.workreport.account.dto.CompanyRequest;
import biz.dreamaker.workreport.account.dto.CompanyResponse;
import biz.dreamaker.workreport.account.dto.FindPasswordRequest;
import biz.dreamaker.workreport.account.dto.PasswordResponse;
import biz.dreamaker.workreport.account.dto.UpdatePasswordRequest;
import biz.dreamaker.workreport.security.dto.ParseAuthenticationToAccount;
import biz.dreamaker.workreport.security.tokens.PostAuthorizationToken;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountEnrollController {

    private final AccountService accountService;

    public AccountEnrollController(
        AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/find-password")
    public ResponseEntity<PasswordResponse> findPassword(
        @RequestBody FindPasswordRequest request
    ) {
        PasswordResponse password = accountService.findPassword(request);
        return ResponseEntity.ok().body(password);
    }

    @PutMapping("/api/account/password")
    public ResponseEntity<Boolean> updatePassword(
        @RequestBody UpdatePasswordRequest request,
        Authentication authentication
    ) {
        String username = getLoginAccountUsername(authentication);
        accountService.updatePassword(username, request);
        return ResponseEntity.ok().body(true);
    }

    @GetMapping("/api/account/me")
    public ResponseEntity<AdminInfoResponse> findAdminMine(Authentication authentication) {
        String username = getLoginAccountUsername(authentication);
        AdminInfoResponse responses = accountService.findByUsername(username);
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/api/account/me/company")
    public ResponseEntity<CompanyResponse> findAdminMineOfCompany(Authentication authentication) {
        String username = getLoginAccountUsername(authentication);
        CompanyResponse responses = accountService.findByUsernameCompany(username);
        return ResponseEntity.ok().body(responses);
    }

    @PostMapping("/enroll/personal")
    public ResponseEntity<AdminInfoResponse> enrollAdminManagerAccount(
        @RequestBody AdminInfoRequest request) {
        AdminInfoResponse response = accountService.enrollPersonal(request);

        return ResponseEntity.created(URI.create("/accounts/" + response.getId()))
            .body(response);
    }

    @PutMapping("/api/account/{id}")
    public ResponseEntity<AdminInfoResponse> updateAdminManagerAccount(
        @PathVariable(name = "id") Long id, @RequestBody AdminInfoRequest request) {
        AdminInfoResponse response = accountService.updateAccount(id, request);

        return ResponseEntity.ok()
            .body(response);
    }

    @PutMapping("/api/account/company/personal")
    public ResponseEntity<AdminInfoResponse> updateAdminManagerAccountPersonal(
        Authentication authentication, @RequestBody CompanyRequest request) {
        String username = ParseAuthenticationToAccount.getLoginAccountUsername(authentication);
        AdminInfoResponse response = accountService
            .updateAccountForCompanyOfPersonal(username, request);

        return ResponseEntity.ok()
            .body(response);
    }

    @PutMapping("/api/account/company/group")
    public ResponseEntity<AdminInfoResponse> updateAdminManagerAccountGroup(
        Authentication authentication,
        @RequestBody CompanyRequest request) {
        String username = ParseAuthenticationToAccount.getLoginAccountUsername(authentication);
        AdminInfoResponse response = accountService
            .updateAccountForCompanyOfGroup(username, request);

        return ResponseEntity.ok()
            .body(response);
    }

    @PutMapping("/api/account/personal")
    public ResponseEntity<AdminInfoResponse> updateAccountPersonal(
        Authentication authentication) {
        String username = ParseAuthenticationToAccount.getLoginAccountUsername(authentication);
        accountService.updateAccountForPersonal(username);

        return ResponseEntity.ok()
            .build();
    }

    @PutMapping("/api/account/company/{id}")
    public ResponseEntity<CompanyResponse> updateAdminManagerAccountPersonal(
        @PathVariable Long id, @RequestBody CompanyRequest request) {
        CompanyResponse response = accountService
            .updateAccountForCompany(id, request);

        return ResponseEntity.ok()
            .body(response);
    }

    public String getLoginAccountUsername(Authentication authentication) {
        PostAuthorizationToken token = (PostAuthorizationToken) authentication;
        return token.getAccountContext().getUsername();
    }
}
