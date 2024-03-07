package acme.roles.client;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Client extends AbstractRole{
	
	//Serialisation identifier -----------------------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	//Attributes --------------------------------------------------------------------------------
	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^CLI-[0-9]{4}$", message = "{client.identification.error}")
	String identification;
	
	@NotBlank
	@Length(max = 100)
	String companyName;
	
	@NotBlank
	ClientType type;
	
	@NotBlank
	@Length(max = 255)
	String email;
	
	@URL
	@Length(max = 255)
	String link;
}
