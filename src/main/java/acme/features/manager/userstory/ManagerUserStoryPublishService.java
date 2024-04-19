
package acme.features.manager.userstory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.userstories.UserStory;
import acme.entities.userstories.UserStoryPriority;
import acme.roles.Manager;

@Service
public class ManagerUserStoryPublishService extends AbstractService<Manager, UserStory> {

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

		Principal principal = super.getRequest().getPrincipal();
		manager = this.managerUserStoryRepository.findManagerById(principal.getActiveRoleId());

		userStoryId = super.getRequest().getData("id", int.class);
		userStory = this.managerUserStoryRepository.findOneUserStoryById(userStoryId);

		status = userStory != null && userStory.isDraftMode() && super.getRequest().getPrincipal().hasRole(manager) && userStory.getManager().equals(manager);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		UserStory object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.managerUserStoryRepository.findOneUserStoryById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final UserStory object) {
		assert object != null;

		super.bind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link");
	}

	@Override
	public void validate(final UserStory object) {
		assert object != null;
	}

	@Override
	public void perform(final UserStory object) {
		assert object != null;
		object.setDraftMode(false);
		this.managerUserStoryRepository.save(object);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;

		SelectChoices choices;
		choices = SelectChoices.from(UserStoryPriority.class, object.getPriority());

		Dataset dataset;
		dataset = super.unbind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link");
		dataset.put("statuses", choices);
		super.getResponse().addData(dataset);
	}
}
