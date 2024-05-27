
package acme.features.sponsor.invoice;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorships.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Repository
public interface SponsorInvoiceRepository extends AbstractRepository {

	@Query("select i from Invoice i where i.id = :id")
	Invoice findInvoiceById(int id);

	@Query("select i from Invoice i where i.code = :code")
	Invoice findInvoiceByCode(String code);

	@Query("select s from Sponsorship s where s.id = :id")
	Sponsorship findSponsorshipById(int id);

	@Query("select i from Invoice i where i.sponsorship.sponsor.id = :sponsorId")
	Collection<Invoice> findAllInvoicesBySponsorId(int sponsorId);

	@Query("select i from Invoice i where i.sponsorship.id = :sponsorshipId")
	Collection<Invoice> findAllInvoicesBySponsorshipId(int sponsorshipId);

	@Query("select s from Sponsor s where s.id = :id")
	Sponsor findSponsorById(int id);
}
