
package acme.features.administrator.dashboard;

import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.claims.Claim;
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

		Map<Integer, Long> claimCountByWeek = this.calculateClaimCountByWeek();
		double averageClaimsPosted = claimCountByWeek.values().stream().map(Long::doubleValue).mapToDouble(Double::valueOf).average().orElse(0);
		double variance = claimCountByWeek.values().stream().mapToDouble(count -> Math.pow(count - averageClaimsPosted, 2)).sum() / claimCountByWeek.size();

		AdministratorDashboard dashboard = AdministratorDashboard.builder().administrators(this.repository.countAdministrators()).auditors(this.repository.countAuditors()).clients(this.repository.countClients()).consumers(this.repository.countConsumers())
			.developers(this.repository.countDevelopers()).managers(this.repository.countManagers()).providers(this.repository.countProviders()).sponsors(this.repository.countSponsors())

			.noticesWithEmailAndLinkRatio(noticesWithEmailAndLinkRatio).criticalObjectivesRatio(criticalObjectivesRatio).nonCriticalObjectivesRatio(nonCriticalObjectivesRatio)

			.averageRiskValue(this.repository.calculateAverageRiskValue()).riskValueDeviation(this.repository.calculateRiskValueDeviation()).minimumRiskValue(this.repository.calculateMinimumRiskValue())
			.maximumRiskValue(this.repository.calculateMaximumRiskValue())

			.averageClaimsPosted(averageClaimsPosted).claimsPostedDeviation(Math.sqrt(variance)).minimumClaimsPosted(Collections.min(claimCountByWeek.values())).maximumClaimsPosted(Collections.max(claimCountByWeek.values())).build();

		super.getBuffer().addData(dashboard);
	}

	private Map<Integer, Long> calculateClaimCountByWeek() {
		Date tenWeeksDelta = MomentHelper.deltaFromCurrentMoment(-10, ChronoUnit.WEEKS);
		List<Claim> recentClaims = this.repository.findClaimsPostedAfter(tenWeeksDelta);

		Map<Integer, Long> claimsCountByWeek = recentClaims.stream().map(c -> (int) MomentHelper.computeDuration(c.getInstantiationMoment(), MomentHelper.getCurrentMoment()).toDays() / 7)
			.collect(Collectors.groupingBy(Function.identity(), HashMap::new, Collectors.counting()));

		for (int i = 0; i < 10; i++)
			claimsCountByWeek.putIfAbsent(i, 0L);

		return claimsCountByWeek;
	}

	@Override
	public void unbind(final AdministratorDashboard object) {
		Dataset dataset = super.unbind(object, "administrators", "auditors", "clients", "consumers", "developers", "managers", "providers", "sponsors", "noticesWithEmailAndLinkRatio", "criticalObjectivesRatio", "nonCriticalObjectivesRatio",
			"averageRiskValue", "riskValueDeviation", "minimumRiskValue", "maximumRiskValue", "averageClaimsPosted", "claimsPostedDeviation", "minimumClaimsPosted", "maximumClaimsPosted");

		super.getResponse().addData(dataset);
	}
}
