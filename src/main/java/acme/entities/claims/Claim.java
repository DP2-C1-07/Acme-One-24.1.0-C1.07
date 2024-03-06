
package acme.entities.claims;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
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
public class Claim extends AbstractEntity {

	//Serialisation identifier -----------------------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	//Attributes --------------------------------------------------------------------------------
	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^C-[0-9]{4}$", message = "claim.code.error")
	String						code;

	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	@NotNull
	Date						instantiationMoment;

	@NotBlank
	@Length(max = 75)
	String						heading;

	@NotBlank
	@Length(max = 100)
	String						description;

	@NotBlank
	@Length(max = 100)
	String						department;

	@Email
	@Length(max = 255)
	String						emailAddress;

	@URL
	@Length(max = 255)
	String						link;
}
