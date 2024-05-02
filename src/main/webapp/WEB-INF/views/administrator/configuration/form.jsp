<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-moment readonly="true" code="administrator.configuration.form.label.systemCurrencies" path="systemCurrencies"/>	
	<acme:input-moment code="administrator.configuration.form.label.acceptedCurrencies" path="acceptedCurrencies"/>

	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update')}">
			<acme:submit code="administrator.configuration.form.button.update" action="/administrator/configuration/update"/>
		</jstl:when>		
	</jstl:choose>		
</acme:form>