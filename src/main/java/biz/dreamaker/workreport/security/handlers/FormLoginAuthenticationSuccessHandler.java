package biz.dreamaker.workreport.security.handlers;

import biz.dreamaker.workreport.security.AccountContext;
import biz.dreamaker.workreport.security.dto.TokenDto;
import biz.dreamaker.workreport.security.jwt.JwtFactory;
import biz.dreamaker.workreport.security.tokens.PostAuthorizationToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class FormLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtFactory factory;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res,
        Authentication auth) throws IOException {
        AccountContext context = ((PostAuthorizationToken) auth).getAccountContext();

        context.getAuthorities().forEach(
            auths -> System.out.println("auth = " + auths.getAuthority())
        );
        String tokenString = factory.generateToken(context);

        Collection<GrantedAuthority> authorities = context.getAuthorities();
        processResponse(res, writeDto(tokenString, authorities));
    }

    private TokenDto writeDto(String token, Collection<GrantedAuthority> authorities) {
        return new TokenDto(token, authorities);
    }

    private void processResponse(HttpServletResponse res, TokenDto dto)
        throws IOException {
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.setStatus(HttpStatus.OK.value());
        res.getWriter().write(objectMapper.writeValueAsString(dto));
    }
}

