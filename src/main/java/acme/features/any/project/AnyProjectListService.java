
package acme.features.any.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.roles.Manager;

@Service
public class AnyProjectListService extends AbstractService<Manager, Project> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyProjectRepository managerProjectRepository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {

		super.getResponse().setAuthorised(true);

	}

	@Override
	public void load() {
		Collection<Project> objects;

		objects = this.managerProjectRepository.findAllProjects();

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset;
		dataset = super.unbind(object, "code", "title", "projectAbstract", "indication", "cost", "link");
		super.getResponse().addData(dataset);
	}
}
