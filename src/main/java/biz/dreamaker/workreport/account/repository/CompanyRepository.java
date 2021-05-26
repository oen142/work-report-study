package biz.dreamaker.workreport.account.repository;


import biz.dreamaker.workreport.account.domain.Account;
import biz.dreamaker.workreport.account.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long>, CompanyRepositoryCustom {


    boolean existsByAccount(Account account);
}
