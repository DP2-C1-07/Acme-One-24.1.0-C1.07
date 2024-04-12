
package acme.features.manager.userstory;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.ProjectUserStory;
import acme.entities.userstories.UserStory;
import acme.roles.Manager;

@Repository
public interface ManagerUserStoryRepository extends AbstractRepository {

	@Query("select u from UserStory u where u.id = :id")
	UserStory findOneUserStoryById(int id);

	@Query("select u from UserStory u where u.manager.id = :managerId")
	Collection<UserStory> findAllUserStoriesByManagerId(int managerId);

	@Query("select pu.userStory from ProjectUserStory pu where pu.project.id = :projectId")
	Collection<UserStory> findAllUserStoriesByProjectId(int projectId);

	@Query("select m from Manager m where m.id = :managerId")
	Manager findManagerById(int managerId);

	@Query("select pu from ProjectUserStory pu where pu.userStory.id = :userStoryId")
	Collection<ProjectUserStory> findProjectUserStoriesByUserStoryId(int userStoryId);
}
