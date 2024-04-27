<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>


<h2>
	<acme:message code="manager.manager-dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="manager.manager-dashboard.form.label.total-could-user-stories"/>
		</th>
		<td>
			<acme:print value="${totalCouldUserStories}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.manager-dashboard.form.label.total-should-user-stories"/>
		</th>
		<td>
			<acme:print value="${totalShouldUserStories}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.manager-dashboard.form.label.total-must-user-stories"/>
		</th>
		<td>
			<acme:print value="${totalMustUserStories}"/>
		</td>
	</tr>		
	
	<tr>
		<th scope="row">
			<acme:message code="manager.manager-dashboard.form.label.total-wont-user-stories"/>
		</th>
		<td>
			<acme:print value="${totalWontUserStories}"/>
		</td>
	</tr>	
	
	<tr>
		<th scope="row">
			<acme:message code="manager.manager-dashboard.form.label.user-story-estimated-cost-average"/>
		</th>
		<td>
			<jstl:choose>
            <jstl:when test="${empty userStoryEstimatedCostAverage}">
                no data yet
            </jstl:when>
            <jstl:otherwise>
                <acme:print value="${userStoryEstimatedCostAverage}"/>
            </jstl:otherwise>
            </jstl:choose>
		</td>
	</tr>	
	
	<tr>
		<th scope="row">
			<acme:message code="manager.manager-dashboard.form.label.user-story-estimated-cost-deviation"/>
		</th>
		<td>
		<jstl:choose>
            <jstl:when test="${empty userStoryEstimatedCostDeviation}">
                no data yet
            </jstl:when>
            <jstl:otherwise>
			<acme:print value="${userStoryEstimatedCostDeviation}"/>
			</jstl:otherwise>
			</jstl:choose>
		</td>
	</tr>	
	
	<tr>
		<th scope="row">
			<acme:message code="manager.manager-dashboard.form.label.maximum-user-story-estimated-cost"/>
		</th>
		<td>
		<jstl:choose>
            <jstl:when test="${empty maximumUserStoryEstimatedCost}">
                no data yet
            </jstl:when>
            <jstl:otherwise>
			<acme:print value="${maximumUserStoryEstimatedCost}"/>
			</jstl:otherwise>
			</jstl:choose>
		</td>
	</tr>	
	
	<tr>
		<th scope="row">
			<acme:message code="manager.manager-dashboard.form.label.minimum-user-story-estimated-cost"/>
		</th>
		<td>
		<jstl:choose>
            <jstl:when test="${empty minimumUserStoryEstimatedCost}">
                no data yet
            </jstl:when>
            <jstl:otherwise>
			<acme:print value="${minimumUserStoryEstimatedCost}"/>
			</jstl:otherwise>
			</jstl:choose>
		</td>
	</tr>	
	
	<tr>
		<th scope="row">
			<acme:message code="manager.manager-dashboard.form.label.project-cost-average"/>
		</th>
		<td>
		<jstl:choose>
            <jstl:when test="${empty projectCostAverage}">
                no data yet
            </jstl:when>
            <jstl:otherwise>
			<acme:print value="${projectCostAverage}"/>
			</jstl:otherwise>
			</jstl:choose>
		</td>
	</tr>	
	
	<tr>
		<th scope="row">
			<acme:message code="manager.manager-dashboard.form.label.project-cost-deviation"/>
		</th>
		<td>
		<jstl:choose>
            <jstl:when test="${empty projectCostDeviation}">
                no data yet
            </jstl:when>
            <jstl:otherwise>
			<acme:print value="${projectCostDeviation}"/>
			</jstl:otherwise>
			</jstl:choose>
		</td>
	</tr>	
	
	<tr>
		<th scope="row">
			<acme:message code="manager.manager-dashboard.form.label.maximum-project-cost"/>
		</th>
		<td>
		<jstl:choose>
            <jstl:when test="${empty maximumProjectCost}">
                no data yet
            </jstl:when>
            <jstl:otherwise>
		
			<acme:print value="${maximumProjectCost}"/>
			</jstl:otherwise>
			</jstl:choose>
		</td>
	</tr>	
	
	<tr>
		<th scope="row">
			<acme:message code="manager.manager-dashboard.form.label.minimum-project-cost"/>
		</th>
		<td>
		<jstl:choose>
            <jstl:when test="${empty minimumProjectCost}">
                no data yet
            </jstl:when>
            <jstl:otherwise>
			<acme:print value="${minimumProjectCost}"/>
			</jstl:otherwise>
			</jstl:choose>
		</td>
	</tr>	
	
</table>

<div>
	<canvas id="canvas"></canvas>
</div>

<acme:return/>