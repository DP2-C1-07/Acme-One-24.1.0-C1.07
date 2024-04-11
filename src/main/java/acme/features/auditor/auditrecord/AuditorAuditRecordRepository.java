
package acme.features.auditor.auditrecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audit_records.AuditRecord;
import acme.roles.Auditor;

@Repository
public interface AuditorAuditRecordRepository extends AbstractRepository {

	@Query("select a from AuditRecord a where a.id = :id")
	AuditRecord findOneById(int id);

	@Query("select a from AuditRecord a where a.codeAudit.auditor.id = :auditorId")
	Collection<AuditRecord> findAllByAuditorId(int auditorId);

	@Query("select a from AuditRecord a where a.codeAudit.id = :codeAuditId")
	Collection<AuditRecord> findAllByCodeAuditId(int codeAuditId);

	@Query("select a from Auditor a where a.id = :auditorId")
	Auditor findAuditorByAuditorId(int auditorId);
}
