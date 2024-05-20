
package acme.features.auditor.auditrecord;

import java.sql.Date;
import java.util.concurrent.TimeUnit;

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
	private AuditorAuditRecordRepository auditorAuditRecordRepository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		boolean status;
		int auditRecordId;
		Auditor auditor;
		AuditRecord auditRecord;

		auditRecordId = super.getRequest().getData("id", int.class);
		auditRecord = this.auditorAuditRecordRepository.findOneById(auditRecordId);
		auditor = auditRecord.getCodeAudit().getAuditor();

		status = auditRecord != null && super.getRequest().getPrincipal().hasRole(auditor) && auditRecord.getCodeAudit().getAuditor().equals(auditor) && auditRecord.getDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditRecord object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.auditorAuditRecordRepository.findOneById(id);

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
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("periodEnd")) {
			long diffInMili;
			long diffInHour;

			super.state(object.getPeriodEnd().after(Date.valueOf("2000-1-1")) || object.getPeriodEnd().equals(Date.valueOf("2000-1-1")), "periodEnd", "auditor.code-audit.error.executionDate");

			if (object.getPeriodBeginning() != null) {
				diffInMili = object.getPeriodEnd().getTime() - object.getPeriodBeginning().getTime();
				diffInHour = TimeUnit.MILLISECONDS.toHours(diffInMili);
				super.state(diffInHour >= 1, "periodEnd", "auditor.audit-record.error.duration");
				super.state(object.getPeriodBeginning() != null || object.getPeriodBeginning().before(object.getPeriodEnd()), "periodEnd", "auditor.audit-record.error.consecutiveDates");
			}
		}

		if (!super.getBuffer().getErrors().hasErrors("periodBeginning"))
			super.state(object.getPeriodBeginning().after(Date.valueOf("2000-1-1")) || object.getPeriodBeginning().equals(Date.valueOf("2000-1-1")), "periodBeginning", "auditor.code-audit.error.executionDate");

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			AuditRecord existing;
			boolean status;

			existing = this.auditorAuditRecordRepository.findOneByCode(object.getCode());
			if (existing != null)
				status = existing.getId() == object.getId();
			else
				status = false;
			super.state(existing == null || status, "code", "auditor.audit-record.error.code");
		}
	}

	@Override
	public void perform(final AuditRecord object) {
		assert object != null;
		object.setDraftMode(false);
		this.auditorAuditRecordRepository.save(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		CodeAudit codeAudit;
		codeAudit = object.getCodeAudit();
		SelectChoices choices;

		choices = SelectChoices.from(Mark.class, object.getMark());

		Dataset dataset;
		dataset = super.unbind(object, "code", "periodBeginning", "periodEnd", "link", "draftMode");
		dataset.put("codeAudit", codeAudit);
		dataset.put("mark", choices);
		super.getResponse().addData(dataset);
	}

}
