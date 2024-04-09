
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.userstories.UserStory;
import acme.features.manager.userstories.ManagerUserStoryRepository;
import acme.roles.Manager;

@Service
public class ManagerProjectDeleteService extends AbstractService<Manager, Project> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private ManagerProjectRepository	managerProjectRepository;

	@Autowired
	private ManagerUserStoryRepository	managerUserStoryRepository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		boolean status;
		int projectId;
		Manager manager;
		Project project;

		projectId = super.getRequest().getData("id", int.class);
		project = this.managerProjectRepository.findOneById(projectId);
		manager = project.getManager();

		status = project != null && super.getRequest().getPrincipal().hasRole(manager) && project.getManager().equals(manager);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void bind(final Project object) {
		assert object != null;

		Manager manager;
		manager = object.getManager();

		super.bind(object, "code", "title", "projectAbstract", "indication", "cost", "link");
		object.setManager(manager);
	}

	@Override
	public void load() {
		Project object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.managerProjectRepository.findOneById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void validate(final Project object) {
		assert object != null;
	}

	@Override
	public void perform(final Project object) {
		assert object != null;

		Collection<UserStory> userStories;
		int projectId;
		projectId = super.getRequest().getData("id", int.class);
		userStories = this.managerUserStoryRepository.findAllByProjectId(projectId);

		this.managerProjectRepository.deleteAll(userStories);
		this.managerProjectRepository.delete(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Manager manager;
		manager = object.getManager();

		Dataset dataset;
		dataset = super.unbind(object, "code", "title", "projectAbstract", "indication", "cost", "link");
		dataset.put("manager", manager);
		super.getResponse().addData(dataset);
	}
}
