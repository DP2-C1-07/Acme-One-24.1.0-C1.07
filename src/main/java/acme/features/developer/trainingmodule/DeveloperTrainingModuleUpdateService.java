
package acme.features.developer.trainingmodule;

import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.trainingmodules.TrainingModule;
import acme.entities.trainingmodules.TrainingModuleDifficultyLevel;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleUpdateService extends AbstractService<Developer, TrainingModule> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingModuleRepository repository;

	// AbstractService interface -------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		TrainingModule trainingModule;
		Developer developer;

		masterId = super.getRequest().getData("id", int.class);
		trainingModule = this.repository.findOneTrainingModuleById(masterId);

		developer = this.repository.findOneDeveloperById(super.getRequest().getPrincipal().getActiveRoleId());
		status = trainingModule != null && trainingModule.isDraft() && super.getRequest().getPrincipal().hasRole(developer) && trainingModule.getDeveloper().equals(developer);

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
		super.bind(object, "code", "details", "difficultyLevel", "link", "totalTime", "project");
		object.setUpdateMoment(updateTime);
		object.setDeveloper(developer);
		object.setDraft(true);
	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;

		if (object.getUpdateMoment() != null && !super.getBuffer().getErrors().hasErrors("creationMoment") && !super.getBuffer().getErrors().hasErrors("updateMoment"))
			super.state(MomentHelper.isAfterOrEqual(object.getUpdateMoment(), object.getCreationMoment()), "updateMoment", "developer.training-module.form.error.update-before-creation");

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingModule existing;

			existing = this.repository.findOneTrainingModuleByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "developer.training-module.form.error.duplicated");
		}

	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		SelectChoices choices;
		Dataset dataset;
		Collection<Project> projects;
		SelectChoices choicesProject;

		projects = this.repository.findAllPublishedProjects();
		choicesProject = SelectChoices.from(projects, "code", object.getProject());

		choices = SelectChoices.from(TrainingModuleDifficultyLevel.class, object.getDifficultyLevel());
		dataset = super.unbind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "totalTime", "project");
		dataset.put("difficultyLevels", choices);
		if (object.isDraft()) {
			final Locale local = super.getRequest().getLocale();

			dataset.put("draft", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("draft", "No");
		dataset.put("project", choicesProject.getSelected().getKey());
		dataset.put("projects", choicesProject);
		super.getResponse().addData(dataset);
	}

}
