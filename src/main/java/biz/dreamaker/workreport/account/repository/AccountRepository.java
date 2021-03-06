package biz.dreamaker.workreport.account.repository;

import biz.dreamaker.workreport.account.domain.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUsername(String username);
}
