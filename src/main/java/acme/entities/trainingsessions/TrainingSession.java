
package acme.entities.trainingsessions;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.trainingmodules.TrainingModule;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TrainingSession extends AbstractEntity {

	//Serialisation identifier -----------------------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	//Attributes --------------------------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "TS-[A-Z]{1,3}-[0-9]{3}")
	String						code;

	//como se ha reusuelto en el foro el atributo period se implementa como dos atributos, uno que indique el
	//comienzo del periodo y otro que indique el final.
	//TODO: implementar una restriccion en el servicio que compruebe que la duración mínima sea de una semana
	//y que el inicio sea una semana despues del momento de creacion del trainingmodule asociado

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	Date						initiateMoment;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	Date						finalizationMoment;

	@NotBlank
	@Length(max = 75)
	String						location;

	@NotBlank
	@Length(max = 75)
	String						instructor;

	@NotNull
	@Email
	String						contactEmail;

	@URL
	String						link;

	@NotNull
	@Valid
	@ManyToOne()
	private TrainingModule		trainingModule;

}
