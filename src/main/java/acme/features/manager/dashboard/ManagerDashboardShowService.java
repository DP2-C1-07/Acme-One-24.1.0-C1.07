
package acme.features.manager.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.ManagerDashboard;
import acme.roles.Manager;

@Service
public class ManagerDashboardShowService extends AbstractService<Manager, ManagerDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerDashboardRepository repository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		ManagerDashboard dashboard;

		int totalCouldUserStories;
		int totalShouldUserStories;
		int totalMustUserStories;
		int totalWontUserStories;
		Double userStoryEstimatedCostAverage;
		Double userStoryEstimatedCostDeviation;
		Integer maximumUserStoryEstimatedCost;
		Integer minimumUserStoryEstimatedCost;
		Double projectCostAverage;
		Double projectCostDeviation;
		Integer maximumProjectCost;
		Integer minimumProjectCost;

		totalCouldUserStories = this.repository.totalCouldUserStories();
		totalShouldUserStories = this.repository.totalShouldUserStories();
		totalMustUserStories = this.repository.totalMustUserStories();
		totalWontUserStories = this.repository.totalWontUserStories();
		userStoryEstimatedCostAverage = this.repository.userStoryEstimatedCostAverage();
		userStoryEstimatedCostDeviation = this.repository.userStoryEstimatedCostDeviation();
		maximumUserStoryEstimatedCost = this.repository.maximumUserStoryEstimatedCost();
		minimumUserStoryEstimatedCost = this.repository.minimumUserStoryEstimatedCost();
		projectCostAverage = this.repository.projectCostAverage();
		projectCostDeviation = this.repository.projectCostDeviation();
		maximumProjectCost = this.repository.maximumProjectCost();
		minimumProjectCost = this.repository.minimumProjectCost();

		dashboard = new ManagerDashboard();
		dashboard.setTotalCouldUserStories(totalCouldUserStories);
		dashboard.setTotalShouldUserStories(totalShouldUserStories);
		dashboard.setTotalMustUserStories(totalMustUserStories);
		dashboard.setTotalWontUserStories(totalWontUserStories);
		dashboard.setUserStoryEstimatedCostAverage(userStoryEstimatedCostAverage);
		dashboard.setUserStoryEstimatedCostDeviation(userStoryEstimatedCostDeviation);
		dashboard.setMaximumUserStoryEstimatedCost(maximumUserStoryEstimatedCost);
		dashboard.setMinimumUserStoryEstimatedCost(minimumUserStoryEstimatedCost);
		dashboard.setProjectCostAverage(projectCostAverage);
		dashboard.setProjectCostDeviation(projectCostDeviation);
		dashboard.setMaximumProjectCost(maximumProjectCost);
		dashboard.setMinimumProjectCost(minimumProjectCost);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final ManagerDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, //
			"totalCouldUserStories", "totalShouldUserStories", "totalMustUserStories", "totalWontUserStories", // 
			"userStoryEstimatedCostAverage", "userStoryEstimatedCostDeviation", "maximumUserStoryEstimatedCost", "minimumUserStoryEstimatedCost", //
			"projectCostAverage", "projectCostDeviation", "maximumprojectCost", "minimumprojectCost");

		super.getResponse().addData(dataset);
	}
}
