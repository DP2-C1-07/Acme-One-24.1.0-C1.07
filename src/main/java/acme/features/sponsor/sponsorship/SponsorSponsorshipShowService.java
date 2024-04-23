
package acme.features.sponsor.sponsorship;

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
public class SponsorSponsorshipShowService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository sponsorSponsorshipRepository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		int id = super.getRequest().getData("id", int.class);
		Principal principal = super.getRequest().getPrincipal();

		Sponsorship sponsorship = this.sponsorSponsorshipRepository.findSponsorshipById(id);
		Sponsor sponsor = this.sponsorSponsorshipRepository.findSponsorById(principal.getActiveRoleId());

		boolean authorised = sponsorship != null && super.getRequest().getPrincipal().hasRole(sponsor) && sponsorship.getSponsor().equals(sponsor);
		super.getResponse().setAuthorised(authorised);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		Sponsorship sponsorship = this.sponsorSponsorshipRepository.findSponsorshipById(id);

		super.getBuffer().addData(sponsorship);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "code", "moment", "durationDays", "amount", "type", "contactEmail", "link", "project.code");

		Collection<Project> projects = this.sponsorSponsorshipRepository.findAllProjects();
		dataset.put("types", SelectChoices.from(SponsorshipType.class, object.getType()));
		dataset.put("projects", SelectChoices.from(projects, "code", object.getProject()));

		super.getResponse().addData(dataset);
	}
}
