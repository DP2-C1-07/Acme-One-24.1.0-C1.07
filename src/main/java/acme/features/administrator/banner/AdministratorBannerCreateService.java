
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
public class AdministratorBannerCreateService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorBannerRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Administrator.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Banner object;

		object = new Banner();

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;
		Date instantiationTime;

		super.bind(object, "displayPeriodBeginning", "displayPeriodEnd", "pictureLink", "slogan", "link");

		instantiationTime = MomentHelper.getCurrentMoment();
		object.setInstantiationLastUpdateMoment(instantiationTime);
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;
		Date MIN_DATE;
		Date MAX_DATE;

		MIN_DATE = MomentHelper.parse("2000-01-01 00:00", "yyyy-MM-dd HH:mm");
		MAX_DATE = MomentHelper.parse("2200-12-31 23:59", "yyyy-MM-dd HH:mm");

		if (!super.getBuffer().getErrors().hasErrors("displayPeriodBeginning"))
			super.state(MomentHelper.isAfterOrEqual(object.getDisplayPeriodBeginning(), MIN_DATE), "displayPeriodBeginning", "administrator.banner.form.error.before-min-date");

		if (!super.getBuffer().getErrors().hasErrors("displayPeriodBeginning"))
			super.state(MomentHelper.isBeforeOrEqual(object.getDisplayPeriodBeginning(), MAX_DATE), "displayPeriodBeginning", "administrator.banner.form.error.after-max-date");

		if (!super.getBuffer().getErrors().hasErrors("displayPeriodBeginning"))
			super.state(MomentHelper.isBeforeOrEqual(object.getDisplayPeriodBeginning(), MomentHelper.deltaFromMoment(MAX_DATE, -7, ChronoUnit.DAYS)), "displayPeriodBeginning", "administrator.banner.form.error.no-time-for-min-period-b");

		if (!super.getBuffer().getErrors().hasErrors("displayPeriodBeginning"))
			super.state(MomentHelper.isAfterOrEqual(object.getDisplayPeriodBeginning(), object.getInstantiationLastUpdateMoment()), "displayPeriodBeginning", "administrator.banner.form.error.display-period-not-after-ILUMoment");

		if (!super.getBuffer().getErrors().hasErrors("displayPeriodEnd"))
			super.state(MomentHelper.isAfterOrEqual(object.getDisplayPeriodEnd(), object.getInstantiationLastUpdateMoment()), "displayPeriodEnd", "administrator.banner.form.error.display-period-not-after-ILUMoment");

		if (!super.getBuffer().getErrors().hasErrors("displayPeriodEnd"))
			super.state(MomentHelper.isAfterOrEqual(object.getDisplayPeriodEnd(), MIN_DATE), "displayPeriodEnd", "administrator.banner.form.error.before-min-date");

		if (!super.getBuffer().getErrors().hasErrors("displayPeriodEnd"))
			super.state(MomentHelper.isBeforeOrEqual(object.getDisplayPeriodEnd(), MAX_DATE), "displayPeriodEnd", "administrator.banner.form.error.after-max-date");

		if (!super.getBuffer().getErrors().hasErrors("displayPeriodEnd"))
			super.state(MomentHelper.isAfterOrEqual(object.getDisplayPeriodEnd(), MomentHelper.deltaFromMoment(MIN_DATE, 7, ChronoUnit.DAYS)), "displayPeriodEnd", "administrator.banner.form.error.no-time-for-min-period-e");

		if (!super.getBuffer().getErrors().hasErrors("displayPeriodBeginning") && !super.getBuffer().getErrors().hasErrors("displayPeriodEnd")) {
			Date minimumDisplayPeriod;
			minimumDisplayPeriod = MomentHelper.deltaFromMoment(object.getDisplayPeriodBeginning(), 7, ChronoUnit.DAYS);

			super.state(MomentHelper.isAfterOrEqual(object.getDisplayPeriodEnd(), minimumDisplayPeriod), "displayPeriodEnd", "administrator.banner.form.error.too-close");
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
