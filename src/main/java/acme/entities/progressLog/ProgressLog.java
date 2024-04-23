package acme.entities.progressLog;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
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
		@Pattern(regexp = "^PG-[A-Z]{1,2}-[0-9]{4}$", message = "{progressLog.recordIn.error}")
		String recordIn;
		
		@Min(0)
		@Max(1)
		@Digits(fraction = 2, integer = 1)
		double completeness;

		@NotBlank
		@Length(max = 100)
		String comment;
		
		@Temporal(TemporalType.TIMESTAMP)
		@PastOrPresent
		@NotNull
		Date registrationMoment;
		
		@NotBlank
		@Length(max = 75)
		String responsiblePerson;
		
		@NotNull
		boolean draftMode = true;
		
		@ManyToOne(optional = false)
		@NotNull
		@Valid
		Contract contract;
}
