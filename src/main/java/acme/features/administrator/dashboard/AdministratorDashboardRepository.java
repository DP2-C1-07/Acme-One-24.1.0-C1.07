
package acme.features.administrator.dashboard;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface AdministratorDashboardRepository extends AbstractRepository {

	@Query("select count(u) from Administrator u")
	long countAdministrators();

	@Query("select count(u) from Auditor u")
	long countAuditors();

	@Query("select count(u) from Client u")
	long countClients();

	@Query("select count(u) from Consumer u")
	long countConsumers();

	@Query("select count(u) from Developer u")
	long countDevelopers();

	@Query("select count(u) from Manager u")
	long countManagers();

	@Query("select count(u) from Provider u")
	long countProviders();

	@Query("select count(u) from Sponsor u")
	long countSponsors();

	@Query("select count(n) from Notice n")
	long countNotices();

	@Query("select count(n) from Notice n where n.email is not null and n.email != '' and n.link is not null and n.link != ''")
	long countNoticesWithEmailAndLink();

	@Query("select count(o) from Objective o")
	long countObjectives();

	@Query("select count(o) from Objective o where o.critical = true")
	long countCriticalObjectives();

	@Query("select avg(r.impact * r.probability) from Risk r")
	Double calculateAverageRiskValue();

	@Query("select stddev(r.impact * r.probability) from Risk r")
	Double calculateRiskValueDeviation();

	@Query("select min(r.impact * r.probability) from Risk r")
	Double calculateMinimumRiskValue();

	@Query("select max(r.impact * r.probability) from Risk r")
	Double calculateMaximumRiskValue();

	@Query(value = "SELECT AVG(weekly_count) AS average_claims_per_week FROM (SELECT COUNT(*) AS weekly_count FROM claim WHERE instantiation_moment >= :date GROUP BY YEARWEEK(instantiation_moment)) AS weekly_counts", nativeQuery = true)
	Double calculateAverageClaimsPerWeekPostedAfter(Date date);

	@Query(value = "SELECT STDDEV(weekly_count) AS average_claims_per_week FROM (SELECT COUNT(*) AS weekly_count FROM claim WHERE instantiation_moment >= :date GROUP BY YEARWEEK(instantiation_moment)) AS weekly_counts", nativeQuery = true)
	Double calculateClaimsPerWeekDeviationPostedAfter(Date date);

	@Query(value = "SELECT MIN(weekly_count) AS average_claims_per_week FROM (SELECT COUNT(*) AS weekly_count FROM claim WHERE instantiation_moment >= :date GROUP BY YEARWEEK(instantiation_moment)) AS weekly_counts", nativeQuery = true)
	Long calculateMinimumClaimsPerWeekPostedAfter(Date date);

	@Query(value = "SELECT MAX(weekly_count) AS average_claims_per_week FROM (SELECT COUNT(*) AS weekly_count FROM claim WHERE instantiation_moment >= :date GROUP BY YEARWEEK(instantiation_moment)) AS weekly_counts", nativeQuery = true)
	Long calculateMaximumClaimsPerWeekPostedAfter(Date date);
}
