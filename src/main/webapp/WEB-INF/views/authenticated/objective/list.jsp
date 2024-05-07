<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list navigable="true">
	<acme:list-column code="authenticated.objective.list.label.instantiationMoment" path="instantiationMoment" width="10%"/>
	<acme:list-column code="authenticated.objective.list.label.title" path="title" width="10%"/>
	<acme:list-column code="authenticated.objective.list.label.description" path="description" width="10%"/>
	<acme:list-column code="authenticated.objective.list.label.priority" path="priority" width="10%"/>		
	<acme:list-column code="authenticated.objective.list.label.initiateMoment" path="initiateMoment" width="10%"/>		
	<acme:list-column code="authenticated.objective.list.label.finalizationMoment" path="finalizationMoment" width="10%"/>		
	<acme:list-column code="authenticated.objective.list.label.critical" path="critical" width="10%"/>		
	<acme:list-column code="authenticated.objective.list.label.link" path="link" width="10%"/>		
</acme:list>


<jstl:if test="${_command == 'list'}">
	<acme:button code="authenticated.objective.list.button.create" action="/authenticated/objective/create"/>
</jstl:if>