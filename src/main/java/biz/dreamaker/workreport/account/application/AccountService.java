package biz.dreamaker.workreport.account.application;

import biz.dreamaker.workreport.account.domain.Account;
import biz.dreamaker.workreport.account.domain.Company;
import biz.dreamaker.workreport.account.dto.AdminInfoRequest;
import biz.dreamaker.workreport.account.dto.AdminInfoResponse;
import biz.dreamaker.workreport.account.dto.CompanyRequest;
import biz.dreamaker.workreport.account.dto.CompanyResponse;
import biz.dreamaker.workreport.account.repository.AccountRepository;

import java.util.NoSuchElementException;

import biz.dreamaker.workreport.account.repository.CompanyRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(
            AccountRepository accountRepository, CompanyRepository companyRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AdminInfoResponse findByUsername(String username) {
        return AdminInfoResponse.from(accountRepository.findByUsername(username).orElseThrow(
                () -> new NoSuchElementException("해당하는 아이디를 찾을 수 없습니다.")));
    }

    public AdminInfoResponse enrollPersonal(AdminInfoRequest request) {

        Account account = request.toPersonal(passwordEncoder);
        accountRepository.save(account);
        return AdminInfoResponse.from(account);
    }

    public AdminInfoResponse updateAccount(Long id, AdminInfoRequest request) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당하는 아이디를 찾을수 없습니다."));

        account.update(request.getName(), request.getPhoneNumber());
        return AdminInfoResponse.from(account);
    }

    public AdminInfoResponse updateAccountForCompanyOfPersonal(String username, CompanyRequest request) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("해당하는 아이디를 찾을수 없습니다."));

        Company company = request.ofPersonal(account);
        companyRepository.save(company);
        return AdminInfoResponse.from(account);
    }

    public AdminInfoResponse updateAccountForCompanyOfGroup(String username, CompanyRequest request) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("해당하는 아이디를 찾을수 없습니다."));
        Company company = request.ofGroup(account);
        companyRepository.save(company);
        return AdminInfoResponse.from(account);
    }

    public CompanyResponse findByUsernameCompany(String username) {

        Company company = companyRepository.findByAccountUsername(username)
                .orElseThrow(() -> new NoSuchElementException("해당하는 아이디를 찾을수 없습니다."));
        return CompanyResponse.of(company);
    }
}
