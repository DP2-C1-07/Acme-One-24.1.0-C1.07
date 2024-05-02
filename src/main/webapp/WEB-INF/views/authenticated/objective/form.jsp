<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.objective.list.form.instantiationMoment" path="instantiationMoment"/>
	<acme:input-textbox code="authenticated.objective.list.form.title" path="title"/>
	<acme:input-textbox code="authenticated.objective.list.form.description" path="description"/>
	<acme:input-textbox code="authenticated.objective.list.form.priority" path="priority"/>		
	<acme:input-textbox code="authenticated.objective.list.form.initiateMoment" path="initiateMoment"/>		
	<acme:input-textbox code="authenticated.objective.list.form.finalizationMoment" path="finalizationMoment"/>		
	<acme:input-textbox code="authenticated.objective.list.form.critical" path="critical"/>		
	<acme:input-textbox code="authenticated.objective.list.form.link" path="link"/>
</acme:form>
