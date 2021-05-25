package biz.dreamaker.workreport.security.filters;

import biz.dreamaker.workreport.security.handlers.JwtAuthenticationFailureHandler;
import biz.dreamaker.workreport.security.jwt.HeaderTokenExtractor;
import biz.dreamaker.workreport.security.tokens.JwtPreProcessingToken;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtAuthenticationFailureHandler failureHandler;
    private final HeaderTokenExtractor extractor;

    public JwtAuthenticationFilter(RequestMatcher matcher,
        JwtAuthenticationFailureHandler failureHandler,
        HeaderTokenExtractor extractor) {
        super(matcher);

        this.failureHandler = failureHandler;
        this.extractor = extractor;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response)
        throws AuthenticationException {

        String tokenPayload = request.getHeader("Authorization");

        System.out.println("tokenPayload = " + tokenPayload);
        JwtPreProcessingToken token = new JwtPreProcessingToken(
            this.extractor.extract(tokenPayload));

        System.out.println("token.getPrincipal().toString() = " + token.getPrincipal().toString());
        return super.getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain, Authentication authResult)
        throws IOException, ServletException {

        System.out.println("successAuthenticaiton ");
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);

        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, AuthenticationException failed) {

        SecurityContextHolder.clearContext();
        this.unsuccessfulAuthentication(request, response, failed);
    }
}
