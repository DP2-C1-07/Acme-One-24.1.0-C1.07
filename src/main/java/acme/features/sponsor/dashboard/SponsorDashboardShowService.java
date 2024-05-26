
package acme.features.sponsor.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.SponsorDashboard;
import acme.roles.Sponsor;

@Service
public class SponsorDashboardShowService extends AbstractService<Sponsor, SponsorDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorDashboardRepository repository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		boolean isSponsor = super.getRequest().getPrincipal().hasRole(Sponsor.class);
		super.getResponse().setAuthorised(isSponsor);
	}

	@Override
	public void load() {
		int sponsorId = super.getRequest().getPrincipal().getActiveRoleId();

		SponsorDashboard dashboard = SponsorDashboard.builder().invoicesWithTaxLessOrEqualThan21Percent(this.repository.countSponsorInvoicesWithTaxLessOrEqualThan(sponsorId, 0.21)).sponsorshipsWithLink(this.repository.countSponsorshipsWithLink(sponsorId))

			.averageSponsorshipAmount(this.repository.calculateAverageSponsorshipAmount(sponsorId)).deviationSponsorshipAmount(this.repository.calculateDeviationSponsorshipAmount(sponsorId))
			.minimumSponsorshipAmount(this.repository.calculateMinimumSponsorshipAmount(sponsorId)).maximumSponsorshipAmount(this.repository.calculateMaximumSponsorshipAmount(sponsorId))

			.averageInvoiceQuantity(this.repository.calculateAverageInvoiceQuantity(sponsorId)).deviationInvoiceQuantity(this.repository.calculateDeviationInvoiceQuantity(sponsorId))
			.minimumInvoiceQuantity(this.repository.calculateMinimumInvoiceQuantity(sponsorId)).maximumInvoiceQuantity(this.repository.calculateMaximumInvoiceQuantity(sponsorId)).build();

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final SponsorDashboard object) {
		Dataset dataset = super.unbind(object, "invoicesWithTaxLessOrEqualThan21Percent", "sponsorshipsWithLink", "averageSponsorshipAmount", "deviationSponsorshipAmount", "minimumSponsorshipAmount", "maximumSponsorshipAmount", "averageInvoiceQuantity",
			"deviationInvoiceQuantity", "minimumInvoiceQuantity", "maximumInvoiceQuantity");

		super.getResponse().addData(dataset);
	}
}
