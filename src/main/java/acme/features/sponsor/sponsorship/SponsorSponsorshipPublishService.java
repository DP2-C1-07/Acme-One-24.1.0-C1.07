
package acme.features.sponsor.sponsorship;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.sponsorships.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.sponsorships.SponsorshipType;
import acme.roles.Sponsor;
import acme.utils.Validators;

@Service
public class SponsorSponsorshipPublishService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository	sponsorSponsorshipRepository;

	@Autowired
	private Validators						validators;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		int id = super.getRequest().getData("id", int.class);
		Sponsorship sponsorship = this.sponsorSponsorshipRepository.findSponsorshipById(id);

		Principal principal = super.getRequest().getPrincipal();
		Sponsor sponsor = this.sponsorSponsorshipRepository.findSponsorById(principal.getActiveRoleId());

		boolean authorised = sponsorship != null && !sponsorship.isPublished() && super.getRequest().getPrincipal().hasRole(sponsor) && sponsorship.getSponsor().equals(sponsor);
		super.getResponse().setAuthorised(authorised);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		Sponsorship sponsorship = this.sponsorSponsorshipRepository.findSponsorshipById(id);

		super.getBuffer().addData(sponsorship);
	}

	@Override
	public void bind(final Sponsorship object) {
		assert object != null;

		super.bind(object, "code", "endDate", "amount", "type", "contactEmail", "link");

		Principal principal = super.getRequest().getPrincipal();
		Sponsor sponsor = this.sponsorSponsorshipRepository.findSponsorById(principal.getActiveRoleId());
		object.setSponsor(sponsor);

		int projectId = super.getRequest().getData("project", int.class);
		Project project = this.sponsorSponsorshipRepository.findProjectById(projectId);
		object.setProject(project);
	}

	@Override
	public void validate(final Sponsorship object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("project"))
			super.state(!object.getProject().isDraftMode(), "project", "sponsor.sponsorship.form.error.project-draft-mode");

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Sponsorship existing = this.sponsorSponsorshipRepository.findSponsorshipByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "sponsor.sponsorship.form.error.duplicated-code");
		}

		if (!super.getBuffer().getErrors().hasErrors("endDate")) {
			super.state(object.getMoment().toInstant().plus(30, ChronoUnit.DAYS).isBefore(object.getEndDate().toInstant()), "endDate", "sponsor.sponsorship.form.error.endDate-one-month");
			Date maxDate = MomentHelper.parse("2200-12-31 23:59", "yyyy-MM-dd HH:mm");
			super.state(MomentHelper.isBeforeOrEqual(object.getEndDate(), maxDate), "endDate", "moment.error.after-max-moment");
		}

		if (!super.getBuffer().getErrors().hasErrors("amount")) {
			super.state(object.getAmount().getAmount() >= 0, "amount", "money.error.negative-amount");
			super.state(object.getAmount().getAmount() <= 1000000, "amount", "money.error.exceeded-maximum");
			super.state(this.validators.moneyValidator(object.getAmount().getCurrency()), "amount", "money.error.unsupported-currency");
		}

		Collection<Invoice> invoices = this.sponsorSponsorshipRepository.findAllInvoicesBySponsorshipId(object.getId());
		if (!super.getBuffer().getErrors().hasErrors("amount")) {
			boolean allInvoicesHaveSameCurrencyThanSponsorship = invoices.stream().allMatch(invoice -> invoice.getQuantity().getCurrency().equalsIgnoreCase(object.getAmount().getCurrency()));
			super.state(allInvoicesHaveSameCurrencyThanSponsorship, "amount", "sponsor.sponsorship.form.error.different-currency");
		}

		if (!super.getBuffer().getErrors().hasErrors("amount")) {
			boolean invoicesAmountAddUp = invoices.stream().map(Invoice::getTotalAmount).mapToDouble(Money::getAmount).sum() == object.getAmount().getAmount();
			super.state(invoicesAmountAddUp, "amount", "sponsor.sponsorship.form.error.wrong-amount");
		}

		if (!super.getBuffer().getErrors().hasErrors("*"))
			super.state(invoices.stream().allMatch(Invoice::isPublished), "*", "sponsor.sponsorship.form.error.not-all-invoices-published");
	}

	@Override
	public void perform(final Sponsorship object) {
		assert object != null;
		object.setPublished(true);
		this.sponsorSponsorshipRepository.save(object);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "code", "published", "moment", "endDate", "amount", "type", "contactEmail", "link");

		Collection<Project> projects = object.isPublished() ? Collections.singletonList(object.getProject()) : this.sponsorSponsorshipRepository.findPublishedProjects();
		if (object.getProject() != null && !projects.contains(object.getProject()))
			projects.add(object.getProject());

		dataset.put("types", SelectChoices.from(SponsorshipType.class, object.getType()));
		dataset.put("projects", SelectChoices.from(projects, "code", object.getProject()));

		super.getResponse().addData(dataset);
	}
}
