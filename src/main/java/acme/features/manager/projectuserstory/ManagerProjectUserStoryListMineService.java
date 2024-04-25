
package acme.features.manager.projectuserstory;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.ProjectUserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectUserStoryListMineService extends AbstractService<Manager, ProjectUserStory> {

	@Autowired
	private ManagerProjectUserStoryRepository repository;


	@Override
	public void authorise() {
		Principal principal = super.getRequest().getPrincipal();
		Manager manager = this.repository.findOneManagerById(principal.getActiveRoleId());

		boolean status = super.getRequest().getPrincipal().hasRole(manager);

		super.getResponse().setAuthorised(status);
	}
	@Override
	public void load() {
		Principal principal = super.getRequest().getPrincipal();
		Manager manager = this.repository.findOneManagerById(principal.getActiveRoleId());

		Collection<ProjectUserStory> projectUserStories = this.repository.findProjectUserStoriesByManagerId(manager.getId());

		super.getBuffer().addData(projectUserStories);
	}

	@Override
	public void unbind(final ProjectUserStory object) {
		assert object != null;
		Dataset dataset = new Dataset();
		dataset.put("userStory", object.getUserStory().getTitle());
		super.getResponse().addData(dataset);
	}

}
