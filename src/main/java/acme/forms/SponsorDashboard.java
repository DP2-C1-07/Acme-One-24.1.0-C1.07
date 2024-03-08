
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SponsorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	private Integer				invoicesWithTaxLessOrEqualThan21Percent;
	private Integer				sponsorshipsWithLink;

	private Double				averageSponsorshipAmount;
	private Double				deviationSponsorshipAmount;
	private Double				minimumSponsorshipAmount;
	private Double				maximumSponsorshipAmount;

	private Double				averageInvoiceQuantity;
	private Double				deviationInvoiceQuantity;
	private Double				minimumInvoiceQuantity;
	private Double				maximumInvoiceQuantity;
}
