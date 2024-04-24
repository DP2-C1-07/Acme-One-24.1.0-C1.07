
package acme.features.auditor.auditrecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.audit_records.AuditRecord;
import acme.entities.codeaudits.CodeAudit;
import acme.entities.codeaudits.Mark;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordPublishService extends AbstractService<Auditor, AuditRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditRecordRepository auditorAuditRecordRespository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		boolean status;
		int auditRecordId;
		Auditor auditor;
		AuditRecord auditRecord;

		auditRecordId = super.getRequest().getData("id", int.class);
		auditRecord = this.auditorAuditRecordRespository.findOneById(auditRecordId);
		auditor = auditRecord.getCodeAudit().getAuditor();

		status = auditRecord != null && super.getRequest().getPrincipal().hasRole(auditor) && auditRecord.getCodeAudit().getAuditor().equals(auditor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditRecord object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.auditorAuditRecordRespository.findOneById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final AuditRecord object) {
		assert object != null;

		CodeAudit codeAudit;
		codeAudit = object.getCodeAudit();

		super.bind(object, "code", "periodBeginning", "periodEnd", "mark", "link");
		object.setCodeAudit(codeAudit);
	}

	@Override
	public void validate(final AuditRecord object) {
		//TODO
	}

	@Override
	public void perform(final AuditRecord object) {
		assert object != null;
		object.setDraftMode(false);
		this.auditorAuditRecordRespository.save(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		CodeAudit codeAudit;
		codeAudit = object.getCodeAudit();
		SelectChoices choices;

		choices = SelectChoices.from(Mark.class, object.getMark());

		Dataset dataset;
		dataset = super.unbind(object, "code", "periodBeginning", "periodEnd", "link");
		dataset.put("codeAudit", codeAudit);
		dataset.put("mark", choices);
		super.getResponse().addData(dataset);
	}

}
