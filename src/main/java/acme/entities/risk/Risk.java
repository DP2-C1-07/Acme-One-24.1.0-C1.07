package acme.entities.risk;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
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
		@Pattern(regexp = "R-[0-9]{3}")
		String reference;
		
		@NotNull
		@Temporal(TemporalType.TIMESTAMP)
		@Past
		Date identificationDate;
		
		@NotNull
		@Min(0)
		Integer impact;
		
		@NotNull
		@Min(0)
		@Max(100)
		Double probability;
		
		@NotNull
		Double value = impact * probability;
		
		@NotBlank
		@Length(max = 101)
		String description;
		
		@URL
		String optionalLink;
}