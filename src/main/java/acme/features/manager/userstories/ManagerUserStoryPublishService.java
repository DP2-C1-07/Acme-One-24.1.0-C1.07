
package acme.features.manager.userstories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.userstories.UserStory;
import acme.features.manager.ManagerRepository;
import acme.roles.Manager;

@Service
public class ManagerUserStoryPublishService extends AbstractService<Manager, UserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerUserStoryRepository	managerUserStoryRepository;

	@Autowired
	private ManagerRepository			managerRepository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		boolean status;
		int managerId;
		int userStoryId;
		Manager manager;
		UserStory userStory;

		userStoryId = super.getRequest().getData("id", int.class);
		userStory = this.managerUserStoryRepository.findOneById(userStoryId);
		managerId = userStory.getManager().getId();
		manager = this.managerRepository.findOneById(managerId);

		status = userStory != null && super.getRequest().getPrincipal().hasRole(manager) && userStory.getManager().equals(manager);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		UserStory object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.managerUserStoryRepository.findOneById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final UserStory object) {
		assert object != null;

		super.bind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link", "manager");
	}

	@Override
	public void validate(final UserStory object) {
		// TODO: qué hay que poner aquí?
	}

	@Override
	public void perform(final UserStory object) {
		assert object != null;

		this.managerUserStoryRepository.save(object);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;

		Dataset dataset;
		dataset = super.unbind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link", "manager");
		super.getResponse().addData(dataset);
	}
}
