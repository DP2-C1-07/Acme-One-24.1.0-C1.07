
package acme.features.developer.trainingsession;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.trainingsessions.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionUpdateService extends AbstractService<Developer, TrainingSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int trainingSessionId;
		TrainingSession trainingSession;
		Developer developer;

		trainingSessionId = super.getRequest().getData("id", int.class);
		trainingSession = this.repository.findOneTrainingSessionById(trainingSessionId);
		developer = this.repository.findOneDeveloperById(super.getRequest().getPrincipal().getActiveRoleId());

		status = trainingSession != null && trainingSession.isDraft() && super.getRequest().getPrincipal().hasRole(trainingSession.getTrainingModule().getDeveloper()) && trainingSession.getTrainingModule().getDeveloper().equals(developer);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingSession object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTrainingSessionById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingSession object) {
		assert object != null;

		super.bind(object, "code", "initiateMoment", "finalizationMoment", "location", "instructor", "contactEmail", "link");
		object.setDraft(true);
	}

	@Override
	public void validate(final TrainingSession object) {
		assert object != null;

		Date MIN_DATE;
		Date MAX_DATE;

		MIN_DATE = MomentHelper.parse("2000-01-01 00:00", "yyyy-MM-dd HH:mm");
		MAX_DATE = MomentHelper.parse("2200-12-31 23:59", "yyyy-MM-dd HH:mm");

		if (!super.getBuffer().getErrors().hasErrors("initiateMoment"))
			super.state(MomentHelper.isAfterOrEqual(object.getInitiateMoment(), MIN_DATE), "initiateMoment", "developer.training-session.form.error.before-min-date");

		if (!super.getBuffer().getErrors().hasErrors("initiateMoment"))
			super.state(MomentHelper.isBeforeOrEqual(object.getInitiateMoment(), MAX_DATE), "initiateMoment", "developer.training-session.form.error.after-max-date");

		if (!super.getBuffer().getErrors().hasErrors("initiateMoment"))
			super.state(MomentHelper.isBeforeOrEqual(object.getInitiateMoment(), MomentHelper.deltaFromMoment(MAX_DATE, -7, ChronoUnit.DAYS)), "initiateMoment", "developer.training-session.form.error.no-time-for-min-period-i");

		if (!super.getBuffer().getErrors().hasErrors("initiateMoment"))
			super.state(MomentHelper.isAfterOrEqual(object.getInitiateMoment(), MomentHelper.deltaFromMoment(object.getTrainingModule().getCreationMoment(), 7, ChronoUnit.DAYS)), "initiateMoment",
				"developer.training-session.form.error.initiate-one-week-after-tm-creation");

		if (!super.getBuffer().getErrors().hasErrors("finalizationMoment"))
			super.state(MomentHelper.isAfterOrEqual(object.getFinalizationMoment(), MIN_DATE), "finalizationMoment", "developer.training-session.form.error.before-min-date");

		if (!super.getBuffer().getErrors().hasErrors("finalizationMoment"))
			super.state(MomentHelper.isBeforeOrEqual(object.getFinalizationMoment(), MAX_DATE), "finalizationMoment", "developer.training-session.form.error.after-max-date");

		if (!super.getBuffer().getErrors().hasErrors("finalizationMoment"))
			super.state(MomentHelper.isAfterOrEqual(object.getFinalizationMoment(), MomentHelper.deltaFromMoment(MIN_DATE, 7, ChronoUnit.DAYS)), "finalizationMoment", "developer.training-session.form.error.no-time-for-min-period-f");

		if (!super.getBuffer().getErrors().hasErrors("initiateMoment") && !super.getBuffer().getErrors().hasErrors("finalizationMoment")) {
			Date minimumDisplayPeriod;
			minimumDisplayPeriod = MomentHelper.deltaFromMoment(object.getInitiateMoment(), 7, ChronoUnit.DAYS);

			super.state(MomentHelper.isAfterOrEqual(object.getFinalizationMoment(), minimumDisplayPeriod), "finalizationMoment", "developer.training-session.form.error.too-close");
		}

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingSession existing;

			existing = this.repository.findOneTrainingSessionByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "developer.training-session.form.error.duplicated");
		}
	}

	@Override
	public void perform(final TrainingSession object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "initiateMoment", "finalizationMoment", "location", "instructor", "contactEmail", "link", "trainingModule");
		dataset.put("masterId", object.getTrainingModule().getId());
		if (object.isDraft()) {
			final Locale local = super.getRequest().getLocale();

			dataset.put("draft", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("draft", "No");
		super.getResponse().addData(dataset);

	}

}
