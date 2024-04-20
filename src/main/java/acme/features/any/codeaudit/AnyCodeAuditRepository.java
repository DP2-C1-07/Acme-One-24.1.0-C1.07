
package acme.features.any.codeaudit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.codeaudits.CodeAudit;

@Repository
public interface AnyCodeAuditRepository extends AbstractRepository {

	@Query("select ca from CodeAudit ca")
	Collection<CodeAudit> findAllCodeAudits();

	@Query("select ca from CodeAudit ca where ca.id = :id")
	CodeAudit findOneById(int id);

	@Query("select ca from CodeAudit ca where ca.draftMode = FALSE")
	Collection<CodeAudit> findAllPublishedCodeAudits();

}
