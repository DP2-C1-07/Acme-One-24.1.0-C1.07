
package acme.entities.banner;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	 @Index(columnList = "displayPeriodBeginning, displayPeriodEnd", unique = false)
})
public class Banner extends AbstractEntity {
	//Serialisation identifier -----------------------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	//Attributes --------------------------------------------------------------------------------

	@PastOrPresent
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	Date						instantiationLastUpdateMoment;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	Date						displayPeriodBeginning;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	Date						displayPeriodEnd;

	@URL
	@NotBlank
	@Length(max = 255)
	String						pictureLink;

	@NotBlank
	@Length(max = 75)
	String						slogan;

	@URL
	@NotBlank
	@Length(max = 255)
	String						link;
}
