
package acme.features.developer.dashboard;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.trainingmodules.TrainingModule;
import acme.entities.trainingsessions.TrainingSession;
import acme.forms.DeveloperDashboard;
import acme.roles.Developer;

@Service
public class DeveloperDashboardShowService extends AbstractService<Developer, DeveloperDashboard> {

	@Autowired
	private DeveloperDashboardRepository dashboardRepository;


	@Override
	public void authorise() {
		boolean status;

		Principal principal = super.getRequest().getPrincipal();
		int id = principal.getAccountId();
		Developer developer = this.dashboardRepository.findDeveloperById(id);
		status = developer != null && principal.hasRole(Developer.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Principal principal = super.getRequest().getPrincipal();
		int userAccountId = principal.getAccountId();
		DeveloperDashboard developerDashboard = new DeveloperDashboard();
		Collection<TrainingModule> modules = this.dashboardRepository.findAllTMByDeveloperId(userAccountId);
		Collection<TrainingSession> sessions = this.dashboardRepository.findAllTSByDeveloperId(userAccountId);

		developerDashboard.setTotalTrainingSessionsWithLink(Double.NaN);
		developerDashboard.setTotalTrainingModulesWithUpdateMoment(Double.NaN);
		developerDashboard.setTrainingModuleEstimatedTimeAverage(Double.NaN);
		developerDashboard.setTrainingModuleEstimatedTimeDeviation(Double.NaN);
		developerDashboard.setTrainingModuleMinimunEstimatedTime(Double.NaN);
		developerDashboard.setTrainingModuleMaximumEstimatedTime(Double.NaN);

		if (!modules.isEmpty()) {
			developerDashboard.setTotalTrainingModulesWithUpdateMoment(this.dashboardRepository.totalTrainingModulesWithUpdateMoment(userAccountId));
			developerDashboard.setTrainingModuleEstimatedTimeAverage(this.dashboardRepository.averageTrainingModulesTime(userAccountId));
			developerDashboard.setTrainingModuleEstimatedTimeDeviation(this.dashboardRepository.deviatonTrainingModulesTime(userAccountId));
			developerDashboard.setTrainingModuleMinimunEstimatedTime(this.dashboardRepository.minimumTrainingModulesTime(userAccountId));
			developerDashboard.setTrainingModuleMaximumEstimatedTime(this.dashboardRepository.maximumTrainingModulesTime(userAccountId));
		}

		if (!sessions.isEmpty())
			developerDashboard.setTotalTrainingSessionsWithLink(this.dashboardRepository.totalTrainingSessionsWithLink(userAccountId));

		super.getBuffer().addData(developerDashboard);

	}

	@Override
	public void unbind(final DeveloperDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, "totalTrainingModulesWithUpdateMoment", "totalTrainingSessionsWithLink", "trainingModuleEstimatedTimeAverage", "trainingModuleEstimatedTimeDeviation", "trainingModuleMinimunEstimatedTime",
			"trainingModuleMaximumEstimatedTime");

		super.getResponse().addData(dataset);
	}
}
