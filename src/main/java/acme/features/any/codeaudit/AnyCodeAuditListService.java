
package acme.features.any.codeaudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.audit_records.AuditRecord;
import acme.entities.codeaudits.CodeAudit;
import acme.entities.codeaudits.Mark;

@Service
public class AnyCodeAuditListService extends AbstractService<Any, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyCodeAuditRepository repository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {

		super.getResponse().setAuthorised(true);

	}

	@Override
	public void load() {
		Collection<CodeAudit> objects;

		objects = this.repository.findAllPublishedCodeAudits();

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;
		String auditor;

		auditor = object.getAuditor().getUserAccount().getUsername();
		Collection<AuditRecord> list = this.repository.findAllAuditRecordsByCodeAuditId(object.getId());
		Mark mark = object.getMark(list);

		Dataset dataset;
		dataset = super.unbind(object, "code", "executionDate", "type");
		dataset.put("mark", mark);
		dataset.put("auditor", auditor);
		super.getResponse().addData(dataset);
	}

}
