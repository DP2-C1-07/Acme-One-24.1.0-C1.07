
package acme.features.manager;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.userstories.UserStory;

@Repository
public interface ManagerUserStoryRepository extends AbstractRepository {

	@Query("select us from UserStory us where us.manager.id = :managerId")
	Collection<UserStory> findAllUserStoriesByManagerId(int managerId);
}
