
package acme.features.manager.projectuserstory;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectUserStory;
import acme.entities.userstories.UserStory;
import acme.roles.Manager;

public interface ManagerProjectUserStoryRepository extends AbstractRepository {

	@Query("select m from Manager m where m.id = :managerId")
	Manager findOneManagerById(int managerId);

	@Query("select p from Project p where p.manager.id = :managerId")
	Collection<Project> findProjectsByManagerId(int managerId);

	@Query("select u from UserStory u where u.manager.id = :managerId")
	Collection<UserStory> findUserStoriesByManagerId(int managerId);

	@Query("select p from Project p where p.id = :projectId")
	Project findOneProjectById(int projectId);

	@Query("select u from UserStory u where u.id = :userStoryId")
	UserStory findOneUserStoryById(int userStoryId);

	@Query("select pu from ProjectUserStory pu where pu.id = :projectUserStoryId")
	ProjectUserStory findOneProjectUserStoryById(int projectUserStoryId);

	@Query("select pu from ProjectUserStory pu where pu.id = :projectUserStoryId")
	Collection<ProjectUserStory> findProjectUserStoriesById(int projectUserStoryId);

	@Query("select pu from ProjectUserStory pu where pu.project.manager.id = :managerId and pu.userStory.manager.id = :managerId")
	Collection<ProjectUserStory> findProjectUserStoriesByManagerId(int managerId);
}
