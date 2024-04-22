
package acme.features.auditor.dashboard;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.audit_records.AuditRecord;
import acme.features.auditor.auditrecord.AuditorAuditRecordRepository;
import acme.features.auditor.codeaudit.AuditorCodeAuditRepository;
import acme.forms.AuditorDashboard;
import acme.roles.Auditor;

@Service
public class AuditorDashboardShowService extends AbstractService<Auditor, AuditorDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorDashboardRepository		auditorDashboardRepository;

	@Autowired
	private AuditorCodeAuditRepository		auditorCodeAuditRepository;

	@Autowired
	private AuditorAuditRecordRepository	auditorAuditRecordRepository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Auditor.class);

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		AuditorDashboard dashboard;
		Principal principal;
		Collection<Long> auditRecordCount;
		Collection<AuditRecord> auditRecords;
		List<Long> listOfPeriodInAuditRecord;

		int totalStaticCodeAudits;
		int totalDynamicCodeAudits;

		Double averageAuditRecord;
		Double deviationAuditRecord;
		Integer minimumAuditRecord;
		Integer maximumAuditRecord;

		Double averageTimeOfPeriodInAuditRecord;
		Double deviationTimeOfPeriodInAuditRecord;
		Integer minimumTimeOfPeriodInAuditRecord;
		Integer maximumTimeOfPeriodInAuditRecord;

		principal = super.getRequest().getPrincipal();

		auditRecordCount = this.auditorDashboardRepository.findAuditRecordCountsByAuditorId(principal.getActiveRoleId());
		auditRecords = this.auditorAuditRecordRepository.findAllByAuditorId(principal.getActiveRoleId());
		listOfPeriodInAuditRecord = auditRecords.stream().map(auditRecord -> auditRecord.getPeriodInMinutes()).toList();

		totalStaticCodeAudits = this.auditorDashboardRepository.totalStaticCodeAudits();
		totalDynamicCodeAudits = this.auditorDashboardRepository.totalDynamicCodeAudits();

		averageAuditRecord = this.calculateAverage(auditRecordCount);
		deviationAuditRecord = this.calculateStandardDeviation(auditRecordCount);
		minimumAuditRecord = this.calculateMinimum(auditRecordCount);
		maximumAuditRecord = this.calculateMaximum(auditRecordCount);

		averageTimeOfPeriodInAuditRecord = this.calculateAverage(listOfPeriodInAuditRecord);
		deviationTimeOfPeriodInAuditRecord = this.calculateStandardDeviation(listOfPeriodInAuditRecord);
		minimumTimeOfPeriodInAuditRecord = this.calculateMinimum(listOfPeriodInAuditRecord);
		maximumTimeOfPeriodInAuditRecord = this.calculateMaximum(listOfPeriodInAuditRecord);

		dashboard = new AuditorDashboard();

		dashboard.setTotalDynamicCodeAudits(totalDynamicCodeAudits);
		dashboard.setTotalStaticCodeAudits(totalStaticCodeAudits);

		dashboard.setAverageAuditRecord(averageAuditRecord);
		dashboard.setDeviationAuditRecord(deviationAuditRecord);
		dashboard.setMinimumAuditRecord(minimumAuditRecord);
		dashboard.setMaximumAuditRecord(maximumAuditRecord);

		dashboard.setAverageTimeOfPeriodInAuditRecord(averageTimeOfPeriodInAuditRecord);
		dashboard.setDeviationTimeOfPeriodInAuditRecord(deviationTimeOfPeriodInAuditRecord);
		dashboard.setMinimumTimeOfPeriodInAuditRecord(minimumTimeOfPeriodInAuditRecord);
		dashboard.setMaximumTimeOfPeriodInAuditRecord(maximumTimeOfPeriodInAuditRecord);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final AuditorDashboard object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object,	//
			"totalStaticCodeAudits", "totalDynamicCodeAudits", "averageAuditRecord", "deviationAuditRecord", //
			"minimumAuditRecord", "maximumAuditRecord", "averageTimeOfPeriodInAuditRecord", //
			"deviationTimeOfPeriodInAuditRecord", "minimumTimeOfPeriodInAuditRecord", "maximumTimeOfPeriodInAuditRecord");

		super.getResponse().addData(dataset);
	}

	private Double calculateStandardDeviation(final Collection<Long> counts) {
		if (counts.isEmpty())
			return 0.0;
		double mean = this.calculateAverage(counts);
		double temp = 0;
		for (Long count : counts)
			temp += (count - mean) * (count - mean);
		return Math.sqrt(temp / counts.size());
	}

	private Integer calculateMinimum(final Collection<Long> counts) {
		if (counts.isEmpty())
			return 0;
		return counts.stream().min(Long::compare).orElse(0L).intValue();
	}

	private Integer calculateMaximum(final Collection<Long> counts) {
		if (counts.isEmpty())
			return 0;
		return counts.stream().max(Long::compare).orElse(0L).intValue();
	}

	private Double calculateAverage(final Collection<Long> counts) {
		if (counts.isEmpty())
			return 0.0;
		double sum = 0.0;
		for (Long count : counts)
			sum += count;
		return sum / counts.size();
	}

}
