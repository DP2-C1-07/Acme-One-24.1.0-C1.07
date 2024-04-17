
package acme.features.auditor.codeaudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.audit_records.AuditRecord;
import acme.entities.code_audits.CodeAudit;
import acme.entities.code_audits.Mark;
import acme.features.auditor.auditrecord.AuditorAuditRecordRepository;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditShowService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorCodeAuditRepository		auditorCodeAuditRepository;

	@Autowired
	private AuditorAuditRecordRepository	auditorAuditRecordRespository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		boolean status;
		int userStoryId;
		Auditor auditor;
		CodeAudit codeAudit;

		userStoryId = super.getRequest().getData("id", int.class);
		codeAudit = this.auditorCodeAuditRepository.findOneById(userStoryId);
		auditor = codeAudit.getAuditor();

		status = codeAudit != null && super.getRequest().getPrincipal().hasRole(auditor) && codeAudit.getAuditor().equals(auditor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		CodeAudit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.auditorCodeAuditRepository.findOneById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Collection<AuditRecord> list = this.auditorAuditRecordRespository.findAllByCodeAuditId(object.getId());
		Mark mark = object.getMark(list);

		Dataset dataset;
		dataset = super.unbind(object, "code", "executionDate", "type", "correctiveAction", "link", "project");
		dataset.put("mark", mark);
		super.getResponse().addData(dataset);
	}
}
