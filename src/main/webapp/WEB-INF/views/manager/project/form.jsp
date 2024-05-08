<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="manager.project.form.label.code" path="code"/>
	<acme:input-textbox code="manager.project.form.label.title" path="title"/>
	<acme:input-textbox code="manager.project.form.label.projectAbstract" path="projectAbstract"/>	
	<acme:input-integer code="manager.project.form.label.cost" path="cost"/>	
	<acme:input-url code="manager.project.form.label.link" path="link"/>
	<acme:input-checkbox code="manager.project.form.label.indication" path="indication"/>	
	
	<jstl:choose>
	<jstl:when test="${_command != 'create'}">
			<acme:input-textbox code="manager.project.form.label.draftMode" path="draftMode" readonly="true"/>
			<acme:button code="manager.project.form.button.user-stories" action="/manager/user-story/list-mine?projectId=${id}"/>
	</jstl:when>
	</jstl:choose>
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && (draftMode == 'Yes' ||draftMode == 'Sí')}">
			<acme:submit code="manager.project.form.button.update" action="/manager/project/update"/>
			<acme:submit code="manager.project.form.button.delete" action="/manager/project/delete"/>
			<acme:submit code="manager.project.form.button.publish" action="/manager/project/publish"/>
		</jstl:when>
		
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="manager.project.form.button.create" action="/manager/project/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>