
package acme.features.auditor.auditrecord;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.audit_records.AuditRecord;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordListService extends AbstractService<Auditor, AuditRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditRecordRepository auditorAuditRecordRepository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<AuditRecord> object;
		int codeAuditId;

		codeAuditId = super.getRequest().getData("codeAuditId", int.class);
		object = this.auditorAuditRecordRepository.findAllByCodeAuditId(codeAuditId);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		Dataset dataset;
		dataset = super.unbind(object, "code", "periodBeginning", "periodEnd", "mark");
		if (object.getDraftMode()) {
			final Locale local = super.getRequest().getLocale();

			dataset.put("draftMode", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("draftMode", "No");
		super.getResponse().addData(dataset);
	}

	@Override
	public void unbind(final Collection<AuditRecord> objects) {
		assert objects != null;

		int codeAuditId;

		codeAuditId = super.getRequest().getData("codeAuditId", int.class);

		super.getResponse().addGlobal("codeAuditId", codeAuditId);
	}

}
