
package acme.features.auditor.codeaudit;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.audit_records.AuditRecord;
import acme.entities.codeaudits.CodeAudit;
import acme.entities.codeaudits.Mark;
import acme.features.auditor.auditrecord.AuditorAuditRecordRepository;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditListMineService extends AbstractService<Auditor, CodeAudit> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorCodeAuditRepository	auditorCodeAuditRepository;

	@Autowired
	private AuditorAuditRecordRepository	auditorAuditRecordRespository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<CodeAudit> objects;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		objects = this.auditorCodeAuditRepository.findAllByAuditorId(principal.getActiveRoleId());

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Collection<AuditRecord> list = this.auditorAuditRecordRespository.findAllByCodeAuditId(object.getId());
		Mark mark = object.getMark(list);

		Dataset dataset;
		dataset = super.unbind(object, "code", "executionDate", "type");
		dataset.put("mark", mark);
		if (object.getDraftMode()) {
			final Locale local = super.getRequest().getLocale();

			dataset.put("draftMode", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("draftMode", "No");
		super.getResponse().addData(dataset);
	}
}
