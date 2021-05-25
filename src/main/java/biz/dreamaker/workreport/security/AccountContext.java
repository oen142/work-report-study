package biz.dreamaker.workreport.security;

import biz.dreamaker.workreport.account.domain.Account;
import biz.dreamaker.workreport.account.domain.UserRole;
import biz.dreamaker.workreport.security.tokens.JwtPostProcessingToken;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class AccountContext extends User {

    private Account account;

    public AccountContext(Account account, String username, String password,
        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.account = account;
    }

    public AccountContext(String username, String password, String role) {
        super(username, password, parseAuthorities(role));
    }

    public static AccountContext fromAccountModel(Account account) {
        return new AccountContext(account, account.getUsername(), account.getPassword(),
            parseAuthorities(account.getUserRole()));
    }

    public static AccountContext fromJwtPostToken(JwtPostProcessingToken token) {
        return new AccountContext(null, token.getUserId(), token.getPassword(),
            token.getAuthorities());
    }

    private static List<SimpleGrantedAuthority> parseAuthorities(String role) {
        return parseAuthorities(UserRole.getRoleByName(role));
    }

    private static List<SimpleGrantedAuthority> parseAuthorities(UserRole role) {
        return Stream.of(role)
            .map(r -> new SimpleGrantedAuthority(r.getRoleName()))
            .collect(Collectors.toList());
    }

    public Account getAccount() {
        return this.account;
    }
}
