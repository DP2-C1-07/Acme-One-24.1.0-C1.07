
package acme.features.authenticated.objective;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.objectives.Objective;
import acme.entities.objectives.ObjectivePriority;

@Service
public class AuthenticatedObjectiveShowService extends AbstractService<Authenticated, Objective> {

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
		Objective object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneObjectiveById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Objective object) {
		assert object != null;

		SelectChoices choices;
		choices = SelectChoices.from(ObjectivePriority.class, object.getPriority());

		Dataset dataset;
		dataset = super.unbind(object, "instantiationMoment", "title", "description", "priority", "initiateMoment", "finalizationMoment", "link");
		if (object.isCritical()) {
			final Locale local = super.getRequest().getLocale();

			dataset.put("critical", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("critical", "No");
		dataset.put("statuses", choices);
		super.getResponse().addData(dataset);
	}
}
