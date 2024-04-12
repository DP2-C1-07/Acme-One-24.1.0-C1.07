<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.claim.form.label.code" path="code"/>
	<acme:input-textbox code="any.claim.form.label.instantiationMoment" path="instantiationMoment"/>
	<acme:input-textbox code="any.claim.form.label.heading" path="heading"/>	
	<acme:input-textbox code="any.claim.form.label.description" path="description"/>	
	<acme:input-textbox code="any.claim.form.label.department" path="department"/>
	<acme:input-email code="any.claim.form.label.emailAddress" path="emailAddress"/>	
	<acme:input-url code="any.claim.form.label.link" path="link"/>	
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|publish')}">
			<acme:submit code="any.claim.form.button.publish" action="/any/claim/publish"/>
		</jstl:when>
				
	</jstl:choose>
</acme:form>