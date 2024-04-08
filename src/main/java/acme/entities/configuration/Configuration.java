
package acme.entities.configuration;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Configuration extends AbstractEntity {
	//Serialisation identifier -----------------------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	//Attributes --------------------------------------------------------------------------------
	@NotBlank
	@Length(min = 3, max = 3)
	@Pattern(regexp = "[A-Z]{3}", message = "{configuration.systemCurrency.error}")
	String						systemCurrency;

	@NotBlank
	@Length(min = 3)
	@Pattern(regexp = "([A-Z]{3},\\s)+", message = "{configuration.acceptedCurrencies.error}")
	String						acceptedCurrencies;
}
