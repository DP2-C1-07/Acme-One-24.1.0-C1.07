
package acme.features.developer.trainingsession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.trainingmodules.TrainingModule;
import acme.entities.trainingsessions.TrainingSession;

@Repository
public interface DeveloperTrainingSessionRepository extends AbstractRepository {

	@Query("select t from TrainingModule t where t.id = :id")
	TrainingModule findOneTrainingModuleById(int id);

	@Query("select ts.TrainingModule from TrainingSession ts where ts.id = :id")
	TrainingModule findOneTrainingModuleByTrainingSessionId(int id);

	@Query("select ts from TrainingSession ts where ts.id = :id")
	TrainingSession findOneTrainingSessionById(int id);

	@Query("select ts from TrainingSession ts where ts.TrainingModule.id = :masterId")
	Collection<TrainingSession> findManyTrainingSessionsByMasterId(int masterId);

}
