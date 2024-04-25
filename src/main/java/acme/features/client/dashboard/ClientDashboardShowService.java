
package acme.features.client.dashboard;


import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
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
	    ClientDashboard dashboard = new ClientDashboard();
	    Principal principal = super.getRequest().getPrincipal();

	    Collection<Double> budgets = this.repository.findBudgetAmountsByClientId(principal.getActiveRoleId());
	    Map<String, Double> progressMap = new HashMap<>();
	    
	    // progressLogByCompletenessRate
	    Collection<Double> progressLogs = this.repository.findProgressLogsCompletenessByClientId(principal.getActiveRoleId());
	    progressMap.put("25", countProgressLogs(progressLogs, 0, 25));
	    progressMap.put("50", countProgressLogs(progressLogs, 25, 50));
	    progressMap.put("75", countProgressLogs(progressLogs, 50, 75));
	    progressMap.put("100", countProgressLogs(progressLogs, 75, 100));
	    
	    dashboard.setProgressLogByCompletenessRate(progressMap);
	    
	    // stats
	    Double averageBudget = calculateAverage(budgets);
	    Double deviationBudget = calculateStandardDeviation(budgets);
	    Double minimumBudget = calculateMinimum(budgets);
	    Double maximumBudget = calculateMaximum(budgets);
	    
	    dashboard.setAverageBudget(averageBudget);
	    dashboard.setDeviationBudget(deviationBudget);
	    dashboard.setMinimumBudget(minimumBudget);
	    dashboard.setMaximumBudget(maximumBudget);
	    
	    super.getBuffer().addData(dashboard);
	}


	@Override
	public void unbind(final ClientDashboard object) {
		
		String emptyMessage;
		Locale local;
		Dataset dataset;
		
		local = super.getRequest().getLocale();
		emptyMessage = local.equals(Locale.ENGLISH) ? "No Data" : "Sin Datos";
		
		dataset = super.unbind(object, "progressLogByCompletenessRate", "averageBudget", "deviationBudget",
			"minimumBudget", "maximumBudget");
		
		dataset.put("emptyMessage", emptyMessage);
		
		super.getResponse().addData(dataset);

	}
	
	private Double countProgressLogs(Collection<Double> progressLogsCompleteness, double lowerBound, double upperBound) {
	    long count = progressLogsCompleteness.stream()
	                     .filter(log -> log >= lowerBound/100 && log < upperBound/100)
	                     .count();
	    return (double) count;
	}

	private Double calculateAverage(final Collection<Double> budgets) {
		return budgets.isEmpty() 
			? null
			: budgets.stream().collect(Collectors.averagingDouble(budget -> budget));
	}
	
	private Double calculateStandardDeviation(final Collection<Double> budgets) {
		if (budgets.isEmpty())
			return null;
		double mean = this.calculateAverage(budgets);
		double temp = 0;
		for (Double budget : budgets)
			temp += (budget - mean) * (budget - mean);
		return Math.sqrt(temp / budgets.size());
	}

	private Double calculateMinimum(final Collection<Double> budgets) {
		return budgets.stream().min(Double::compare).orElse(null);
	}

	private Double calculateMaximum(final Collection<Double> budgets) {
		return budgets.stream().max(Double::compare).orElse(null);
	}

}