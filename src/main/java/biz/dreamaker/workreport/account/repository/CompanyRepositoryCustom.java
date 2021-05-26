package biz.dreamaker.workreport.account.repository;

import biz.dreamaker.workreport.account.domain.Company;

import java.util.Optional;

public interface CompanyRepositoryCustom {

    Optional<Company> findByAccountId(Long accountId);

    Optional<Company> findByAccountUsername(String username);
}
