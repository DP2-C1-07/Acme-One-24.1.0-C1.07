
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.roles.Manager;

@Service
public class ManagerProjectListMineService extends AbstractService<Manager, Project> {
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
		project = this.managerProjectRepository.findOneById(projectId);
		manager = project.getManager();

		status = super.getRequest().getPrincipal().hasRole(manager);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Project> objects;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		objects = this.managerProjectRepository.findAllProjectsByManagerId(principal.getActiveRoleId());

		super.getBuffer().addData(objects);
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
