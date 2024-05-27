
package acme.features.sponsor.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Invoice;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceShowService extends AbstractService<Sponsor, Invoice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorInvoiceRepository repository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		Principal principal = super.getRequest().getPrincipal();
		Sponsor sponsor = this.repository.findSponsorById(principal.getActiveRoleId());

		int id = super.getRequest().getData("id", int.class);
		Invoice invoice = this.repository.findInvoiceById(id);

		boolean authorised = super.getRequest().getPrincipal().hasRole(sponsor) && invoice.getSponsorship().getSponsor().equals(sponsor);
		super.getResponse().setAuthorised(authorised);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		Invoice invoice = this.repository.findInvoiceById(id);

		super.getBuffer().addData(invoice);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "code", "registrationTime", "dueDate", "published", "quantity", "tax", "sponsorship.code");
		dataset.put("project.code", object.getSponsorship().getProject().getCode());
		dataset.put("totalAmount", object.getQuantity() == null ? null : object.getTotalAmount());

		super.getResponse().addData(dataset);
	}
}
