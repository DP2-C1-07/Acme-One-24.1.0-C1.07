
package acme.features.client.contracts;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contract.Contract;
import acme.entities.projects.Project;
import acme.roles.client.Client;
import acme.utils.Validators;

@Service
public class ClientContractCreateService extends AbstractService<Client, Contract> {

	@Autowired
	private ClientContractRepository	clientContractRepository;

	@Autowired
	private Validators					validator;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {

		Contract contract = new Contract();
		Client client;

		client = this.clientContractRepository.findOneClientById(super.getRequest().getPrincipal().getActiveRoleId());

		contract.setDraftMode(true);
		contract.setClient(client);

		super.getBuffer().addData(contract);

	}

	@Override
	public void bind(final Contract object) {

		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		object.setDraftMode(true);
		project = this.clientContractRepository.findOneProjectById(projectId);

		super.bind(object, "code", "instantiationMoment", "providerName", "customerName", "goals", "budget");
		object.setInstantiationMoment(MomentHelper.getCurrentMoment());
		object.setProject(project);
	}

	@Override
	public void validate(final Contract object) {

		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Contract existing;

			existing = this.clientContractRepository.findOneContractByCode(object.getCode());
			super.state(existing == null, "code", "client.contract.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("budget")) {
			super.state(object.getBudget().getAmount() >= 0, "budget", "client.contract.form.error.negative-amount");
			super.state(object.getBudget().getAmount() <= 1000000, "budget", "client.contract.form.error.excededMaximum");
			super.state(validator.moneyValidator(object.getBudget().getCurrency()), "budget", "client.contract.form.error.currency-not-suported");
		}
	}

	@Override
	public void perform(final Contract object) {

		assert object != null;

		Date moment;

		moment = MomentHelper.getCurrentMoment();
		object.setInstantiationMoment(moment);

		this.clientContractRepository.save(object);
	}

	@Override
	public void unbind(final Contract object) {

		assert object != null;

		Collection<Project> projects;
		SelectChoices choices;

		projects = this.clientContractRepository.findAllPublishedProjects();
		choices = SelectChoices.from(projects, "code", object.getProject());

		Dataset dataset;

		dataset = super.unbind(object, "code", "providerName", "customerName", "goals", "budget");

		dataset.put("instantationMoment", MomentHelper.getCurrentMoment());
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		super.getResponse().addData(dataset);
	}

}
