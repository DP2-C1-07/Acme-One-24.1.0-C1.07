<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>


<h2>
	<acme:message code="manager.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.total-could-user-stories"/>
		</th>
		<td>
			<acme:print value="${totalCouldUserStories}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.total-should-user-stories"/>
		</th>
		<td>
			<acme:print value="${totalShouldUserStories}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.total-must-user-stories"/>
		</th>
		<td>
			<acme:print value="${totalMustUserStories}"/>
		</td>
	</tr>		
	
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.total-wont-user-stories"/>
		</th>
		<td>
			<acme:print value="${totalWontUserStories}"/>
		</td>
	</tr>	
	
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.user-story-estimated-cost-average"/>
		</th>
		<td>
			<acme:print value="${userStoryEstimatedCostAverage}"/>
		</td>
	</tr>	
	
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.user-story-estimated-cost-deviation"/>
		</th>
		<td>
			<acme:print value="${userStoryEstimatedCostDeviation}"/>
		</td>
	</tr>	
	
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.maximum-user-story-estimated-cost"/>
		</th>
		<td>
			<acme:print value="${maximumUserStoryEstimatedCost}"/>
		</td>
	</tr>	
	
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.minimum-user-story-estimated-cost"/>
		</th>
		<td>
			<acme:print value="${minimumUserStoryEstimatedCost}"/>
		</td>
	</tr>	
	
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.project-cost-average"/>
		</th>
		<td>
			<acme:print value="${projectCostAverage}"/>
		</td>
	</tr>	
	
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.project-cost-deviation"/>
		</th>
		<td>
			<acme:print value="${projectCostDeviation}"/>
		</td>
	</tr>	
	
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.maximum-project-cost"/>
		</th>
		<td>
			<acme:print value="${maximumProjectCost}"/>
		</td>
	</tr>	
	
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.minimum-project-cost"/>
		</th>
		<td>
			<acme:print value="${minimumProjectCost}"/>
		</td>
	</tr>	
	
</table>

<h2>
	<acme:message code="manager.dashboard.form.title.application-statuses"/>
</h2>

<div>
	<canvas id="canvas"></canvas>
</div>

<acme:return/>