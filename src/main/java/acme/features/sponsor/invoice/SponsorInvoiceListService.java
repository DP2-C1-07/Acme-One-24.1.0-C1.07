
package acme.features.sponsor.invoice;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceListService extends AbstractService<Sponsor, Invoice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorInvoiceRepository repository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		Principal principal = super.getRequest().getPrincipal();
		Sponsor sponsor = this.repository.findSponsorById(principal.getActiveRoleId());

		boolean authorised = super.getRequest().getPrincipal().hasRole(sponsor);

		if (authorised && super.getRequest().hasData("sponsorshipId", Integer.class)) {
			Integer sponsorshipId = super.getRequest().getData("sponsorshipId", Integer.class);
			Sponsorship sponsorship = this.repository.findSponsorshipById(sponsorshipId);
			authorised = sponsorship.getSponsor().equals(sponsor);
		}

		super.getResponse().setAuthorised(authorised);
	}

	@Override
	public void load() {
		Collection<Invoice> invoices;

		if (super.getRequest().hasData("sponsorshipId")) {
			Integer sponsorshipId = super.getRequest().getData("sponsorshipId", Integer.class);
			invoices = this.repository.findAllInvoicesBySponsorshipId(sponsorshipId);

			Sponsorship sponsorship = this.repository.findSponsorshipById(sponsorshipId);
			super.getResponse().addGlobal("modifiable", !sponsorship.isPublished());
		} else {
			int sponsorId = super.getRequest().getPrincipal().getActiveRoleId();
			invoices = this.repository.findAllInvoicesBySponsorId(sponsorId);
		}

		super.getBuffer().addData(invoices);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "code", "registrationTime", "dueDate", "published");
		dataset.put("projectCode", object.getSponsorship().getProject().getCode());
		dataset.put("sponsorshipCode", object.getSponsorship().getCode());
		dataset.put("totalAmount", object.getTotalAmount());

		super.getResponse().addData(dataset);
	}
}
