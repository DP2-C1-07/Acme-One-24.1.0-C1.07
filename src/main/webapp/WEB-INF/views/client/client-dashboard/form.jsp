<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
	<acme:message code="client.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">

	<%--
	-TODO
	--%>
	
	<tr>
		<th scope="row">
			<acme:message code="client.dashboard.form.label.contract-average"/>
		</th>
		<td>
			<acme:print value="${averageBudget}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.dashboard.form.label.contract-deviation"/>
		</th>
		<td>
			<acme:print value="${deviationBudget}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.dashboard.form.label.contract-minimum"/>
		</th>
		<td>
			<acme:print value="${minimumBudget}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="client.dashboard.form.label.contract-maximum"/>
		</th>
		<td>
			<acme:print value="${maximumBudget}"/>
		</td>
	</tr>			
</table>

<div>
	<canvas id="canvas"></canvas>
</div>

<acme:return/>