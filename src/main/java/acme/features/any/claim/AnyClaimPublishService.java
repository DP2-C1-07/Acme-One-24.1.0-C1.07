
package acme.features.any.claim;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.claims.Claim;

@Service
public class AnyClaimPublishService extends AbstractService<Any, Claim> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyClaimRepository repository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		boolean status;
		int claimId;
		Claim claim;

		claimId = super.getRequest().getData("id", int.class);
		claim = this.repository.findOneById(claimId);
		status = claim != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Claim object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Claim object) {
		assert object != null;

		super.bind(object, "code", "instantiationMoment", "heading", "description", "department", "emailAddress", "link");
	}

	@Override
	public void validate(final Claim object) {
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Claim existing;

			existing = this.repository.findOneByCode(object.getCode());
			super.state(existing == null || existing.getCode().equals(object.getCode()), "code", "any.claim.publish.error.duplicated");
		}
	}

	@Override
	public void perform(final Claim object) {
		assert object != null;
		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Claim object) {
		assert object != null;

		Dataset dataset;
		dataset = super.unbind(object, "code", "instantiationMoment", "heading", "description", "department", "emailAddress", "link");
		if (object.isDraftMode()) {
			final Locale local = super.getRequest().getLocale();

			dataset.put("draftMode", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("draftMode", "No");
		super.getResponse().addData(dataset);
	}
}
