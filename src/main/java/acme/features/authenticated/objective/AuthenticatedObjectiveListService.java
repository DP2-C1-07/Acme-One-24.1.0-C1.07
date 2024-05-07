
package acme.features.authenticated.objective;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.objectives.Objective;

@Service
public class AuthenticatedObjectiveListService extends AbstractService<Authenticated, Objective> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedObjectiveRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Authenticated.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Objective> objects;

		objects = this.repository.findAllObjectives();

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Objective object) {
		assert object != null;

		Dataset dataset;
		dataset = super.unbind(object, "instantiationMoment", "title", "description", "priority", "initiateMoment", "finalizationMoment", "link");
		if (object.isCritical()) {
			final Locale local = super.getRequest().getLocale();

			dataset.put("critical", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("critical", "No");
		super.getResponse().addData(dataset);
	}
}
