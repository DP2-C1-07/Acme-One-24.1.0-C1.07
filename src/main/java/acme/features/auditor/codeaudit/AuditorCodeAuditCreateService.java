
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
public class AuditorCodeAuditCreateService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private AuditorCodeAuditRepository		auditorCodeAuditRepository;

	@Autowired
	private AuditorAuditRecordRepository	auditorAuditRecordRepository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		CodeAudit object;
		Auditor auditor;

		auditor = this.auditorCodeAuditRepository.findAuditorByAuditorId(super.getRequest().getPrincipal().getActiveRoleId());
		object = new CodeAudit();
		object.setAuditor(auditor);

		super.getBuffer().addData(object);
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
	public void validate(final CodeAudit object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			CodeAudit existing;

			existing = this.auditorCodeAuditRepository.findOneByCode(object.getCode());
			super.state(existing == null, "code", "auditor.code-audit.error.code");
		}

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
		projects = this.auditorCodeAuditRepository.findAllPublishedProjects();	//TODO: cambiarlo por solo los projecto publicados

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
