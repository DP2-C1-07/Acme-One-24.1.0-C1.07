<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-moment code="authenticated.objective.form.label.instantiationMoment" path="instantiationMoment"/>
	<acme:input-textbox code="authenticated.objective.form.label.title" path="title"/>
	<acme:input-textbox code="authenticated.objective.form.label.description" path="description"/>
	<acme:input-select code="authenticated.objective.form.label.priority" path="priority" choices="${statuses}"/>		
	<acme:input-moment code="authenticated.objective.form.label.initiateMoment" path="initiateMoment"/>		
	<acme:input-moment code="authenticated.objective.form.label.finalizationMoment" path="finalizationMoment"/>		
	<acme:input-checkbox code="authenticated.objective.form.label.critical" path="critical"/>		
	<acme:input-url code="authenticated.objective.form.label.link" path="link"/>


<jstl:choose>	 
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="authenticated.objective.form.button.create" action="/authenticated/objective/create"/>
		</jstl:when>		
</jstl:choose>
</acme:form>