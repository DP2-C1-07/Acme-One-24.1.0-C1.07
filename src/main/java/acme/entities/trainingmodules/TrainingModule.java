
package acme.entities.trainingmodules;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.projects.Project;
import acme.roles.Developer;
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
	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	Date							updateMoment;

	@URL
	@Length(max = 255)
	String							link;

	@Min(1)
	int								totalTime;

	boolean							draft;

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Developer				developer;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Project					project;
}
