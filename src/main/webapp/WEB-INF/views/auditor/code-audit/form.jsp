<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="auditor.code-audit.form.label.code" path="code"/>
	<acme:input-textbox code="auditor.code-audit.form.label.executionDate" path="executionDate"/>
	<acme:input-textbox code="auditor.code-audit.form.label.type" path="type"/>	
	<acme:input-textbox code="auditor.code-audit.form.label.correctiveAction" path="correctiveAction"/>	
	<acme:input-textbox code="auditor.code-audit.form.label.mark" path="mark"/>	
	<acme:input-url code="auditor.code-audit.form.label.link" path="link"/>
	<acme:input-textbox code="auditor.code-audit.form.label.project" path="project"/>	
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')}">
			<acme:submit code="auditor.code-audit.form.button.update" action="/manager/project/update"/>
			<acme:submit code="auditor.code-audit.project.form.button.delete" action="/manager/project/delete"/>
			<acme:submit code="auditor.code-audit.project.form.button.publish" action="/manager/project/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="manager.project.form.button.create" action="/manager/project/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>