package biz.dreamaker.workreport.report.repository;

import biz.dreamaker.workreport.report.entity.WorkReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkReportRepository extends JpaRepository<WorkReport, Long>  , WorkReportRepositoryCustom {
}
