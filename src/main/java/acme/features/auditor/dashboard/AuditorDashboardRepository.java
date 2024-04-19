
package acme.features.auditor.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface AuditorDashboardRepository extends AbstractRepository {

	@Query("select count(c) from CodeAudit c where c.type = acme.entities.codeaudits.CodeAuditType.STATIC")
	int totalStaticCodeAudits();

	@Query("select count(c) from CodeAudit c where c.type = acme.entities.codeaudits.CodeAuditType.DYNAMIC")
	int totalDynamicCodeAudits();

	@Query("select count(a) from AuditRecord a where a.codeAudit.auditor.id = :auditorId group by a.codeAudit.id")
	Collection<Long> findAuditRecordCountsByAuditorId(int auditorId);

	//	@Query("select avg(c.count) from (select count(a) as count from AuditRecord a where a.codeAudit.auditor.id = :auditorId group by a.codeAudit.id) c")
	//	Double averageAuditRecord(int auditorId);
	//
	//	@Query("select stddev(c.count) from (select count(a) as count from AuditRecord a where a.codeAudit.auditor.id = :auditorId group by a.codeAudit.id) c")
	//	Double deviationAuditRecord(int auditorId);
	//
	//	@Query("select min(c.count) from (select count(a) as count from AuditRecord a where a.codeAudit.auditor.id = :auditorId group by a.codeAudit.id) c")
	//	Integer minimumAuditRecord(int auditorId);
	//
	//	@Query("select max(c.count) from (select count(a) as count from AuditRecord a where a.codeAudit.auditor.id = :auditorId group by a.codeAudit.id) c")
	//	Integer maximumAuditRecord(int auditorId);

}
