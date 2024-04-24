
package acme.features.administrator.banner;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.banner.Banner;

@Service
public class AdministratorBannerUpdateService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorBannerRepository repository;

	// AbstractService<Administrator, Banner> -------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Banner object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneBannerById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "displayPeriodBeginning", "displayPeriodEnd", "pictureLink", "slogan", "link");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("displayPeriodBeginning"))
			super.state(MomentHelper.isAfter(object.getDisplayPeriodBeginning(), object.getInstantiationLastUpdateMoment()), "displayPeriodBeginning", "administrator.banner.form.error.DisplayPeriodNotAfterILUMoment");

		if (!super.getBuffer().getErrors().hasErrors("displayPeriodBeginning") && !super.getBuffer().getErrors().hasErrors("displayPeriodEnd")) {
			Date minimumDisplayPeriod;
			minimumDisplayPeriod = MomentHelper.deltaFromMoment(object.getDisplayPeriodBeginning(), 7, ChronoUnit.DAYS);

			super.state(MomentHelper.isAfter(object.getDisplayPeriodEnd(), minimumDisplayPeriod), "displayPeriodEnd", "administrator.banner.form.error.too-close");
		}
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "displayPeriodBeginning", "displayPeriodEnd", "pictureLink", "slogan", "link");
		dataset.put("instantiationLastUpdateMoment", object.getInstantiationLastUpdateMoment());
		super.getResponse().addData(dataset);
	}

}
