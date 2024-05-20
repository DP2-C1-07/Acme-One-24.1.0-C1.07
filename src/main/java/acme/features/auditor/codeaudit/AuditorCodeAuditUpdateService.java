
package acme.features.auditor.codeaudit;

import java.sql.Date;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.audit_records.AuditRecord;
import acme.entities.codeaudits.CodeAudit;
import acme.entities.codeaudits.CodeAuditType;
import acme.entities.codeaudits.Mark;
import acme.entities.projects.Project;
import acme.features.auditor.auditrecord.AuditorAuditRecordRepository;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditUpdateService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private AuditorCodeAuditRepository		auditorCodeAuditRepository;

	@Autowired
	private AuditorAuditRecordRepository	auditorAuditRecordRepository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		boolean status;
		int userStoryId;
		Auditor auditor;
		CodeAudit codeAudit;

		userStoryId = super.getRequest().getData("id", int.class);
		codeAudit = this.auditorCodeAuditRepository.findOneById(userStoryId);
		auditor = codeAudit.getAuditor();

		status = codeAudit != null && super.getRequest().getPrincipal().hasRole(auditor) && codeAudit.getAuditor().equals(auditor) && codeAudit.getDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void bind(final CodeAudit object) {
		assert object != null;

		Auditor auditor;
		auditor = object.getAuditor();

		super.bind(object, "code", "executionDate", "type", "correctiveAction", "link", "project");
		object.setAuditor(auditor);
	}

	@Override
	public void load() {
		CodeAudit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.auditorCodeAuditRepository.findOneById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void validate(final CodeAudit object) {
		assert object != null;
		boolean status;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			CodeAudit existing;

			existing = this.auditorCodeAuditRepository.findOneByCode(object.getCode());
			if (existing != null)
				status = existing.getId() == object.getId();
			else
				status = false;
			super.state(existing == null || status, "code", "auditor.code-audit.error.code");
		}

		if (!super.getBuffer().getErrors().hasErrors("*"))
			super.state(object.getDraftMode(), "*", "auditor.code-audit.error.publish");

		if (!super.getBuffer().getErrors().hasErrors("executionDate"))
			super.state(object.getExecutionDate().after(Date.valueOf("2000-1-1")) || object.getExecutionDate().equals(Date.valueOf("2000-1-1")), "executionDate", "auditor.code-audit.error.executionDate");
	}

	@Override
	public void perform(final CodeAudit object) {
		assert object != null;

		this.auditorCodeAuditRepository.save(object);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;
		Dataset dataset;

		Collection<Project> projects;
		projects = this.auditorCodeAuditRepository.findAllProjects();	//TODO: cambiarlo por solo los projecto publicados

		Collection<AuditRecord> list = this.auditorAuditRecordRepository.findAllByCodeAuditId(object.getId());
		Mark mark = object.getMark(list);

		SelectChoices choicesType;
		choicesType = SelectChoices.from(CodeAuditType.class, object.getType());

		SelectChoices choicesProject;
		choicesProject = SelectChoices.from(projects, "code", object.getProject());

		dataset = super.unbind(object, "code", "executionDate", "type", "correctiveAction", "link");
		dataset.put("mark", mark);
		dataset.put("types", choicesType);
		dataset.put("project", choicesProject.getSelected().getKey());
		dataset.put("projects", choicesProject);
		dataset.put("draftMode", object.getDraftMode());
		super.getResponse().addData(dataset);
	}
}
