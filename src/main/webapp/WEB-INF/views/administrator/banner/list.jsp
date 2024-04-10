<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list navigable="true">
	<acme:list-column code="administrator.banner.list.label.instantiationMoment" path="instantiationMoment" width="20%"/>
	<acme:list-column code="administrator.banner.list.label.lastUpdateMoment" path="lastUpdateMoment" width="20%"/>
	<acme:list-column code="administrator.banner.list.label.displayPeriodBeginning" path="displayPeriodBeginning" width="20%"/>
	<acme:list-column code="administrator.banner.list.label.displayPeriodEnd" path="displayPeriodEnd" width="20%"/>
	<acme:list-column code="administrator.banner.list.label.slogan" path="slogan" width="20%"/>	
</acme:list>

<acme:button code="administrator.banner.list.button.create" action="/administrator/company/create"/>