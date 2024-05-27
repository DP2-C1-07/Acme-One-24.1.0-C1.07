
package acme.entities.codeaudits;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
@Table(indexes = {
	@Index(columnList = "auditor_id", unique = false), @Index(columnList = "draftMode", unique = false), @Index(columnList = "code", unique = false), @Index(columnList = "project_id", unique = false)
})
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
		if (listOfAuditRecords.isEmpty())
			return Mark.F_MINUS;

		Map<Mark, Integer> count = new HashMap<>();
		for (Mark mark : listOfAuditRecords.stream().map(AuditRecord::getMark).toList())
			if (count.containsKey(mark))
				count.put(mark, count.get(mark) + 1);
			else
				count.put(mark, 1);

		Mark mode = null;
		int maxCount = 0;
		for (Map.Entry<Mark, Integer> entry : count.entrySet())
			if (entry.getValue() == maxCount && entry.getKey().getNumericMark() < mode.getNumericMark())
				mode = entry.getKey();
			else if (entry.getValue() > maxCount) {
				maxCount = entry.getValue();
				mode = entry.getKey();
			}

		return mode;

	}
}
