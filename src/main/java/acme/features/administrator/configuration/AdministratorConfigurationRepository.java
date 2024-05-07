
package acme.features.administrator.configuration;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.accounts.Administrator;
import acme.client.repositories.AbstractRepository;
import acme.entities.configuration.Configuration;

@Repository
public interface AdministratorConfigurationRepository extends AbstractRepository {

	@Query("select a from Administrator a where a.id = :id")
	Administrator findAdministratorById(int id);

	@Query("select c from Configuration c")
	Configuration findConfiguration();

}
