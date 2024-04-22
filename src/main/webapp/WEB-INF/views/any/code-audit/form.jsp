<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
		<acme:input-textbox code="any.code-audit.form.label.code" path="code"/>
	<acme:input-moment code="any.code-audit.form.label.executionDate" path="executionDate"/>
	<acme:input-textbox code="any.code-audit.form.label.type" path="type"/>	
	<acme:input-textbox code="any.code-audit.form.label.correctiveAction" path="correctiveAction"/>	
	<acme:input-textbox code="any.code-audit.form.label.mark" path="mark"/>	
	<acme:input-url code="any.code-audit.form.label.link" path="link"/>
	<acme:input-textbox code="any.code-audit.form.label.project" path="project"/>
</acme:form>

<acme:button code="any.code-audit.form.button.list.audit-record" action="/any/audit-record/list?codeAuditId=${id}"/>