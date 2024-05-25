
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SponsorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	private Long				invoicesWithTaxLessOrEqualThan21Percent;
	private Long				sponsorshipsWithLink;

	private Double				averageSponsorshipAmount;
	private Double				deviationSponsorshipAmount;
	private Double				minimumSponsorshipAmount;
	private Double				maximumSponsorshipAmount;

	private Double				averageInvoiceQuantity;
	private Double				deviationInvoiceQuantity;
	private Double				minimumInvoiceQuantity;
	private Double				maximumInvoiceQuantity;
}
