
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	Integer						totalStaticCodeAudits;
	Integer						totalDynamicCodeAudits;

	Double						averageAuditRecord;
	Double						deviationAuditRecord;
	Integer						minimumAuditRecord;
	Integer						maximumAuditRecord;

	Double						averageTimeOfPeriodInAuditRecord;
	Double						deviationTimeOfPeriodInAuditRecord;
	Integer						minimumTimeOfPeriodInAuditRecord;
	Integer						maximumTimeOfPeriodInAuditRecord;

}
