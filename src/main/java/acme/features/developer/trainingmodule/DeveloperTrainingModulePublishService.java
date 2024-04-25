
package acme.features.developer.trainingmodule;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.trainingmodules.TrainingModule;
import acme.entities.trainingmodules.TrainingModuleDifficultyLevel;
import acme.entities.trainingsessions.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModulePublishService extends AbstractService<Developer, TrainingModule> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingModuleRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int trainingModuleId;
		TrainingModule trainingModule;
		Developer developer;

		trainingModuleId = super.getRequest().getData("id", int.class);
		trainingModule = this.repository.findOneTrainingModuleById(trainingModuleId);
		developer = trainingModule == null ? null : trainingModule.getDeveloper();
		status = trainingModule != null && trainingModule.isDraft() && super.getRequest().getPrincipal().hasRole(developer);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingModule object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTrainingModuleById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingModule object) {
		assert object != null;

		Developer developer;
		Date updateTime;

		updateTime = MomentHelper.getCurrentMoment();
		developer = object.getDeveloper();

		super.bind(object, "code", "creationMoment", "details", "difficultyLevel", "link", "totalTime", "draft");
		object.setUpdateMoment(updateTime);
		object.setDeveloper(developer);
	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;
		Collection<TrainingSession> trainingSessions = this.repository.findManyTrainingSessionsByTrainingModuleId(object.getId());

		if (object.getUpdateMoment() != null && !super.getBuffer().getErrors().hasErrors("creationMoment") && !super.getBuffer().getErrors().hasErrors("updateMoment"))
			super.state(MomentHelper.isAfterOrEqual(object.getUpdateMoment(), object.getCreationMoment()), "updateMoment", "developer.training-module.form.error.update-before-creation");

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingModule existing;

			existing = this.repository.findOneTrainingModuleByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "developer.training-module.form.error.duplicated");
		}
		if (trainingSessions.isEmpty())
			super.state(true, "draft", "developer.training-module.form.error.at-least-one-training-session");
		if (!trainingSessions.isEmpty()) {
			Boolean allTSPublished;

			allTSPublished = trainingSessions.stream().allMatch(t -> !t.draft);
			super.state(!allTSPublished, "draft", "developer.training-module.form.error.all-training-sessions-must-be-published");

		}

	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;

		object.setDraft(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		SelectChoices choices;
		Dataset dataset;

		choices = SelectChoices.from(TrainingModuleDifficultyLevel.class, object.getDifficultyLevel());
		dataset = super.unbind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "totalTime", "draft");
		dataset.put("difficultyLevels", choices);

		super.getResponse().addData(dataset);
	}

}
