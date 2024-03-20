
package acme.features.any;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.claims.Claim;

@Repository
public interface AnyClaimRepository extends AbstractRepository {

	@Query("select c from Claim c")
	Collection<Claim> findAllClaims();
}
