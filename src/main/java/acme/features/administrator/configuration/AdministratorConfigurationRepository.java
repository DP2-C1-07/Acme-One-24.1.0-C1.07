
package acme.features.administrator.configuration;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.accounts.Administrator;
import acme.client.repositories.AbstractRepository;
import acme.entities.configuration.Configuration;

@Repository
public interface AdministratorConfigurationRepository extends AbstractRepository {

	@Query("select a from administrator a where a.id = :id")
	Administrator findAdministratorById(int id);

	@Query("select c from configuration c where c.id = :id")
	Configuration findConfigurationById(int id);

}
