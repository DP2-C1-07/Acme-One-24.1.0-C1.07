<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="sponsor.sponsorship.form.label.code" path="code"/>
	<acme:input-moment code="sponsor.sponsorship.form.label.moment" path="moment"/>
	<acme:input-integer code="sponsor.sponsorship.form.label.duration" path="durationDays"/>	
	<acme:input-integer code="sponsor.sponsorship.form.label.amount" path="amount"/>	
	<acme:input-select code="sponsor.sponsorship.form.label.type" path="type" choices="${types}"/>	
	<acme:input-email code="sponsor.sponsorship.form.label.email" path="contactEmail"/>
	<acme:input-url code="sponsor.sponsorship.form.label.link" path="link"/>	
	
	<jstl:choose>
		<jstl:when test="${_command == 'show'}">
			<acme:input-textbox code="sponsor.sponsorship.form.label.project" path="project.code"/>
		</jstl:when>
	</jstl:choose>
</acme:form>
