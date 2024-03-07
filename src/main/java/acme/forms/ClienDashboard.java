package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienDashboard extends AbstractForm{
	
	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	Double totalProgressLogsWithCompletenessBelow25;
	Double totalProgressLogsWithCompletenessBetwenn25And50;
	Double totalProgressLogsWithCompletenessBetwenn50And75;
	Double totalProgressLogsWithCompletenessAbove75;
	Double averageBudget;
	Double deviationBudget;
	Double minimumBudget;
	Double maximumBudget;
}
