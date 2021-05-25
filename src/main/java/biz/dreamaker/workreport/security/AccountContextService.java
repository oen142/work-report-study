package biz.dreamaker.workreport.security;

import biz.dreamaker.workreport.account.domain.Account;
import biz.dreamaker.workreport.account.repository.AccountRepository;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AccountContextService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        Account account = accountRepository.findByUsername(userId)
            .orElseThrow(() -> new NoSuchElementException("아이디에 맞는 계정이 없습니다."));

        return getAccountContext(account);
    }

    private AccountContext getAccountContext(Account account) {
        return AccountContext.fromAccountModel(account);
    }
}
