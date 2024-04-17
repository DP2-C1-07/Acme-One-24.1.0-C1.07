
package acme.features.auditor.codeaudit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.code_audits.CodeAudit;
import acme.roles.Auditor;

@Repository
public interface AuditorCodeAuditRepository extends AbstractRepository {

	@Query("select c from CodeAudit c where c.id = :id")
	CodeAudit findOneById(int id);

	@Query("select c from CodeAudit c where c.auditor.id = :auditorId")
	Collection<CodeAudit> findAllByAuditorId(int auditorId);

	@Query("select c from CodeAudit c where c.project.id = :projectId")
	Collection<CodeAudit> findAllByProjectId(int projectId);

	@Query("select a from Auditor a where a.id = :auditorId")
	Auditor findAuditorByAuditorId(int auditorId);

	@Query("delete a from AuditRecord a where a.codeAudit.id = :codeAuditId")
	Auditor deleteAuditRecordByCodeAuditId(int codeAuditId);

}
