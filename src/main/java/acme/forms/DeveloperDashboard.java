
package acme.forms;

import acme.client.data.AbstractForm;

public class DeveloperDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	Integer						totalTrainingModulesWithUpdateMoment;
	Integer						totalTrainingSessionsWithLink;
	Double						trainingModuleEstimatedTimeAverage;
	Double						trainingModuleEstimatedTimeDeviation;
	Integer						trainingModuleMinimunEstimatedTime;
	Integer						trainingModuleMaximumEstimatedTime;
}
