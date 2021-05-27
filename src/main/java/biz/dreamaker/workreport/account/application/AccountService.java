package biz.dreamaker.workreport.account.application;

import biz.dreamaker.workreport.account.domain.Account;
import biz.dreamaker.workreport.account.domain.Company;
import biz.dreamaker.workreport.account.dto.*;
import biz.dreamaker.workreport.account.repository.AccountRepository;

import java.util.NoSuchElementException;
import java.util.UUID;

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

        boolean isAccount = companyRepository.existsByAccount(account);
        if (isAccount) {
            throw new RuntimeException("해당 아이디로 등록되어 있는 회사계정이 있습니다.");
        }
        Company company = request.ofPersonal(account);
        companyRepository.save(company);
        return AdminInfoResponse.from(account);
    }

    public AdminInfoResponse updateAccountForCompanyOfGroup(String username, CompanyRequest request) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("해당하는 아이디를 찾을수 없습니다."));

        boolean isAccount = companyRepository.existsByAccount(account);
        if (isAccount) {
            throw new RuntimeException("해당 아이디로 등록되어 있는 회사계정이 있습니다.");
        }
        Company company = request.ofGroup(account);
        companyRepository.save(company);
        return AdminInfoResponse.from(account);
    }

    public CompanyResponse findByUsernameCompany(String username) {

        Company company = companyRepository.findByAccountUsername(username)
                .orElseThrow(() -> new NoSuchElementException("해당하는 아이디를 찾을수 없습니다."));
        return CompanyResponse.of(company);
    }

    public PasswordResponse findPassword(FindPasswordRequest request) {
        Account account = accountRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new NoSuchElementException("해당하는 아이디를 찾을 수 없습니다."));

        account.isCorrect(request.getUsername(), request.getName(), request.getPhoneNumber());

        String password = UUID.randomUUID().toString();

        account.updatePassword(passwordEncoder.encode(password));
        return PasswordResponse.of(password);
    }

    public void updatePassword(String username, UpdatePasswordRequest request) {

        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("해당하는 아이디를 찾을 수 없습니다."));

        account.isCorrectPassword(passwordEncoder, request.getPrePassword());

        account.updatePassword(passwordEncoder.encode(request.getNewPassword()));

    }
}
