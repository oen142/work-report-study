package biz.dreamaker.workreport.account.repository;

import biz.dreamaker.workreport.account.domain.Company;
import biz.dreamaker.workreport.account.domain.QAccount;
import biz.dreamaker.workreport.account.domain.QCompany;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Optional;

import static biz.dreamaker.workreport.account.domain.QAccount.account;
import static biz.dreamaker.workreport.account.domain.QCompany.company;

public class CompanyRepositoryImpl extends QuerydslRepositorySupport implements CompanyRepositoryCustom {


    private JPAQueryFactory queryFactory;

    public CompanyRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Company.class);
        this.queryFactory = queryFactory;
    }


    @Override
    public Optional<Company> findByAccountId(Long accountId) {
        return Optional.ofNullable(
                queryFactory.selectFrom(company)
                        .innerJoin(company.account, account)
                        .fetchJoin()
                        .where(account.id.eq(accountId))
                        .fetchOne());
    }

    @Override
    public Optional<Company> findByAccountUsername(String username) {
        return Optional.ofNullable(
                queryFactory.selectFrom(company)
                        .innerJoin(company.account, account)
                        .fetchJoin()
                        .where(account.username.eq(username))
                        .fetchOne());
    }
}
