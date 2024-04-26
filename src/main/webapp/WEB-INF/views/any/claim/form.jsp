<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.claim.form.label.code" path="code" readonly="true"/>
	<acme:input-textbox code="any.claim.form.label.instantiationMoment" path="instantiationMoment" readonly="true"/>
	<acme:input-textbox code="any.claim.form.label.heading" path="heading" readonly="true"/>	
	<acme:input-textbox code="any.claim.form.label.description" path="description" readonly="true"/>	
	<acme:input-textbox code="any.claim.form.label.department" path="department" readonly="true"/>
	<acme:input-email code="any.claim.form.label.emailAddress" path="emailAddress" readonly="true"/>	
	<acme:input-url code="any.claim.form.label.link" path="link" readonly="true"/>
	<acme:input-textbox code="any.claim.form.label.draftMode" path="draftMode" readonly="true"/>	
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|publish') && (draftMode == 'Yes' ||draftMode == 'Sí')}">
			<acme:submit code="any.claim.form.button.publish" action="/any/claim/publish"/>
		</jstl:when>
				
	</jstl:choose>
</acme:form>