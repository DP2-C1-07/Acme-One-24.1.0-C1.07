
package acme.features.any.auditrecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.audit_records.AuditRecord;
import acme.features.auditor.auditrecord.AuditorAuditRecordRepository;

@Service
public class AnyAuditRecordListService extends AbstractService<Any, AuditRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditRecordRepository anyAuditRecordRepository;


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
		object = this.anyAuditRecordRepository.findAllByCodeAuditId(codeAuditId);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		Dataset dataset;
		dataset = super.unbind(object, "code", "periodBeginning", "periodEnd", "mark");
		dataset.put("codeAuditId", object.getCodeAudit().getId());
		super.getResponse().addData(dataset);
	}

}
