
package acme.features.administrator.dashboard;

import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
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

	// Integerernal state ---------------------------------------------------------

	@Autowired
	private AdministratorDashboardRepository repository;


	// AbstractService Inteeface ----------------------------------------------
	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AdministratorDashboard dashboard;

		Integer totalAdministrator;
		Integer totalAuditor;
		Integer totalClient;
		Integer totalConsumer;
		Integer totalDeveloper;
		Integer totalManager;
		Integer totalProvider;
		Integer totalSponsor;
		Double ratioNoticesWithEmailAndLink;
		Double ratioCriticalObjectives;
		Double ratioNonCriticalObjectives;
		Double riskValueAverage;
		Double riskValueDeviation;
		Double riskValueMinimum;
		Double claimsPostedAverage;
		Double claimsPostedDeviation;
		Integer claimsPostedMaximum;
		Integer claimsPostedMinimum;

		totalAdministrator = this.repository.totalAdministrator();
		totalAuditor = this.repository.totalAuditor();
		totalClient = this.repository.totalClient();
		totalConsumer = this.repository.totalConsumer();
		totalDeveloper = this.repository.totalDeveloper();
		totalManager = this.repository.totalManager();
		totalProvider = this.repository.totalProvider();
		totalSponsor = this.repository.totalSponsor();
		ratioNoticesWithEmailAndLink = 1. * this.repository.countNoticesWithEmailAndLink() / (1. * this.repository.countTotalNotices());
		ratioCriticalObjectives = 1. * this.repository.criticalObjectives() / (1. * this.repository.nonCriticalObjectives());
		ratioNonCriticalObjectives = 1. * this.repository.nonCriticalObjectives() / (1. * this.repository.nonCriticalObjectives());
		riskValueAverage = this.repository.riskValueAverage();
		riskValueDeviation = this.repository.riskValueDeviation();
		riskValueMinimum = this.repository.riskValueMinimum();

		Date tenWeeksDelta = MomentHelper.deltaFromCurrentMoment(-10, ChronoUnit.WEEKS);

		List<Claim> recentClaims = this.repository.recentClaims(tenWeeksDelta);

		Function<Claim, Integer> claimToWeeks = c -> (int) MomentHelper.computeDuration(c.getInstantiationMoment(), MomentHelper.getCurrentMoment()).toDays() / 7;

		Map<Integer, Long> claimsCountByWeek = new HashMap<>();
		for (int i = 0; i < 10; i++)
			claimsCountByWeek.put(i, 0L);

		Map<Integer, Long> recentClaimsCountByWeek = recentClaims.stream().map(claimToWeeks).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		for (Map.Entry<Integer, Long> entry : recentClaimsCountByWeek.entrySet())
			claimsCountByWeek.put(entry.getKey(), entry.getValue());

		DoubleSummaryStatistics stats = claimsCountByWeek.values().stream().mapToDouble(Long::doubleValue).summaryStatistics();

		claimsPostedAverage = stats.getAverage();

		Double variance = claimsCountByWeek.isEmpty() ? 0 : claimsCountByWeek.values().stream().mapToDouble(count -> Math.pow(count - claimsPostedAverage, 2)).sum() / claimsCountByWeek.size();

		claimsPostedDeviation = Math.sqrt(variance);
		claimsPostedMaximum = claimsCountByWeek.isEmpty() ? 0 : Collections.max(claimsCountByWeek.values()).intValue();
		claimsPostedMinimum = claimsCountByWeek.isEmpty() ? 0 : Collections.min(claimsCountByWeek.values()).intValue();

		dashboard = new AdministratorDashboard();
		dashboard.setTotalAdministrator(totalAdministrator);
		dashboard.setTotalAuditor(totalAuditor);
		dashboard.setTotalClient(totalClient);
		dashboard.setTotalConsumer(totalConsumer);
		dashboard.setTotalDeveloper(totalDeveloper);
		dashboard.setTotalManager(totalManager);
		dashboard.setTotalProvider(totalProvider);
		dashboard.setTotalSponsor(totalSponsor);
		dashboard.setRatioNoticesWithEmailAndLink(ratioNoticesWithEmailAndLink);
		dashboard.setRatioCriticalObjectives(ratioCriticalObjectives);
		dashboard.setRatioNonCriticalObjectives(ratioNonCriticalObjectives);
		dashboard.setRiskValueAverage(riskValueAverage);
		dashboard.setRiskValueDeviation(riskValueDeviation);
		dashboard.setRiskValueMinimum(riskValueMinimum);
		dashboard.setClaimsPostedAverage(claimsPostedAverage);
		dashboard.setClaimsPostedDeviation(claimsPostedDeviation);
		dashboard.setClaimsPostedMaximum(claimsPostedMaximum);
		dashboard.setClaimsPostedMinimum(claimsPostedMinimum);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final AdministratorDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, //
			"totalAdministrator", "totalAuditor", "totalClient", "totalConsumer", // 
			"totalDeveloper", "totalManager", "totalProvider", "totalSponsor", //
			"ratioNoticesWithEmailAndLink", "ratioCriticalObjectives", "ratioNonCriticalObjectives", "riskValueAverage", //
			"riskValueDeviation", "riskValueMinimum", "claimsPostedAverage", "claimsPostedDeviation", "claimsPostedMaximum", "claimsPostedMinimum");

		super.getResponse().addData(dataset);
	}
}
