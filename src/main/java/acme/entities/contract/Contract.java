package acme.entities.contract;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.client.data.AbstractEntity;
import acme.entities.projects.Project;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Contract extends AbstractEntity{

		//Serialisation identifier -----------------------------------------------------------------

		private static final long	serialVersionUID	= 1L;

		//Attributes --------------------------------------------------------------------------------
		@NotBlank
		@Column(unique = true)
		@Pattern(regexp = "^[A-Z]{1,3}-[0-9]{3}$", message="{contract.code.error}")
		String code;
		
		@Temporal(TemporalType.TIMESTAMP)
		@PastOrPresent
		@NotNull
		Date instantiationMoment;
		
		@NotBlank
		@Length(max = 75)
		String providerName;
		
		@NotBlank
		@Length(max = 75)
		String customerName;
		
		@NotBlank
		@Length(max = 100)
		String goals;
		
		@Min(0)
		@Digits(fraction = 2, integer = 5)
		double budget;
		
		@ManyToOne
		@NotNull
		@Valid
		Project project;
}
