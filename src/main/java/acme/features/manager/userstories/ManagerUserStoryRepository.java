
package acme.features.manager.userstories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.userstories.UserStory;

@Repository
public interface ManagerUserStoryRepository extends AbstractRepository {

	@Query("select u from UserStory whre u.id = :id")
	UserStory findOneById(int id);

	@Query("select u from UserStory u where u.manager.id = :managerId")
	Collection<UserStory> findAllByManagerId(int managerId);

	@Query("select u from UserStory u where u.project.id = :projectId")
	Collection<UserStory> findAllByProjectId(int projectId);
}
