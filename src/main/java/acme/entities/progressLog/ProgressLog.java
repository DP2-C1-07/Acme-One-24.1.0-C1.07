package acme.entities.progressLog;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.client.data.AbstractEntity;
import acme.entities.contract.Contract;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProgressLog extends AbstractEntity {
	
		//Serialisation identifier -----------------------------------------------------------------

		private static final long	serialVersionUID	= 1L;

		//Attributes --------------------------------------------------------------------------------
		@NotBlank
		@Column(unique = true)
		@Pattern(regexp = "PG-[A-Z]{1,2}-[0-9]{4}")
		String recordIn;
		
		@NotNull
		@Min(0)
		@Max(1)
		Double completeness;

		@NotBlank
		@Length(max = 100)
		String comment;
		
		@Temporal(TemporalType.TIMESTAMP)
		@Past
		@NotNull
		Date registrationMoment;
		
		@NotBlank
		@Length(max = 75)
		String responsiblePerson;
		
		@ManyToOne
		Contract contract;
}
