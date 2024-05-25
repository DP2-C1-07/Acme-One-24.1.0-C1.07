
package acme.entities.sponsorships;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Invoice extends AbstractEntity {

	//Serialisation identifier -----------------------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	//Attributes --------------------------------------------------------------------------------

	@NotNull
	@Column(unique = true)
	@Pattern(regexp = "^IN-[0-9]{4}-[0-9]{4}$", message = "{sponsor.invoice.form.error.code-format}")
	private String				code;

	@NotNull
	@PastOrPresent
	@Temporal(TemporalType.TIMESTAMP)
	private Date				registrationTime;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	// TODO: at least one month ahead the registration time
	private Date				dueDate;

	@NotNull
	// TODO: not nought
	private Money				quantity;

	@Min(0)
	@Max(5) // there are governments applying taxes higher than 100% in some products
	private double				tax;

	@URL
	@Length(max = 255)
	private String				link;


	@Transient
	public Money getTotalAmount() {
		Money money = new Money();
		money.setAmount(this.quantity.getAmount() * (1d + this.tax));
		money.setCurrency(this.quantity.getCurrency());
		return money;
	}


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Sponsorship sponsorship;

}
