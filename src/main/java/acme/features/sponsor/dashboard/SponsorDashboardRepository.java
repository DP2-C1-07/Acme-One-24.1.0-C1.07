
package acme.features.sponsor.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface SponsorDashboardRepository extends AbstractRepository {

	@Query("select count(i) from Invoice i where i.sponsorship.sponsor.id = :sponsorId and i.tax <= :percentage")
	long countSponsorInvoicesWithTaxLessOrEqualThan(int sponsorId, double percentage);

	@Query("select count(i) from Invoice i where i.sponsorship.sponsor.id = :sponsorId and i.link is not null and i.link != ''")
	long countSponsorshipsWithLink(int sponsorId);

	@Query("select avg(s.amount.amount) from Sponsorship s where s.sponsor.id = :sponsorId")
	Double calculateAverageSponsorshipAmount(int sponsorId);

	@Query("select stddev(s.amount.amount) from Sponsorship s where s.sponsor.id = :sponsorId")
	Double calculateDeviationSponsorshipAmount(int sponsorId);

	@Query("select min(s.amount.amount) from Sponsorship s where s.sponsor.id = :sponsorId")
	Double calculateMinimumSponsorshipAmount(int sponsorId);

	@Query("select max(s.amount.amount) from Sponsorship s where s.sponsor.id = :sponsorId")
	Double calculateMaximumSponsorshipAmount(int sponsorId);

	@Query("select avg(i.quantity.amount) from Invoice i where i.sponsorship.sponsor.id = :sponsorId")
	Double calculateAverageInvoiceQuantity(int sponsorId);

	@Query("select stddev(i.quantity.amount) from Invoice i where i.sponsorship.sponsor.id = :sponsorId")
	Double calculateDeviationInvoiceQuantity(int sponsorId);

	@Query("select min(i.quantity.amount) from Invoice i where i.sponsorship.sponsor.id = :sponsorId")
	Double calculateMinimumInvoiceQuantity(int sponsorId);

	@Query("select max(i.quantity.amount) from Invoice i where i.sponsorship.sponsor.id = :sponsorId")
	Double calculateMaximumInvoiceQuantity(int sponsorId);
}
