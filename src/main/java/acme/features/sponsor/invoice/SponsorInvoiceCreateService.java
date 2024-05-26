
package acme.features.sponsor.invoice;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceCreateService extends AbstractService<Sponsor, Invoice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorInvoiceRepository repository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		Principal principal = super.getRequest().getPrincipal();
		Sponsor sponsor = this.repository.findSponsorById(principal.getActiveRoleId());

		Integer sponsorshipId = super.getRequest().getData("sponsorshipId", Integer.class);
		Sponsorship sponsorship = this.repository.findSponsorshipById(sponsorshipId);

		boolean authorised = super.getRequest().getPrincipal().hasRole(sponsor) && sponsorship.getSponsor().equals(sponsor);
		super.getResponse().setAuthorised(authorised);
	}

	@Override
	public void load() {
		Integer sponsorshipId = super.getRequest().getData("sponsorshipId", Integer.class);
		Sponsorship sponsorship = this.repository.findSponsorshipById(sponsorshipId);
		//		super.getResponse().addGlobal("sponsorshipId", sponsorshipId);

		Invoice invoice = new Invoice();
		invoice.setSponsorship(sponsorship);

		super.getBuffer().addData(invoice);
	}

	@Override
	public void bind(final Invoice object) {
		assert object != null;

		super.bind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link");
	}

	@Override
	public void validate(final Invoice object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("*"))
			super.state(!object.getSponsorship().isPublished(), "*", "sponsor.invoice.form.error.sponsorship-published");

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Invoice existing = this.repository.findInvoiceByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "sponsor.invoice.form.error.duplicated-code");
		}

		if (!super.getBuffer().getErrors().hasErrors("quantity"))
			super.state(object.getQuantity().getAmount() > 0, "quantity", "sponsor.invoice.form.error.wrong-quantity");

		if (!super.getBuffer().getErrors().hasErrors("quantity"))
			super.state(object.getQuantity().getCurrency().equalsIgnoreCase(object.getSponsorship().getAmount().getCurrency()), "quantity", "sponsor.invoice.form.error.different-currency");

		if (!super.getBuffer().getErrors().hasErrors("registrationTime") && !super.getBuffer().getErrors().hasErrors("dueDate"))
			super.state(object.getRegistrationTime().toInstant().plus(30, ChronoUnit.DAYS).isBefore(object.getDueDate().toInstant()), "dueDate", "sponsor.invoice.form.error.dueDate-one-month");
	}

	@Override
	public void perform(final Invoice object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "code", "registrationTime", "dueDate", "published");
		dataset.put("projectCode", object.getSponsorship().getProject().getCode());
		dataset.put("sponsorshipCode", object.getSponsorship().getCode());
		dataset.put("totalAmount", object.getQuantity() == null ? null : object.getTotalAmount());

		super.getResponse().addData(dataset);
	}
}
