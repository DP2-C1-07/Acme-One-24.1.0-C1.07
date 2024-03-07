
package acme.entities.code_audits;

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
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.projects.Project;
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
	@Pattern(regexp = "^[A-Z]{1,3}-[0-9]{3}$")
	String						code;

	@Past
	@NotNull
	@Temporal(TemporalType.DATE)
	Date						executionDate;

	@NotNull
	CodeAuditType				type;

	@NotBlank
	@Length(max = 100)
	String						correctiveAction;

	@Valid
	@NotNull
	Mark						mark;

	@Nullable
	@URL
	String						link;

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	Project						project;
}
