
package acme.features.authenticated.objective;

import java.time.temporal.ChronoUnit;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.objectives.Objective;
import acme.entities.objectives.ObjectivePriority;

@Service
public class AuthenticatedObjectiveCreateService extends AbstractService<Authenticated, Objective> {

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

		object = new Objective();
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Objective object) {
		assert object != null;

		super.bind(object, "instantiationMoment", "title", "description", "priority", "initiateMoment", "finalizationMoment", "critical", "link");
	}

	@Override
	public void validate(final Objective object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("instantiationMoment") && !super.getBuffer().getErrors().hasErrors("initiateMoment") && !super.getBuffer().getErrors().hasErrors("finalizationMoment")) {
			boolean condition1 = MomentHelper.isAfter(object.getInitiateMoment(), object.getInstantiationMoment());
			super.state(condition1, "initiateMoment", "authenticated.objective.create.error.initiate-moment");

			boolean condition2 = MomentHelper.isAfter(object.getFinalizationMoment(), object.getInitiateMoment());
			super.state(condition2, "finalizationMoment", "authenticated.objective.create.error.finalization-moment");

			boolean condition3 = MomentHelper.isLongEnough(object.getInitiateMoment(), object.getFinalizationMoment(), 1, ChronoUnit.HOURS);
			super.state(condition3, "finalizationMoment", "authenticated.objective.create.error.duration");
		}
	}

	@Override
	public void perform(final Objective object) {
		assert object != null;
		this.repository.save(object);
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
