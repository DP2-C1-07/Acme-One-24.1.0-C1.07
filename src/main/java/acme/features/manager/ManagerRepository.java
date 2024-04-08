
package acme.features.manager;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.roles.Manager;

@Repository
public interface ManagerRepository extends AbstractRepository {

	@Query("select m from Manager m where m.id = :id")
	Manager findOneById(int id);
}
