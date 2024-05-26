
package acme.features.sponsor.invoice;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Invoice;
import acme.roles.Sponsor;
import acme.utils.Validators;

@Service
public class SponsorInvoiceUpdateService extends AbstractService<Sponsor, Invoice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorInvoiceRepository	repository;

	@Autowired
	private Validators					validators;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		Principal principal = super.getRequest().getPrincipal();
		Sponsor sponsor = this.repository.findSponsorById(principal.getActiveRoleId());

		int id = super.getRequest().getData("id", int.class);
		Invoice invoice = this.repository.findInvoiceById(id);

		boolean authorised = super.getRequest().getPrincipal().hasRole(sponsor) && invoice.getSponsorship().getSponsor().equals(sponsor) && !invoice.isPublished();
		super.getResponse().setAuthorised(authorised);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		Invoice invoice = this.repository.findInvoiceById(id);

		super.getBuffer().addData(invoice);
	}

	@Override
	public void bind(final Invoice object) {
		assert object != null;

		super.bind(object, "code", "dueDate", "quantity", "tax", "link");
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

		if (!super.getBuffer().getErrors().hasErrors("quantity")) {
			super.state(object.getQuantity().getAmount() > 0, "quantity", "money.error.must-be-positive");
			super.state(object.getQuantity().getAmount() <= 1000000, "quantity", "money.error.exceeded-maximum");
			super.state(this.validators.moneyValidator(object.getQuantity().getCurrency()), "quantity", "money.error.unsupported-currency");
			super.state(object.getQuantity().getCurrency().equalsIgnoreCase(object.getSponsorship().getAmount().getCurrency()), "quantity", "sponsor.invoice.form.error.different-currency");
		}

		if (!super.getBuffer().getErrors().hasErrors("dueDate"))
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

		Dataset dataset = super.unbind(object, "code", "registrationTime", "dueDate", "published", "quantity", "tax", "sponsorship.code");
		dataset.put("project.code", object.getSponsorship().getProject().getCode());
		dataset.put("totalAmount", object.getQuantity() == null ? null : object.getTotalAmount());

		super.getResponse().addData(dataset);
	}
}
