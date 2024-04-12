
package acme.features.manager.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface ManagerDashboardRepository extends AbstractRepository {

	@Query("select count(u) from UserStory u where u.priority = acme.entities.userstories.UserStoryPriority.COULD")
	int totalCouldUserStories();

	@Query("select count(u) from UserStory u where u.priority = acme.entities.userstories.UserStoryPriority.SHOULD")
	int totalShouldUserStories();

	@Query("select count(u) from UserStory u where u.priority = acme.entities.userstories.UserStoryPriority.MUST")
	int totalMustUserStories();

	@Query("select count(u) from UserStory u where u.priority = acme.entities.userstories.UserStoryPriority.WONT")
	int totalWontUserStories();

	@Query("select avg(u.estimatedCost) from UserStory u")
	Double userStoryEstimatedCostAverage();

	@Query("select max(u.estimatedCost) from UserStory u")
	Integer maximumUserStoryEstimatedCost();

	@Query("select min(u.estimatedCost) from UserStory u")
	Integer minimumUserStoryEstimatedCost();

	@Query("select avg(p.cost) from Project p")
	Double projectCostAverage();

	@Query("select max(p.cost) from Project p")
	Integer maximumProjectCost();

	@Query("select min(p.cost) from Project p")
	Integer minimumProjectCost();

	@Query("select stddev(u.estimatedCost) from UserStory u")
	Double userStoryEstimatedCostDeviation();

	@Query("select stddev(p.cost) from Project p")
	Double projectCostDeviation();
}
