package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AdministratorDashboard extends AbstractForm{
	
	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	private Integer totalAdministrator;
	private Integer totalAuditor;
	private Integer totalClient;
	private Integer totalConsumer;
	private Integer totalDeveloper;
    private Integer totalManager;
    private Integer totalProvider;
    private Integer totalSponsor;
    private Double ratioNoticesWithEmailAndLink;
    private Double ratioCriticalObjectives;
    private Double ratioNonCriticalObjectives;
    private Double riskValueAverage;
    private Double riskValueDeviation;
    private Double riskValueMaximum;
    private Double riskValueMinimum;
    private Double claimsPostedAverage;
    private Double claimsPostedDeviation;
    private Integer claimsPostedMaximum;
    private Integer claimsPostedMinimum;
}
