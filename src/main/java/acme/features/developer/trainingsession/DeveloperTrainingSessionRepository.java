
package acme.features.developer.trainingsession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.trainingmodules.TrainingModule;
import acme.entities.trainingsessions.TrainingSession;
import acme.roles.Developer;

@Repository
public interface DeveloperTrainingSessionRepository extends AbstractRepository {

	@Query("select t from TrainingModule t where t.id = :id")
	TrainingModule findOneTrainingModuleById(int id);

	@Query("select ts.trainingModule from TrainingSession ts where ts.id = :id")
	TrainingModule findOneTrainingModuleByTrainingSessionId(int id);

	@Query("select ts from TrainingSession ts where ts.id = :id")
	TrainingSession findOneTrainingSessionById(int id);

	@Query("select ts from TrainingSession ts where ts.trainingModule.id = :masterId")
	Collection<TrainingSession> findManyTrainingSessionsByMasterId(int masterId);

	@Query("select ts from TrainingSession ts where ts.code = :code")
	TrainingSession findOneTrainingSessionByCode(String code);

	@Query("select d from Developer d where d.id = :id")
	Developer findOneDeveloperById(int id);
}
