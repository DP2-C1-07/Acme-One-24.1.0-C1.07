
package acme.features.any.auditrecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.audit_records.AuditRecord;

@Service
public class AnyAuditRecordShowService extends AbstractService<Any, AuditRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyAuditRecordRepository anyAuditRecordRepository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		AuditRecord object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.anyAuditRecordRepository.findOneById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		Dataset dataset;
		dataset = super.unbind(object, "code", "periodBeginning", "periodEnd", "mark", "link");

		super.getResponse().addData(dataset);
	}

}
