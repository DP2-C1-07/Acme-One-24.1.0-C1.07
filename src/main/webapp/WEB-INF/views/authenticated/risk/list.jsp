<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list navigable="true">
	<acme:list-column code="authenticated.risk.list.label.reference" path="reference" width="10%"/>
	<acme:list-column code="authenticated.risk.list.label.identificationDate" path="identificationDate" width="10%"/>
	<acme:list-column code="authenticated.risk.list.label.impact" path="impact" width="10%"/>
	<acme:list-column code="authenticated.risk.list.label.probability" path="probability" width="10%"/>
	<acme:list-column code="authenticated.risk.list.label.project" path="project" width="10%"/>				
</acme:list>

