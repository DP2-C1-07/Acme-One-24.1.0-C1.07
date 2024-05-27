
package acme.features.any.trainingModule;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.trainingmodules.TrainingModule;
import acme.entities.trainingmodules.TrainingModuleDifficultyLevel;

@Service
public class AnyTrainingModuleShowService extends AbstractService<Any, TrainingModule> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyTrainingModuleRepository repository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		TrainingModule object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		SelectChoices choices;
		Dataset dataset;
		Collection<Project> projects;
		SelectChoices choicesProject;

		projects = List.of(object.getProject());
		choicesProject = SelectChoices.from(projects, "code", object.getProject());

		choices = SelectChoices.from(TrainingModuleDifficultyLevel.class, object.getDifficultyLevel());

		dataset = super.unbind(object, "code", "creationMoment", "updateMoment", "details", "difficultyLevel", "link", "totalTime");
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
