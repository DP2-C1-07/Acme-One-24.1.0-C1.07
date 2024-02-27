
package acme.entities.projects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.roles.Manager;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Project extends AbstractEntity {

	//Serialisation identifier -----------------------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	//Attributes --------------------------------------------------------------------------------
	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{3}-[0-9]{4}")
	String						code;

	@NotBlank
	@Length(max = 75)
	String						title;

	@NotBlank
	@Length(max = 100)
	String						projectAbstract;

	@NotNull
	Boolean						indication;

	@NotNull
	@Min(value = 0)
	Integer						cost;

	@URL
	String						link;

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne()
	private Manager				manager;
}
