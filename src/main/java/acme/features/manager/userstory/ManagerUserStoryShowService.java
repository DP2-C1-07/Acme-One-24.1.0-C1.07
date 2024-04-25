
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
public class ManagerUserStoryShowService extends AbstractService<Manager, UserStory> {
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
		Principal principal;

		principal = super.getRequest().getPrincipal();

		userStoryId = super.getRequest().getData("id", int.class);
		userStory = this.managerUserStoryRepository.findOneUserStoryById(userStoryId);
		manager = this.managerUserStoryRepository.findManagerById(principal.getActiveRoleId());

		status = userStory != null && super.getRequest().getPrincipal().hasRole(manager) && userStory.getManager().equals(manager);

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
	public void unbind(final UserStory object) {
		assert object != null;

		SelectChoices choices;
		choices = SelectChoices.from(UserStoryPriority.class, object.getPriority());

		Dataset dataset;
		dataset = super.unbind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link", "draftMode");
		dataset.put("statuses", choices);
		super.getResponse().addData(dataset);
	}
}
