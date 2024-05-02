
package acme.features.authenticated.objective;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.objectives.Objective;

public interface AuthenticatedObjectiveRepository {

	@Query("select o from Objective o")
	Collection<Objective> findAllObjectives();

	@Query("select o from Objective o where o.id = :id")
	Objective findOneObjectiveById(int id);

}
