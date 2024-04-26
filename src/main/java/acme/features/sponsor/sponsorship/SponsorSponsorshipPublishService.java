
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.sponsorships.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.sponsorships.SponsorshipType;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipPublishService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository sponsorSponsorshipRepository;


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

		super.bind(object, "code", "moment", "durationDays", "amount", "type", "contactEmail", "link");

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

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Sponsorship existing = this.sponsorSponsorshipRepository.findSponsorshipByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "sponsor.sponsorship.form.error.duplicated-code");
		}

		Collection<Invoice> invoices = this.sponsorSponsorshipRepository.findAllInvoicesBySponsorshipId(object.getId());
		if (!super.getBuffer().getErrors().hasErrors("amount")) {
			boolean allInvoicesHaveSameCurrencyThanSponsorship = invoices.stream().allMatch(invoice -> invoice.getQuantity().getCurrency().equalsIgnoreCase(object.getAmount().getCurrency()));
			super.state(allInvoicesHaveSameCurrencyThanSponsorship, "amount", "sponsor.sponsorship.form.error.different-currency");
		}

		if (!super.getBuffer().getErrors().hasErrors("amount")) {
			boolean invoicesAmountAddUp = invoices.stream().map(Invoice::getTotalAmount).mapToDouble(Money::getAmount).sum() >= object.getAmount().getAmount();
			super.state(invoicesAmountAddUp, "amount", "sponsor.sponsorship.form.error.wrong-amount");
		}
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

		Dataset dataset = super.unbind(object, "code", "published", "moment", "durationDays", "amount", "type", "contactEmail", "link", "project.code");

		Collection<Project> projects = this.sponsorSponsorshipRepository.findAllProjects();
		dataset.put("types", SelectChoices.from(SponsorshipType.class, object.getType()));

		if (!object.isPublished())
			dataset.put("projects", SelectChoices.from(projects, "code", object.getProject()));

		super.getResponse().addData(dataset);
	}
}
