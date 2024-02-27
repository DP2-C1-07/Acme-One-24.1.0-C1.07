
package acme.entities.userstories;

import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.projects.Project;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserStory extends AbstractEntity {

	//Serialisation identifier -----------------------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	//Attributes --------------------------------------------------------------------------------
	@NotBlank
	@Length(max = 75)
	String						title;

	@NotBlank
	@Length(max = 100)
	String						description;

	@NotNull
	@Min(value = 0)
	Integer						estimatedCost;

	@NotBlank
	@Length(max = 100)
	String						acceptanceCriteria;

	@NotNull
	UserStoryPriority			priority;

	@URL
	String						link;

	// Relationships ----------------------------------------------------------

	@NotNull

	@Valid

	@ManyToOne()
	private Project				project;

}
