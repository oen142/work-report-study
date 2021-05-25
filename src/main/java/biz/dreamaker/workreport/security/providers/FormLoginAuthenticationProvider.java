package biz.dreamaker.workreport.security.providers;

import biz.dreamaker.workreport.account.domain.Account;
import biz.dreamaker.workreport.account.repository.AccountRepository;
import biz.dreamaker.workreport.security.AccountContext;
import biz.dreamaker.workreport.security.tokens.PostAuthorizationToken;
import biz.dreamaker.workreport.security.tokens.PreAuthorizationToken;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class FormLoginAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {

        PreAuthorizationToken token = (PreAuthorizationToken) authentication;

        String username = token.getUsername();
        String password = token.getUserPassword();

        Account account = accountRepository.findByUsername(username)
            .orElseThrow(() -> new NoSuchElementException("정보에 맞는 계정이 없습니다."));

        if (isCorrectPassword(password, account)) {
            return PostAuthorizationToken
                .getTokenFromAccountContext(AccountContext.fromAccountModel(account));
        }

        throw new NoSuchElementException("인증 정보가 정확하지 않습니다.");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return PreAuthorizationToken.class.isAssignableFrom(aClass);
    }

    private boolean isCorrectPassword(String password, Account account) {
        return passwordEncoder.matches(password, account.getPassword());
    }
}
