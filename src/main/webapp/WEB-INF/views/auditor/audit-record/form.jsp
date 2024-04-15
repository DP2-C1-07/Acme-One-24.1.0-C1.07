<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="auditor.audit-record.form.label.code" path="code"/>
	<acme:input-moment code="auditor.audit-record.form.label.periodBeginning" path="periodBeginning"/>
	<acme:input-textbox code="auditor.audit-record.form.label.periodEnd" path="periodEnd"/>	
	<acme:input-textbox code="auditor.audit-record.form.label.mark" path="mark"/>	
	<acme:input-url code="auditor.audit-record.form.label.link" path="link"/>

</acme:form>