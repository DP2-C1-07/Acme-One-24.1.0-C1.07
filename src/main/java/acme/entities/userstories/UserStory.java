
package acme.entities.userstories;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.roles.Manager;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "priority, manager_id, draftMode", unique = false), @Index(columnList = "manager_id, draftMode", unique = false), @Index(columnList = "manager_id", unique = false)
})
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

	@Min(value = 0)
	@Max(value = 100000)
	int							estimatedCost;

	@NotBlank
	@Length(max = 100)
	String						acceptanceCriteria;

	@NotNull
	UserStoryPriority			priority;

	@URL
	@Length(max = 255)
	String						link;

	boolean						draftMode			= true;

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Manager				manager;

}
