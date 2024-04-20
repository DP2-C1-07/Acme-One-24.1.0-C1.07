<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.project.list.label.code" path="code" width="10%"/>
	<acme:list-column code="any.project.list.label.executionDate" path="executionDate" width="10%"/>
	<acme:list-column code="any.project.list.label.type" path="type" width="10%"/>
</acme:list>
