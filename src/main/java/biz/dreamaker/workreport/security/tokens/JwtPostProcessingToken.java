package biz.dreamaker.workreport.security.tokens;

import biz.dreamaker.workreport.account.domain.UserRole;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class JwtPostProcessingToken extends UsernamePasswordAuthenticationToken {

    private JwtPostProcessingToken(Object principal, Object credentials,
        Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public JwtPostProcessingToken(String username, UserRole role) {
        super(username, "1234", parseAuthorities(role));
    }

    private static Collection<? extends GrantedAuthority> parseAuthorities(UserRole role) {
        return Stream.of(role)
            .map(JwtPostProcessingToken::parseUserRoleFromToken)
            .collect(Collectors.toList());
    }

    private static SimpleGrantedAuthority parseUserRoleFromToken(UserRole r) {
        return new SimpleGrantedAuthority(r.getRoleName());
    }

    public String getUserId() {
        return (String) super.getPrincipal();
    }

    public String getPassword() {
        return (String) super.getCredentials();
    }
}
