
package acme.features.manager.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.roles.Manager;

@Service
public class ManagerProjectShowService extends AbstractService<Manager, Project> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository managerProjectRepository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		boolean status;
		int projectId;
		Manager manager;
		Project project;

		projectId = super.getRequest().getData("id", int.class);
		project = this.managerProjectRepository.findOneProjectById(projectId);
		Principal principal = super.getRequest().getPrincipal();
		manager = this.managerProjectRepository.findManagerById(principal.getActiveRoleId());

		status = project != null && super.getRequest().getPrincipal().hasRole(manager) && project.getManager().equals(manager);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Project object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.managerProjectRepository.findOneProjectById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset;
		dataset = super.unbind(object, "code", "title", "projectAbstract", "indication", "cost", "link", "draftMode");
		super.getResponse().addData(dataset);
	}
}
