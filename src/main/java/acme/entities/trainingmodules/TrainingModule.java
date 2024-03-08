
package acme.entities.trainingmodules;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TrainingModule extends AbstractEntity {

	//Serialisation identifier -----------------------------------------------------------------

	private static final long		serialVersionUID	= 1L;

	//Attributes --------------------------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^[A-Z]{1,3}-[0-9]{3}$", message = "{trainingModule.code.error}")
	String							code;

	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	Date							creationMoment;

	@NotBlank
	@Length(max = 100)
	String							details;

	@NotNull
	TrainingModuleDifficultyLevel	difficultyLevel;

	//TODO: cuando sepamos hacer servicios añadir la restricción de que el updateMoment debe ser posterior al creationMoment
	@PastOrPresent
	Date							updateMoment;

	@URL
	String							link;

	int								totalTime;

	boolean							draft;

}
