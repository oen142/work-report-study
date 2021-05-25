package biz.dreamaker.workreport.security.providers;

import biz.dreamaker.workreport.security.AccountContext;
import biz.dreamaker.workreport.security.jwt.JwtDecoder;
import biz.dreamaker.workreport.security.tokens.JwtPreProcessingToken;
import biz.dreamaker.workreport.security.tokens.PostAuthorizationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private JwtDecoder jwtDecoder;

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {
        String token = (String) authentication.getPrincipal();
        AccountContext accountContext = jwtDecoder.decodeJwt(token);
        return PostAuthorizationToken.getTokenFromAccountContext(accountContext);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtPreProcessingToken.class.isAssignableFrom(authentication);
    }
}
