<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>


<h2>
	<acme:message code="administrator.administrator-dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="administrator.administrator-dashboard.form.label.total-administrator"/>
		</th>
		<td>
			<acme:print value="${totalAdministrator}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.administrator-dashboard.form.label.total-auditor"/>
		</th>
		<td>
			<acme:print value="${totalAuditor}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.administrator-dashboard.form.label.total-client"/>
		</th>
		<td>
			<acme:print value="${totalClient}"/>
		</td>
	</tr>		
	
	<tr>
		<th scope="row">
			<acme:message code="administrator.administrator-dashboard.form.label.total-consumer"/>
		</th>
		<td>
			<acme:print value="${totalConsumer}"/>
		</td>
	</tr>	
	
	<tr>
		<th scope="row">
			<acme:message code="administrator.administrator-dashboard.form.label.total-developer"/>
		</th>
		<td>
			<acme:print value="${totalDeveloper}"/>
		</td>
	</tr>	
	
	<tr>
		<th scope="row">
			<acme:message code="administrator.administrator-dashboard.form.label.total-manager"/>
		</th>
		<td>
			<acme:print value="${totalManager}"/>
		</td>
	</tr>	
	
	<tr>
		<th scope="row">
			<acme:message code="administrator.administrator-dashboard.form.label.total-provider"/>
		</th>
		<td>
			<acme:print value="${totalProvider}"/>
		</td>
	</tr>	
	
	<tr>
		<th scope="row">
			<acme:message code="administrator.administrator-dashboard.form.label.total-sponsor"/>
		</th>
		<td>
			<acme:print value="${totalSponsor}"/>
		</td>
	</tr>	
	
	<tr>
		<th scope="row">
			<acme:message code="administrator.administrator-dashboard.form.label.ratio-notices-with-email-and-link"/>
		</th>
		<td>
			<jstl:choose>
            <jstl:when test="${empty ratioNoticesWithEmailAndLink}">
                no data yet
            </jstl:when>
            <jstl:otherwise>
                <acme:print value="${ratioNoticesWithEmailAndLink}"/>
            </jstl:otherwise>
            </jstl:choose>
		</td>
	</tr>	
	
	<tr>
		<th scope="row">
			<acme:message code="administrator.administrator-dashboard.form.label.ratio-critical-objectives"/>
		</th>
		<td>
		<jstl:choose>
            <jstl:when test="${empty ratioCriticalObjectives}">
                no data yet
            </jstl:when>
            <jstl:otherwise>
			<acme:print value="${ratioCriticalObjectives}"/>
			</jstl:otherwise>
			</jstl:choose>
		</td>
	</tr>	
	
	<tr>
		<th scope="row">
			<acme:message code="administrator.administrator-dashboard.form.label.ratio-non-critical-objectives"/>
		</th>
		<td>
		<jstl:choose>
            <jstl:when test="${empty ratioNonCriticalObjectives}">
                no data yet
            </jstl:when>
            <jstl:otherwise>
			<acme:print value="${ratioNonCriticalObjectives}"/>
			</jstl:otherwise>
			</jstl:choose>
		</td>
	</tr>	
	
	<tr>
		<th scope="row">
			<acme:message code="administrator.administrator-dashboard.form.label.risk-value-average"/>
		</th>
		<td>
		<jstl:choose>
            <jstl:when test="${empty riskValueAverage}">
                no data yet
            </jstl:when>
            <jstl:otherwise>
			<acme:print value="${riskValueAverage}"/>
			</jstl:otherwise>
			</jstl:choose>
		</td>
	</tr>	
	
	<tr>
		<th scope="row">
			<acme:message code="administrator.administrator-dashboard.form.label.risk-value-deviation"/>
		</th>
		<td>
		<jstl:choose>
            <jstl:when test="${empty riskValueDeviation}">
                no data yet
            </jstl:when>
            <jstl:otherwise>
			<acme:print value="${riskValueDeviation}"/>
			</jstl:otherwise>
			</jstl:choose>
		</td>
	</tr>	
	
	<tr>
		<th scope="row">
			<acme:message code="administrator.administrator-dashboard.form.label.risk-value-minimum"/>
		</th>
		<td>
		<jstl:choose>
            <jstl:when test="${empty riskValueMinimum}">
                no data yet
            </jstl:when>
            <jstl:otherwise>
			<acme:print value="${riskValueMinimum}"/>
			</jstl:otherwise>
			</jstl:choose>
		</td>
	</tr>	
	
	<tr>
		<th scope="row">
			<acme:message code="administrator.administrator-dashboard.form.label.claims-posted-average"/>
		</th>
		<td>
		<jstl:choose>
            <jstl:when test="${empty claimsPostedAverage}">
                no data yet
            </jstl:when>
            <jstl:otherwise>
		
			<acme:print value="${claimsPostedAverage}"/>
			</jstl:otherwise>
			</jstl:choose>
		</td>
	</tr>	
	
	<tr>
		<th scope="row">
			<acme:message code="administrator.administrator-dashboard.form.label.claims-posted-deviation"/>
		</th>
		<td>
		<jstl:choose>
            <jstl:when test="${empty claimsPostedDeviation}">
                no data yet
            </jstl:when>
            <jstl:otherwise>
			<acme:print value="${claimsPostedDeviation}"/>
			</jstl:otherwise>
			</jstl:choose>
		</td>
	</tr>	
	
	<tr>
		<th scope="row">
			<acme:message code="administrator.administrator-dashboard.form.label.claims-posted-maximum"/>
		</th>
		<td>
		<jstl:choose>
            <jstl:when test="${empty claimsPostedMaximum}">
                no data yet
            </jstl:when>
            <jstl:otherwise>
			<acme:print value="${claimsPostedMaximum}"/>
			</jstl:otherwise>
			</jstl:choose>
		</td>
	</tr>	
	
	<tr>
		<th scope="row">
			<acme:message code="administrator.administrator-dashboard.form.label.claims-posted-minimum"/>
		</th>
		<td>
		<jstl:choose>
            <jstl:when test="${empty claimsPostedMinimum}">
                no data yet
            </jstl:when>
            <jstl:otherwise>
			<acme:print value="${claimsPostedMinimum}"/>
			</jstl:otherwise>
			</jstl:choose>
		</td>
	</tr>	
	
</table>

<div>
	<canvas id="canvas"></canvas>
</div>

<acme:return/>