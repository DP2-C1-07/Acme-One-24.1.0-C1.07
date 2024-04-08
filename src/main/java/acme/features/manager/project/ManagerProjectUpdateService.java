
package acme.features.manager.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.roles.Manager;

@Service
public class ManagerProjectUpdateService extends AbstractService<Manager, Project> {

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

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Project existing;

			existing = this.managerProjectRepository.findOneByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "manager.project.publish.error.duplicated");
		}
	}

	@Override
	public void perform(final Project object) {
		assert object != null;

		this.managerProjectRepository.save(object);
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
