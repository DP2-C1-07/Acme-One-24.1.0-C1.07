
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipListMineService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository sponsorSponsorshipRepository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Principal principal = super.getRequest().getPrincipal();
		Collection<Sponsorship> sponsorships = this.sponsorSponsorshipRepository.findAllBySponsorId(principal.getActiveRoleId());

		super.getBuffer().addData(sponsorships);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "code", "moment", "durationDays", "amount", "type", "project.code");

		super.getResponse().addData(dataset);
	}
}
