package biz.dreamaker.workreport.report.repository;

import biz.dreamaker.workreport.account.domain.QAccount;
import biz.dreamaker.workreport.report.entity.QWorkReport;
import biz.dreamaker.workreport.report.entity.WorkReport;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static biz.dreamaker.workreport.account.domain.QAccount.account;
import static biz.dreamaker.workreport.report.entity.QWorkReport.workReport;

public class WorkReportRepositoryImpl extends QuerydslRepositorySupport implements WorkReportRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public WorkReportRepositoryImpl(JPAQueryFactory queryFactory) {
        super(WorkReport.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<WorkReport> findAllByAccountId(Long accountId) {
        List<WorkReport> fetch = queryFactory.selectFrom(workReport)
                .innerJoin(workReport.account, account)
                .fetchJoin()
                .where(account.id.eq(accountId))
                .fetch();
        return fetch;
    }
}
