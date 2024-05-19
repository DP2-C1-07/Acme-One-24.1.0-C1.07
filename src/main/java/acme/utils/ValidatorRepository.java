package acme.utils;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface ValidatorRepository extends AbstractRepository{

	@Query("SELECT c.acceptedCurrencies FROM Configuration c")
	String findAcceptedCurrencies();
}
