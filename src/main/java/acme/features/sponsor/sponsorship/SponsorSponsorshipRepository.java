
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorships.Sponsorship;

@Repository
public interface SponsorSponsorshipRepository extends AbstractRepository {

	@Query("select sponsorship from Sponsorship sponsorship where sponsorship.sponsor.id = :sponsorId")
	Collection<Sponsorship> findAllBySponsorId(int sponsorId);

}
