<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list navigable="true">
	<acme:list-column code="manager.user-story.list.label.title" path="title" width="10%"/>
	<acme:list-column code="manager.user-story.list.label.description" path="description" width="10%"/>
	<acme:list-column code="manager.user-story.list.label.estimatedCost" path="estimatedCost" width="10%"/>
	<acme:list-column code="manager.user-story.list.label.acceptanceCriteria" path="acceptanceCriteria" width="10%"/>
	<acme:list-column code="manager.user-story.list.label.cost" path="cost" width="10%"/>
	<acme:list-column code="manager.user-story.list.label.link" path="link" width="10%"/>
</acme:list>

<jstl:if test="${_command == 'list'}">
	<acme:button code="manager.user-story.list.button.create" action="/manager/user-story/create"/>
</jstl:if>