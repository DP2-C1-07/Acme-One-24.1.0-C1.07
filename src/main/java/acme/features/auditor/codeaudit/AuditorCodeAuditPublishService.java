
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
public class AuditorCodeAuditPublishService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorCodeAuditRepository		auditorCodeAuditRespository;

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
		codeAudit = this.auditorCodeAuditRespository.findOneById(userStoryId);
		auditor = codeAudit.getAuditor();

		status = codeAudit != null && super.getRequest().getPrincipal().hasRole(auditor) && codeAudit.getAuditor().equals(auditor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		CodeAudit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.auditorCodeAuditRespository.findOneById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final CodeAudit object) {
		assert object != null;

		Auditor auditor;
		auditor = object.getAuditor();

		super.bind(object, "code", "executionDate", "type", "correctiveAction", "link", "project");
		object.setAuditor(auditor);
	}

	@Override
	public void validate(final CodeAudit object) {
		Collection<AuditRecord> list = this.auditorAuditRecordRespository.findAllByCodeAuditId(object.getId());
		assert object.getMark(list).getNumericMark() >= Mark.C.getNumericMark();
	}

	@Override
	public void perform(final CodeAudit object) {
		assert object != null;
		object.setDraftMode(false);
		this.auditorCodeAuditRespository.save(object);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Auditor auditor;
		auditor = object.getAuditor();

		Dataset dataset;
		dataset = super.unbind(object, "code", "executionDate", "type", "correctiveAction", "link", "project");
		dataset.put("auditor", auditor);
		super.getResponse().addData(dataset);
	}

}
