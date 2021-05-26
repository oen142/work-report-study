package biz.dreamaker.workreport.report.repository;

import biz.dreamaker.workreport.report.entity.WorkReport;

import java.util.List;

public interface WorkReportRepositoryCustom {

    List<WorkReport> findAllByAccountId(Long accountId);
}
