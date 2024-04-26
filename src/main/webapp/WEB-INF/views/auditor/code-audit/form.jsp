<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="auditor.code-audit.form.label.code" path="code"/>
	<acme:input-moment code="auditor.code-audit.form.label.executionDate" path="executionDate"/>
	<acme:input-select code="auditor.code-audit.form.label.type" path="type" choices="${types}"/>	
	<acme:input-textbox code="auditor.code-audit.form.label.correctiveAction" path="correctiveAction"/>	
	<acme:input-textbox code="auditor.code-audit.form.label.mark" readonly="true" path="mark"/>	
	<acme:input-url code="auditor.code-audit.form.label.link" path="link"/>
	<acme:input-select code="auditor.code-audit.form.label.project" path="project" choices="${projects}"/>	
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode}">
			<acme:submit code="auditor.code-audit.form.button.update" action="/auditor/code-audit/update"/>
			<acme:submit code="auditor.code-audit.form.button.delete" action="/auditor/code-audit/delete"/>
			<acme:submit code="auditor.code-audit.form.button.publish" action="/auditor/code-audit/publish"/>
			<acme:button code="auditor.code-audit.form.button.list.audit-record" action="/auditor/audit-record/list?codeAuditId=${id}"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && !draftMode}">
			<acme:button code="auditor.code-audit.form.button.list.audit-record" action="/auditor/audit-record/list?codeAuditId=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.code-audit.form.button.create" action="/auditor/code-audit/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>