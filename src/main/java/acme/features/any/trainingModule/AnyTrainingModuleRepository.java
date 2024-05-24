
package acme.features.any.trainingModule;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.trainingmodules.TrainingModule;

@Repository
public interface AnyTrainingModuleRepository extends AbstractRepository {

	@Query("select tm from TrainingModule tm where tm.draft = false")
	Collection<TrainingModule> findAllPublishedTrainingModules();

	@Query("select tm from TrainingModule tm where tm.id = :id")
	TrainingModule findOneById(int id);
}
