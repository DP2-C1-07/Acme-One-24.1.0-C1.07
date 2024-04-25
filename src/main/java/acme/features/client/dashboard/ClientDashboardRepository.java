
package acme.features.client.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contract.Contract;
import acme.entities.progressLog.ProgressLog;

@Repository
public interface ClientDashboardRepository extends AbstractRepository {

	@Query("select pl from ProgressLog pl")
	Collection<ProgressLog> findAllProgressLogs();

	@Query("select c from Contract c")
	Collection<Contract> findAllContracts();

	@Query("select c from Contract c where c.client.id = :clientId")
	Collection<Contract> findManyContractsByClientId(int clientId);
	
	@Query("select c.budget.amount from Contract c where c.client.id = :clientId and c.draftMode = false")
	Collection<Double> findBudgetAmountsByClientId(int clientId);
	
	@Query("select pl.completeness from ProgressLog pl where pl.contract.client.id = :clientId")
	Collection<Double> findProgressLogsCompletenessByClientId(int clientId);
}
