
package acme.features.administrator.dashboard;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.claims.Claim;

@Repository
public interface AdministratorDashboardRepository extends AbstractRepository {

	@Query("select count(a) from Administrator a")
	Integer totalAdministrator();

	@Query("select count(a) from Auditor a")
	Integer totalAuditor();

	@Query("select count(c) from Client c")
	Integer totalClient();

	@Query("select count(c) from Consumer c")
	Integer totalConsumer();

	@Query("select count(d) from Developer d")
	Integer totalDeveloper();

	@Query("select count(m) from Manager m")
	Integer totalManager();

	@Query("select count(p) from Provider p")
	Integer totalProvider();

	@Query("select count(s) from Sponsor s")
	Integer totalSponsor();

	@Query("select count(n) from Notice n where n.email is not null and n.link is not null")
	Integer countNoticesWithEmailAndLink();

	@Query("select count(n) from Notice n")
	Integer countTotalNotices();

	@Query("select count(o) from Objective o where o.critical = true")
	Integer criticalObjectives();

	@Query("select count(o) from Objective o where o.critical = false")
	Integer nonCriticalObjectives();

	@Query("select count(o) from Objective o")
	Integer totalObjectives();

	@Query("select avg(r.impact * r.probability) from Risk r")
	Double riskValueAverage();

	@Query("select stddev(r.impact * r.probability) from Risk r")
	Double riskValueDeviation();

	@Query("select min(r.impact * r.probability) from Risk r")
	Double riskValueMinimum();

	@Query("select c from Claim c where c.draftMode = false")
	List<Claim> totalClaims();
}
