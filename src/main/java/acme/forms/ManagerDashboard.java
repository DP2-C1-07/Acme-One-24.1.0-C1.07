
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	Integer						totalCouldUserStories;
	Integer						totalShouldUserStories;
	Integer						totalMustUserStories;
	Integer						totalWontUserStories;
	Double						userStoryEstimatedCostAverage;
	Double						userStoryEstimatedCostDeviation;
	Integer						maximumUserStoryEstimatedCost;
	Integer						minimumUserStoryEstimatedCost;
	Double						projectCostAverage;
	Double						projectCostDeviation;
	Integer						maximumprojectCost;
	Integer						minimumprojectCost;
}
