package acme.entities.contract;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
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
		@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
		String code;
		
		@Temporal(TemporalType.TIMESTAMP)
		@Past
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
		
		@NotNull
		@Min(0)
		Double budget;
		
		@ManyToOne
		Project project;
}
