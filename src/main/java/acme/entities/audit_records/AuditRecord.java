
package acme.entities.audit_records;

import java.util.Date;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.code_audits.CodeAudit;
import acme.entities.code_audits.Mark;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuditRecord extends AbstractEntity {

	//Serialisation identifier -----------------------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	//Attributes --------------------------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^AU-[0-9]{4}-[0-9]{3}$", message = "{auditRecord.code.error}")
	String						code;

	//TODO:Comprobar si hay 1 hora de diferencia entre el principio y el final del periodo
	@PastOrPresent
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	Date						periodBeginning;

	@PastOrPresent
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	Date						periodEnd;

	@NotNull
	Mark						mark;

	@Nullable
	@URL
	@Length(max = 255)
	String						link;

	Boolean						itsPublished		= false;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	CodeAudit					codeAudit;
}
