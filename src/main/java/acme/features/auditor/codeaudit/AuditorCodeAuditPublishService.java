
package acme.features.auditor.codeaudit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.code_audits.CodeAudit;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditPublishService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorCodeAuditRepository auditorCodeAuditRespository;


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

		super.bind(object, "code", "executionDate", "type", "correctiveAction", "mark", "link", "project");
		object.setAuditor(auditor);
	}

	@Override
	public void validate(final CodeAudit object) {
		// TODO: comprobar que nota > C
	}

	@Override
	public void perform(final CodeAudit object) {
		assert object != null;

		this.auditorCodeAuditRespository.save(object);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Auditor auditor;
		auditor = object.getAuditor();

		Dataset dataset;
		dataset = super.unbind(object, "code", "executionDate", "type", "correctiveAction", "mark", "link", "project");
		dataset.put("auditor", auditor);
		super.getResponse().addData(dataset);
	}

}
