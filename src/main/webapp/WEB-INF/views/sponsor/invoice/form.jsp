<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="sponsor.invoice.form.label.code" placeholder="sponsor.invoice.form.placeholder.code" path="code"/>
	<acme:input-textbox code="sponsor.invoice.form.label.project" path="project.code" readonly="true"/>
	<acme:input-textbox code="sponsor.invoice.form.label.sponsorship" path="sponsorship.code" readonly="true"/>
	<acme:input-moment code="sponsor.invoice.form.label.registration-time" path="registrationTime"/>
	<acme:input-moment code="sponsor.invoice.form.label.due-date" path="dueDate"/>	
	<acme:input-money code="sponsor.invoice.form.label.quantity" path="quantity"/>	
	<acme:input-double code="sponsor.invoice.form.label.tax" path="tax"/>
	<jstl:if test="${_command != 'create'}">
		<acme:input-money code="sponsor.invoice.form.label.total-amount" path="totalAmount" readonly="true"/>
	</jstl:if>
	<acme:input-url code="sponsor.invoice.form.label.link" path="link"/>

	<jstl:if test="${not published}">
		<jstl:choose>
			<jstl:when test="${_command == 'create'}">
				<acme:submit code="sponsor.invoice.form.button.create" action="/sponsor/invoice/create?sponsorshipId=${param.sponsorshipId}"/>
			</jstl:when>
			<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')}">
				<acme:submit code="sponsor.invoice.form.button.update" action="/sponsor/invoice/update"/>
				<acme:submit code="sponsor.invoice.form.button.delete" action="/sponsor/invoice/delete"/>
				<acme:submit code="sponsor.invoice.form.button.publish" action="/sponsor/invoice/publish"/>
			</jstl:when>
		</jstl:choose>
	</jstl:if>
</acme:form>
