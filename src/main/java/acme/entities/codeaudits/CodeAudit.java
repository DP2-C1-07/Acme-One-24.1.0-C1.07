
package acme.entities.codeaudits;

import java.util.Collection;
import java.util.Date;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.audit_records.AuditRecord;
import acme.entities.projects.Project;
import acme.roles.Auditor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class CodeAudit extends AbstractEntity {

	//Serialisation identifier -----------------------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	//Attributes --------------------------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^[A-Z]{1,3}-[0-9]{3}$", message = "{codeAudit.code.error}")
	String						code;

	@PastOrPresent
	@NotNull
	@Temporal(TemporalType.DATE)
	Date						executionDate;

	@NotNull
	CodeAuditType				type;

	@NotBlank
	@Length(max = 100)
	String						correctiveAction;

	@Nullable
	@URL
	@Length(max = 255)
	String						link;

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	Project						project;

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	Auditor						auditor;

	@NotNull
	Boolean						draftMode			= true;


	@Transient
	public Mark getMark(final Collection<AuditRecord> listOfAuditRecords) {
		int sum = 0;
		if (listOfAuditRecords.isEmpty())
			return Mark.F_MINUS;
		for (AuditRecord auditRecord : listOfAuditRecords)
			sum += auditRecord.getMark().getNumericMark();
		int media = sum / listOfAuditRecords.size();
		return Mark.byNumericMark(media);

	}
}
