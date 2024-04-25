
package acme.features.developer.trainingmodule;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.trainingmodules.TrainingModule;
import acme.entities.trainingsessions.TrainingSession;
import acme.roles.Developer;

public interface DeveloperTrainingModuleRepository extends AbstractRepository {

	@Query("select t from TrainingModule t where t.id = :id")
	TrainingModule findOneTrainingModuleById(int id);

	@Query("select t from TrainingModule t where t.developer.id = :developerId")
	Collection<TrainingModule> findManyTrainingModulesByDeveloperId(int developerId);

	@Query("select t from TrainingModule t where t.developer.id = :developerId")
	Collection<TrainingModule> findAllTrainingModules(int developerId);

	@Query("select t from TrainingModule t where t.code = :code")
	TrainingModule findOneTrainingModuleByCode(String code);

	@Query("select d from Developer d where d.id = :id")
	Developer findOneDeveloperById(int id);

	@Query("select ts from TrainingSession ts where ts.trainingModule.id = :id")
	Collection<TrainingSession> findManyTrainingSessionsByTrainingModuleId(int id);
}
