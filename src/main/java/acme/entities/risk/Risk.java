
package acme.entities.risk;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
public class Risk extends AbstractEntity {

		//Serialisation identifier -----------------------------------------------------------------

		private static final long	serialVersionUID	= 1L;

		//Attributes --------------------------------------------------------------------------------
		@NotBlank
		@Column(unique = true)
		@Pattern(regexp = "^R-[0-9]{3}$", message="{risk.reference.error}")
		String reference;
		
		@NotNull
		@Temporal(TemporalType.TIMESTAMP)
		@PastOrPresent
		Date identificationDate;
		
		@Min(0)
		@Max(100)
		@Digits(fraction = 2, integer = 3)
		double impact;
		
		@Min(0)
		@Max(1)
		@Digits(fraction = 2, integer = 1)
		double probability;
		
		@NotBlank
		@Length(max = 100)
		String description;
		
		@URL
		@Length(max = 255)
		String link;
		
		@Transient
		public Double getValue() {
			return this.impact * this.probability;
		}
}
