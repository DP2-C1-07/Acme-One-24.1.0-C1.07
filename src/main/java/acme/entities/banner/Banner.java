
package acme.entities.banner;

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
public class Banner extends AbstractEntity {
	//Serialisation identifier -----------------------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	//Attributes --------------------------------------------------------------------------------

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	Date						instantiationMoment;

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	Date						lastUpdateMoment;

	//TODO: añadir al service la logica para que el displayPeriod ocurra despues que lastUpdateMoment / instantiationMoment
	//TODO: comprobar que tiene una duracion minima de 7 dias entre el beginning y el end
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	Date						displayPeriodBeginning;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	Date						displayPeriodEnd;

	@URL
	@NotNull
	String						pictureLink;

	@NotBlank
	@Length(max = 75)
	String						slogan;

	@URL
	@NotNull
	String						link;
}
