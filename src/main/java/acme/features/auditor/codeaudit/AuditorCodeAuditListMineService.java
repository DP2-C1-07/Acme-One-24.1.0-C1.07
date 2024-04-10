
package acme.features.auditor.codeaudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.code_audits.CodeAudit;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditListMineService extends AbstractService<Auditor, CodeAudit> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorCodeAuditRepository auditorCodeAuditRepository;


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

		Dataset dataset;
		dataset = super.unbind(object, "code", "executionDate", "type", "mark");
		super.getResponse().addData(dataset);
	}
}
