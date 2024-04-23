
package acme.features.client.dashboard;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.ClientDashboard;
import acme.roles.client.Client;

@Service
public class ClientDashboardShowService extends AbstractService<Client, ClientDashboard> {

	@Autowired
	private ClientDashboardRepository repository;


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Client.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		//TODO

	}

	@Override
	public void unbind(final ClientDashboard object) {

		Dataset dataset;

		dataset = super.unbind(object, "totalNumProgressLogLessThan25", "totalNumProgressLogBetween25And50",
			"totalNumProgressLogBetween50And75", "totalNumProgressLogAbove75", "averageBudget", "deviationBudget",
			"minimumBudget", "maximumBudget");

		super.getResponse().addData(dataset);

	}

}