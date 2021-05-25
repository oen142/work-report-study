package biz.dreamaker.workreport.account.application;

import biz.dreamaker.workreport.account.dto.AdminInfoResponse;
import biz.dreamaker.workreport.account.repository.AccountRepository;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(
        AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AdminInfoResponse findByUsername(String username) {
        return AdminInfoResponse.from(accountRepository.findByUsername(username).orElseThrow(
            () -> new NoSuchElementException("해당하는 아이디를 찾을 수 없습니다.")));
    }
}
