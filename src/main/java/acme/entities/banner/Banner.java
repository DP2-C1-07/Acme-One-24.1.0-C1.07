
package acme.entities.banner;

import java.time.Duration;
import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.time.DurationMin;

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
	Instant						instantiationMoment;

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	Instant						lastUpdateMoment;

	//TODO: añadir al service la logica para que el displayPeriod ocurra despues que lastUpdateMoment / instantiationMoment
	@DurationMin(days = 7)
	Duration					displayPeriod;

	@URL
	String						pictureLink;

	@NotBlank
	@Max(75)
	String						slogan;

	@URL
	@NotNull
	String						link;
}
