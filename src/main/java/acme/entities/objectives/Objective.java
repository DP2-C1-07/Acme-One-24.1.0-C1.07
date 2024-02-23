
package acme.entities.objectives;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Objective extends AbstractEntity {

	//Serialisation identifier -----------------------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	//Atributes --------------------------------------------------------------------------------

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@NotNull
	Date						instantiationMoment;

	@NotBlank
	@Length(max = 75)
	String						title;

	@NotBlank
	@Length(max = 100)
	String						description;

	@NotNull
	ObjectivePriority			priority;

	//TODO: como se ha respondido en el foro period se implementa con dos atributos de tipo date
	// queda implementar las restricciones de que la duracion debe ser superior a una hora
	// el momento de inicio debe ser posterior al de creacion y el de finalizacion posterior al de inicio
	@NotNull
	Date						initiateMoment;

	@NotNull
	Date						finalizationMoment;

	@NotNull
	Boolean						status;

	@URL
	String						link;
}
