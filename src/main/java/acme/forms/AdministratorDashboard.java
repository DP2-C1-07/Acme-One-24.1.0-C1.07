
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AdministratorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	private long				administrators;
	private long				auditors;
	private long				clients;
	private long				consumers;
	private long				developers;
	private long				managers;
	private long				providers;
	private long				sponsors;
	private Double				noticesWithEmailAndLinkRatio;
	private Double				criticalObjectivesRatio;
	private Double				nonCriticalObjectivesRatio;
	private Double				averageRiskValue;
	private Double				riskValueDeviation;
	private Double				maximumRiskValue;
	private Double				minimumRiskValue;
	private Double				averageClaimsPosted;
	private Double				claimsPostedDeviation;
	private Long				maximumClaimsPosted;
	private Long				minimumClaimsPosted;
}
