
package acme.features.sponsor.sponsorship;

import java.time.temporal.ChronoUnit;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.sponsorships.SponsorshipType;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipCreateService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository sponsorSponsorshipRepository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		Principal principal = super.getRequest().getPrincipal();
		Sponsor sponsor = this.sponsorSponsorshipRepository.findSponsorById(principal.getActiveRoleId());

		boolean authorised = super.getRequest().getPrincipal().hasRole(sponsor);
		super.getResponse().setAuthorised(authorised);
	}

	@Override
	public void load() {
		Principal principal = super.getRequest().getPrincipal();
		Sponsor sponsor = this.sponsorSponsorshipRepository.findSponsorById(principal.getActiveRoleId());

		Sponsorship sponsorship = new Sponsorship();
		sponsorship.setSponsor(sponsor);

		super.getBuffer().addData(sponsorship);
	}

	@Override
	public void bind(final Sponsorship object) {
		assert object != null;

		super.bind(object, "code", "moment", "endDate", "amount", "type", "contactEmail", "link");

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

		if (!super.getBuffer().getErrors().hasErrors("moment") && !super.getBuffer().getErrors().hasErrors("endDate"))
			super.state(object.getMoment().toInstant().plus(30, ChronoUnit.DAYS).isBefore(object.getEndDate().toInstant()), "endDate", "sponsor.sponsorship.form.error.endDate-one-month");
	}

	@Override
	public void perform(final Sponsorship object) {
		assert object != null;
		this.sponsorSponsorshipRepository.save(object);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "code", "published", "moment", "endDate", "amount", "type", "contactEmail", "link", "project.code");

		Collection<Project> projects = this.sponsorSponsorshipRepository.findAllProjects();
		dataset.put("types", SelectChoices.from(SponsorshipType.class, object.getType()));

		if (!object.isPublished())
			dataset.put("projects", SelectChoices.from(projects, "code", null));

		super.getResponse().addData(dataset);
	}
}
