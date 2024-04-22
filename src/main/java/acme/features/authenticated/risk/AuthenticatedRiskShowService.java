
package acme.features.authenticated.risk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.risk.Risk;

@Service
public class AuthenticatedRiskShowService extends AbstractService<Authenticated, Risk> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedRiskRepository authenticatedRiskRepository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		boolean status;
		int auditRecordId;
		Risk risk;

		auditRecordId = super.getRequest().getData("id", int.class);
		risk = this.authenticatedRiskRepository.findOneById(auditRecordId);

		status = risk != null && super.getRequest().getPrincipal().isAuthenticated();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Risk object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.authenticatedRiskRepository.findOneById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Risk object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "reference", "identificationDate", "impact", "probability", "description", "link", "project");

		super.getResponse().addData(dataset);
	}

}
