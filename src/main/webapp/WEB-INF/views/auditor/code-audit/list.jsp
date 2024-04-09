<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list navigable="true">
	<acme:list-column code="auditor.code-audit.list.label.code" path="code" width="10%"/>
	<acme:list-column code="auditor.code-audit.list.label.executionDate" path="executionDate" width="10%"/>
	<acme:list-column code="auditor.code-audit.list.label.type" path="type" width="10%"/>
	<acme:list-column code="auditor.code-audit.list.label.mark" path="mark" width="10%"/>
</acme:list>


