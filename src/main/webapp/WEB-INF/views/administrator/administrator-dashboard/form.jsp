<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>


<h2>
	<acme:message code="administrator.dashboard.form.title.role-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row" width="90%">
			<acme:message code="administrator.dashboard.form.label.administrators"/>
		</th>
		<td>
			<acme:print value="${administrators}"/>
		</td>
	</tr>
	<tr>
		<th scope="row" width="90%">
			<acme:message code="administrator.dashboard.form.label.auditors"/>
		</th>
		<td>
			<acme:print value="${auditors}"/>
		</td>
	</tr>
	<tr>
		<th scope="row" width="90%">
			<acme:message code="administrator.dashboard.form.label.clients"/>
		</th>
		<td>
			<acme:print value="${clients}"/>
		</td>
	</tr>
	<tr>
		<th scope="row" width="90%">
			<acme:message code="administrator.dashboard.form.label.consumers"/>
		</th>
		<td>
			<acme:print value="${consumers}"/>
		</td>
	</tr>
	<tr>
		<th scope="row" width="90%">
			<acme:message code="administrator.dashboard.form.label.developers"/>
		</th>
		<td>
			<acme:print value="${developers}"/>
		</td>
	</tr>
	<tr>
		<th scope="row" width="90%">
			<acme:message code="administrator.dashboard.form.label.managers"/>
		</th>
		<td>
			<acme:print value="${managers}"/>
		</td>
	</tr>
	<tr>
		<th scope="row" width="90%">
			<acme:message code="administrator.dashboard.form.label.providers"/>
		</th>
		<td>
			<acme:print value="${providers}"/>
		</td>
	</tr>
	<tr>
		<th scope="row" width="90%">
			<acme:message code="administrator.dashboard.form.label.sponsors"/>
		</th>
		<td>
			<acme:print value="${sponsors}"/>
		</td>
	</tr>
</table>

<h2>
	<acme:message code="administrator.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row" width="90%">
			<acme:message code="administrator.dashboard.form.label.notices-with-email-and-link-ratio"/>
		</th>
		<td>
			<acme:print value="${noticesWithEmailAndLinkRatio}"/>
		</td>
	</tr>
	<tr>
		<th scope="row" width="90%" width="90%">
			<acme:message code="administrator.dashboard.form.label.critical-objectives-ratio"/>
		</th>
		<td>
			<acme:print value="${criticalObjectivesRatio}"/>
		</td>
	</tr>
	<tr>
		<th scope="row" width="90%">
			<acme:message code="administrator.dashboard.form.label.non-critical-objectives-ratio"/>
		</th>
		<td>
			<acme:print value="${nonCriticalObjectivesRatio}"/>
		</td>
	</tr>
	
	<tr>
		<th scope="row" width="90%">
			<acme:message code="administrator.dashboard.form.label.average-risk-value"/>
		</th>
		<td>
			<acme:print value="${averageRiskValue}"/>
		</td>
	</tr>
	<tr>
		<th scope="row" width="90%">
			<acme:message code="administrator.dashboard.form.label.risk-value-deviation"/>
		</th>
		<td>
			<acme:print value="${riskValueDeviation}"/>
		</td>
	</tr>
	<tr>
		<th scope="row" width="90%">
			<acme:message code="administrator.dashboard.form.label.minimum-risk-value"/>
		</th>
		<td>
			<acme:print value="${minimumRiskValue}"/>
		</td>
	</tr>
	<tr>
		<th scope="row" width="90%">
			<acme:message code="administrator.dashboard.form.label.maximum-risk-value"/>
		</th>
		<td>
			<acme:print value="${maximumRiskValue}"/>
		</td>
	</tr>
</table>

<h2>
	<acme:message code="administrator.dashboard.form.title.claims-posted-by-week"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row" width="90%">
			<acme:message code="administrator.dashboard.form.label.average-claims-posted"/>
		</th>
		<td>
			<acme:print value="${averageClaimsPosted}"/>
		</td>
	</tr>
	<tr>
		<th scope="row" width="90%">
			<acme:message code="administrator.dashboard.form.label.claims-posted-deviation"/>
		</th>
		<td>
			<acme:print value="${claimsPostedDeviation}"/>
		</td>
	</tr>
	<tr>
		<th scope="row" width="90%">
			<acme:message code="administrator.dashboard.form.label.minimum-claims-posted"/>
		</th>
		<td>
			<acme:print value="${minimumClaimsPosted}"/>
		</td>
	</tr>
	<tr>
		<th scope="row" width="90%">
			<acme:message code="administrator.dashboard.form.label.maximum-claims-posted"/>
		</th>
		<td>
			<acme:print value="${maximumClaimsPosted}"/>
		</td>
	</tr>
</table>