
package acme.features.auditor.auditrecord;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.audit_records.AuditRecord;
import acme.entities.codeaudits.CodeAudit;
import acme.features.auditor.codeaudit.AuditorCodeAuditRepository;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordCreateService extends AbstractService<Auditor, AuditRecord> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private AuditorAuditRecordRepository	auditorAuditRecordRepository;

	@Autowired
	private AuditorCodeAuditRepository		auditorCodeAuditRepository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		AuditRecord object;
		CodeAudit codeAudit;
		int codeAuditId;

		System.out.println("funcion1");
		codeAuditId = super.getRequest().getData("codeAuditId", int.class);
		codeAudit = this.auditorCodeAuditRepository.findOneById(codeAuditId);

		System.out.println("funciona2");
		object = new AuditRecord();
		object.setCodeAudit(codeAudit);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final AuditRecord object) {
		assert object != null;

		CodeAudit codeAudit;
		codeAudit = object.getCodeAudit();
		super.bind(object, "code", "periodBeginning", "periodEnd", "mark", "link");
		object.setCodeAudit(codeAudit);
		System.out.println(object);
	}

	@Override
	public void validate(final AuditRecord object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("consecutiveDates"))
			super.state(object.getPeriodBeginning().before(object.getPeriodEnd()), "consecutiveDates", "auditor.audit-record.error.consecutiveDates");

		if (!super.getBuffer().getErrors().hasErrors("duration")) {
			long diffInMili;
			long diffInHour;

			diffInMili = object.getPeriodEnd().getTime() - object.getPeriodBeginning().getTime();
			diffInHour = TimeUnit.MILLISECONDS.toMinutes(diffInMili);
			super.state(diffInHour < 1, "duration", "auditor.audit-record.error.duration");
		}

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			AuditRecord existing;

			existing = this.auditorAuditRecordRepository.findOneByCode(object.getCode());
			super.state(existing == null || existing.getCode().equals(object.getCode()), "code", "auditor.audit-record.error.code");
		}
	}

	@Override
	public void perform(final AuditRecord object) {
		assert object != null;
		this.auditorAuditRecordRepository.save(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		CodeAudit codeAudit;
		codeAudit = object.getCodeAudit();

		Dataset dataset;
		dataset = super.unbind(object, "code", "periodBeginning", "periodEnd", "mark", "link");
		dataset.put("codeAudit", codeAudit);
		super.getResponse().addData(dataset);
	}
}
