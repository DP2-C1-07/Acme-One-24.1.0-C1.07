
package acme.features.any.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.projects.Project;

public interface AnyProjectRepository {

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

	@Query("select p from Project p where p.id = :id")
	Project findOneById(int id);
}
