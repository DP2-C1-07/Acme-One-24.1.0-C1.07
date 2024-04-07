
package acme.features.manager.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface ManagerDashboardRepository extends AbstractRepository {

	@Query("select count(u) from UserStory u where u.priority = COULD")
	int totalCouldUserStories();

	@Query("select count(u) from UserStory u where u.priority = SHOULD")
	int totalShouldUserStories();

	@Query("select count(u) from UserStory u where u.priority = MUST")
	int totalMustUserStories();

	@Query("select count(u) from UserStory u where u.priority = WONT")
	int totalWontUserStories();

	@Query("select avg(u.estimatedCost) from UserStory u")
	Double userStoryEstimatedCostAverage();

	@Query("SELECT SQRT(SUM((u.estimatedCost - avgCost) * (u.estimatedCost - avgCost)) / COUNT(u)) FROM UserStory u, (SELECT AVG(u.estimatedCost) AS avgCost FROM UserStory u)")
	Double userStoryEstimatedCostDeviation();

	@Query("select max(u.estimatedCost) from UserStory u")
	Integer maximumUserStoryEstimatedCost();

	@Query("select min(u.estimatedCost) from UserStory u")
	Integer minimumUserStoryEstimatedCost();

	@Query("select avg(p.cost) from Project p")
	Double projectCostAverage();

	@Query("SELECT SQRT(SUM((p.cost - avgCost) * (p.cost - avgCost)) / COUNT(p)) FROM Project p, (SELECT AVG(p.cost) AS avgCost FROM Project p)")
	Double projectCostDeviation();

	@Query("select max(p.cost) from Project p")
	Integer maximumProjectCost();

	@Query("select min(p.cost) from Project p")
	Integer minimumProjectCost();
}
