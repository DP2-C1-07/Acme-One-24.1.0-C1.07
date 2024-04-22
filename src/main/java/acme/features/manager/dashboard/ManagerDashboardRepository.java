
package acme.features.manager.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface ManagerDashboardRepository extends AbstractRepository {

	@Query("select count(u) from UserStory u where u.priority = acme.entities.userstories.UserStoryPriority.COULD and u.manager.id = :managerId")
	int totalCouldUserStories(int managerId);

	@Query("select count(u) from UserStory u where u.priority = acme.entities.userstories.UserStoryPriority.SHOULD and u.manager.id = :managerId")
	int totalShouldUserStories(int managerId);

	@Query("select count(u) from UserStory u where u.priority = acme.entities.userstories.UserStoryPriority.MUST and u.manager.id = :managerId")
	int totalMustUserStories(int managerId);

	@Query("select count(u) from UserStory u where u.priority = acme.entities.userstories.UserStoryPriority.WONT and u.manager.id = :managerId")
	int totalWontUserStories(int managerId);

	@Query("select avg(u.estimatedCost) from UserStory u where u.manager.id = :managerId")
	Double userStoryEstimatedCostAverage(int managerId);

	@Query("select max(u.estimatedCost) from UserStory u where u.manager.id = :managerId")
	Integer maximumUserStoryEstimatedCost(int managerId);

	@Query("select min(u.estimatedCost) from UserStory u where u.manager.id = :managerId")
	Integer minimumUserStoryEstimatedCost(int managerId);

	@Query("select avg(p.cost) from Project p where p.manager.id = :managerId")
	Double projectCostAverage(int managerId);

	@Query("select max(p.cost) from Project p where p.manager.id = :managerId")
	Integer maximumProjectCost(int managerId);

	@Query("select min(p.cost) from Project p where p.manager.id = :managerId")
	Integer minimumProjectCost(int managerId);

	@Query("select stddev(u.estimatedCost) from UserStory u where u.manager.id = :managerId")
	Double userStoryEstimatedCostDeviation(int managerId);

	@Query("select stddev(p.cost) from Project p where p.manager.id = :managerId")
	Double projectCostDeviation(int managerId);
}
