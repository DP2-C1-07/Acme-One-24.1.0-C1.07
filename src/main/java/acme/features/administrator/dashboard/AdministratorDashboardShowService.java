
package acme.features.administrator.dashboard;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.forms.AdministratorDashboard;

@Service
public class AdministratorDashboardShowService extends AbstractService<Administrator, AdministratorDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorDashboardRepository repository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		boolean isAdministrator = super.getRequest().getPrincipal().hasRole(Administrator.class);
		super.getResponse().setAuthorised(isAdministrator);
	}

	@Override
	public void load() {
		long notices = this.repository.countNotices();
		double noticesWithEmailAndLinkRatio = notices > 0 ? (double) this.repository.countNoticesWithEmailAndLink() / notices : Double.NaN;

		long objectives = this.repository.countObjectives();
		double criticalObjectivesRatio = objectives > 0 ? (double) this.repository.countCriticalObjectives() / objectives : Double.NaN;
		double nonCriticalObjectivesRatio = objectives > 0 ? 1 - criticalObjectivesRatio : Double.NaN;

		Date tenWeeksDelta = MomentHelper.deltaFromCurrentMoment(-10, ChronoUnit.WEEKS);

		AdministratorDashboard dashboard = AdministratorDashboard.builder().administrators(this.repository.countAdministrators()).auditors(this.repository.countAuditors()).clients(this.repository.countClients()).consumers(this.repository.countConsumers())
			.developers(this.repository.countDevelopers()).managers(this.repository.countManagers()).providers(this.repository.countProviders()).sponsors(this.repository.countSponsors())

			.noticesWithEmailAndLinkRatio(noticesWithEmailAndLinkRatio).criticalObjectivesRatio(criticalObjectivesRatio).nonCriticalObjectivesRatio(nonCriticalObjectivesRatio)

			.averageRiskValue(this.repository.calculateAverageRiskValue()).riskValueDeviation(this.repository.calculateRiskValueDeviation()).minimumRiskValue(this.repository.calculateMinimumRiskValue())
			.maximumRiskValue(this.repository.calculateMaximumRiskValue())

			.averageClaimsPosted(this.repository.calculateAverageClaimsPerWeekPostedAfter(tenWeeksDelta)).claimsPostedDeviation(this.repository.calculateClaimsPerWeekDeviationPostedAfter(tenWeeksDelta))
			.minimumClaimsPosted(this.repository.calculateMinimumClaimsPerWeekPostedAfter(tenWeeksDelta)).maximumClaimsPosted(this.repository.calculateMaximumClaimsPerWeekPostedAfter(tenWeeksDelta)).build();

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final AdministratorDashboard object) {
		Dataset dataset = super.unbind(object, "administrators", "auditors", "clients", "consumers", "developers", "managers", "providers", "sponsors", "noticesWithEmailAndLinkRatio", "criticalObjectivesRatio", "nonCriticalObjectivesRatio",
			"averageRiskValue", "riskValueDeviation", "minimumRiskValue", "maximumRiskValue", "averageClaimsPosted", "claimsPostedDeviation", "minimumClaimsPosted", "maximumClaimsPosted");

		super.getResponse().addData(dataset);
	}
}
