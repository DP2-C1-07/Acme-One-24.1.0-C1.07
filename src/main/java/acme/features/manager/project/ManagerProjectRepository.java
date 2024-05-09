
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audit_records.AuditRecord;
import acme.entities.codeaudits.CodeAudit;
import acme.entities.contract.Contract;
import acme.entities.progresslog.ProgressLog;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectUserStory;
import acme.entities.sponsorships.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Manager;

@Repository
public interface ManagerProjectRepository extends AbstractRepository {

	@Query("select p from Project p where p.manager.id = :managerId")
	Collection<Project> findAllProjectsByManagerId(int managerId);

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("select p from Project p where p.code = :code")
	Project findOneProjectByCode(String code);

	@Query("select m from Manager m where m.id = :managerId")
	Manager findManagerById(int managerId);

	@Query("select pu from ProjectUserStory pu where pu.project.id = :projectId")
	Collection<ProjectUserStory> findAllProjectUserStoriesByProjectId(int projectId);

	@Query("select c from CodeAudit c where c.project.id = :projectId")
	Collection<CodeAudit> findAllCodeAuditsFromProjectId(int projectId);

	@Query("select a from AuditRecord a where a.codeAudit.id = :codeAuditId")
	Collection<AuditRecord> findAllAuditRecordsFromCodeAuditId(int codeAuditId);

	@Query("select c from Contract c where c.project.id = :projectId")
	Collection<Contract> findAllContractsByProjectId(int projectId);

	@Query("select p from ProgressLog p where p.contract.id = :contractId")
	Collection<ProgressLog> findAllProgressLogsByContractId(int contractId);

	@Query("select s from Sponsorship s where s.project.id = :projectId")
	Collection<Sponsorship> findAllSponsorshipsByProjectId(int projectId);

	@Query("select i from Invoice i where i.sponsorship.id = :sponsorshipId")
	Collection<Invoice> findAllInvoicesBySponsorshipId(int sponsorshipId);
}
