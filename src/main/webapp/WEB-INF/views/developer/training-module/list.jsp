<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list navigable="true">
	<acme:list-column code="developer.training-module.list.label.code" path="code" width="25%"/>
	<acme:list-column code="developer.training-module.list.label.creation-moment" path="creationMoment" width="25%"/>
	<acme:list-column code="developer.training-module.list.label.difficulty-level" path="difficultyLevel" width="25%"/>
	<acme:list-column code="developer.training-module.list.label.total-time" path="totalTime" width="25%"/>	
	
</acme:list>

<jstl:if test="${_command == 'list-mine'}">
	<acme:button code="developer.training-module.list.button.create" action="/developer/training-module/create"/>
</jstl:if>		
	

