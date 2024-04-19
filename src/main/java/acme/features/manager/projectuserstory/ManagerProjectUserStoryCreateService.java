
package acme.features.manager.projectuserstory;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectUserStory;
import acme.entities.userstories.UserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectUserStoryCreateService extends AbstractService<Manager, ProjectUserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectUserStoryRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		ProjectUserStory object;
		object = new ProjectUserStory();
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final ProjectUserStory object) {
		assert object != null;
		int projectId = super.getRequest().getData("project", int.class);
		int userStoryId = super.getRequest().getData("userstory", int.class);

		Project project = this.repository.findOneProjectById(projectId);
		UserStory userStory = this.repository.findOneUserStoryById(userStoryId);

		object.setProject(project);
		object.setUserStory(userStory);
	}

	@Override
	public void validate(final ProjectUserStory object) {
		assert object != null;
	}

	@Override
	public void perform(final ProjectUserStory object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final ProjectUserStory object) {
		assert object != null;

		Principal principal = super.getRequest().getPrincipal();
		int managerId = principal.getActiveRoleId();

		Collection<Project> projects = this.repository.findProjectsByManagerId(managerId);
		SelectChoices projectChoices = SelectChoices.from(projects, "title", object.getProject());

		Collection<UserStory> userStories = this.repository.findUserStoriesByManagerId(managerId);
		SelectChoices userStoryChoices = SelectChoices.from(userStories, "title", object.getUserStory());

		Dataset dataset = new Dataset();
		dataset.put("project", projectChoices.getSelected().getKey());
		dataset.put("projects", projectChoices);
		dataset.put("userstory", userStoryChoices.getSelected().getKey());
		dataset.put("userstories", userStoryChoices);

		super.getResponse().addData(dataset);
	}
}
