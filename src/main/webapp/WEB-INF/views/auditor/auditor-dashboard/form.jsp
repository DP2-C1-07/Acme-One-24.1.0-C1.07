<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>


<h2>
	<acme:message code="auditor.auditor-dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditor-dashboard.form.label.total-static-code-audits"/>
		</th>
		<td>
			<acme:print value="${totalStaticCodeAudits}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditor-dashboard.form.label.total-dynamic-code-audits"/>
		</th>
		<td>
			<acme:print value="${totalDynamicCodeAudits}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditor-dashboard.form.label.average-audit-records"/>
		</th>
		<td>
			<acme:print value="${emptyList?emptyMessage:averageAuditRecord}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditor-dashboard.form.label.deviation-audit-record"/>
		</th>
		<td>
			<acme:print value="${emptyList?emptyMessage:deviationAuditRecord}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditor-dashboard.form.label.minimum-audit-record"/>
		</th>
		<td>
			<acme:print value="${emptyList?emptyMessage:minimumAuditRecord}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditor-dashboard.form.label.maximum-audit-record"/>
		</th>
		<td>
			<acme:print value="${emptyList?emptyMessage:maximumAuditRecord}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditor-dashboard.form.label.average-time-of-period-in-audit-record"/>
		</th>
		<td>
			<acme:print value="${emptyList?emptyMessage:averageTimeOfPeriodInAuditRecord}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditor-dashboard.form.label.deviation-time-of-period-in-audit-record"/>
		</th>
		<td>
			<acme:print value="${emptyList?emptyMessage:deviationTimeOfPeriodInAuditRecord}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditor-dashboard.form.label.minimum-time-of-period-in-audit-record"/>
		</th>
		<td>
			<acme:print value="${emptyList?emptyMessage:minimumTimeOfPeriodInAuditRecord}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditor-dashboard.form.label.maximum-time-of-period-in-audit-record"/>
		</th>
		<td>
			<acme:print value="${emptyList?emptyMessage:maximumTimeOfPeriodInAuditRecord}"/>
		</td>
	</tr>
	
	
</table>

<div>
	<canvas id="canvas"></canvas>
</div>

<acme:return/>