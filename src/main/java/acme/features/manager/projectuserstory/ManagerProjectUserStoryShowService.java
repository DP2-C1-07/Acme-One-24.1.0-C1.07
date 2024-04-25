
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
public class ManagerProjectUserStoryShowService extends AbstractService<Manager, ProjectUserStory> {

	@Autowired
	private ManagerProjectUserStoryRepository repository;


	@Override
	public void authorise() {
		int id = super.getRequest().getData("id", int.class);
		ProjectUserStory projectUserStory = this.repository.findOneProjectUserStoryById(id);

		Principal principal = super.getRequest().getPrincipal();
		Manager manager = this.repository.findOneManagerById(principal.getActiveRoleId());

		boolean status = projectUserStory != null && super.getRequest().getPrincipal().hasRole(manager) && projectUserStory.getProject().getManager().equals(manager) && projectUserStory.getUserStory().getManager().equals(manager);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		ProjectUserStory projectUserStory = this.repository.findOneProjectUserStoryById(id);

		super.getBuffer().addData(projectUserStory);
	}

	@Override
	public void unbind(final ProjectUserStory object) {
		assert object != null;

		Principal principal = super.getRequest().getPrincipal();
		int managerId = principal.getActiveRoleId();

		Collection<UserStory> userStories = this.repository.findUserStoriesByManagerId(managerId);
		SelectChoices userStoryChoices = SelectChoices.from(userStories, "title", object.getUserStory());

		Collection<Project> projects = this.repository.findProjectsByManagerId(managerId);
		SelectChoices projectChoices = SelectChoices.from(projects, "title", object.getProject());

		Dataset dataset = super.unbind(object, "project", "userStory");
		dataset.put("project", object.getProject().getId());
		dataset.put("projects", projectChoices);
		dataset.put("userStory", object.getUserStory().getId());
		dataset.put("userStories", userStoryChoices);
		// super.getResponse().addGlobal("id", object.getId());
		super.getResponse().addData(dataset);
	}

}
