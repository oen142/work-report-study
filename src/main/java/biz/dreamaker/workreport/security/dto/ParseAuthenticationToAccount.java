package biz.dreamaker.workreport.security.dto;

import biz.dreamaker.workreport.security.tokens.PostAuthorizationToken;
import org.springframework.security.core.Authentication;

public class ParseAuthenticationToAccount {

    private ParseAuthenticationToAccount() {
    }

    public static String getLoginAccountUsername(Authentication authentication) {
        PostAuthorizationToken token = (PostAuthorizationToken) authentication;
        return token.getAccountContext().getUsername();
    }
}
