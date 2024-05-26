
package acme.features.developer.trainingsession;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.trainingsessions.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionDeleteService extends AbstractService<Developer, TrainingSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int trainingSessionId;
		TrainingSession trainingSession;
		Developer developer;

		trainingSessionId = super.getRequest().getData("id", int.class);
		trainingSession = this.repository.findOneTrainingSessionById(trainingSessionId);
		developer = this.repository.findOneDeveloperById(super.getRequest().getPrincipal().getActiveRoleId());

		status = trainingSession != null && trainingSession.isDraft() && super.getRequest().getPrincipal().hasRole(trainingSession.getTrainingModule().getDeveloper()) && trainingSession.getTrainingModule().getDeveloper().equals(developer);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingSession object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTrainingSessionById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingSession object) {
		assert object != null;

		super.bind(object, "code", "initiateMoment", "finalizationMoment", "location", "instructor", "contactEmail", "link", "trainingModule");
	}

	@Override
	public void validate(final TrainingSession object) {
		assert object != null;
		if (!object.isDraft())
			super.state(false, "draft", "developer.training-session.form.error.delete-published");
	}

	@Override
	public void perform(final TrainingSession object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "initiateMoment", "finalizationMoment", "location", "instructor", "contactEmail", "link", "trainingModule");
		dataset.put("masterId", object.getTrainingModule().getId());
		if (object.isDraft()) {
			final Locale local = super.getRequest().getLocale();

			dataset.put("draft", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("draft", "No");
		super.getResponse().addData(dataset);
	}

}
