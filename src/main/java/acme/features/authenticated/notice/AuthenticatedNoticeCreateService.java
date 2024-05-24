
package acme.features.authenticated.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.notices.Notice;

@Service
public class AuthenticatedNoticeCreateService extends AbstractService<Authenticated, Notice> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private AuthenticatedNoticeRepository repository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		boolean status = super.getRequest().getPrincipal().isAuthenticated();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Notice object;

		object = new Notice();
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Notice object) {
		assert object != null;

		super.bind(object, "instantiationMoment", "title", "author", "message", "email", "link");
	}

	@Override
	public void validate(final Notice object) {
		assert object != null;

	}

	@Override
	public void perform(final Notice object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Notice object) {
		assert object != null;

		Dataset dataset;
		dataset = super.unbind(object, "instantiationMoment", "title", "author", "message", "email", "link");

		super.getResponse().addData(dataset);
	}
}
