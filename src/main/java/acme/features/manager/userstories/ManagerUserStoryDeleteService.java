
package acme.features.manager.userstories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.userstories.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryDeleteService extends AbstractService<Manager, UserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerUserStoryRepository managerUserStoryRepository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		boolean status;
		int userStoryId;
		Manager manager;
		UserStory userStory;

		userStoryId = super.getRequest().getData("id", int.class);
		userStory = this.managerUserStoryRepository.findOneById(userStoryId);
		manager = userStory.getManager();

		status = userStory != null && super.getRequest().getPrincipal().hasRole(manager) && userStory.getManager().equals(manager);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void bind(final UserStory object) {
		assert object != null;

		Manager manager;
		manager = object.getManager();

		super.bind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link");
		object.setManager(manager);
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
	public void validate(final UserStory object) {
		assert object != null;
	}

	@Override
	public void perform(final UserStory object) {
		assert object != null;

		this.managerUserStoryRepository.delete(object);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;

		Manager manager;
		manager = object.getManager();

		Dataset dataset;
		dataset = super.unbind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link");
		dataset.put("manager", manager);
		super.getResponse().addData(dataset);
	}
}
