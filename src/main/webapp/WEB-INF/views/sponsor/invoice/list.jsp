<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="sponsor.invoice.list.label.code" path="code" width="10%"/>
	<jstl:if test="${param.sponsorshipId == null}">
		<acme:list-column code="sponsor.invoice.list.label.project-code" path="projectCode" width="10%"/>
		<acme:list-column code="sponsor.invoice.list.label.sponsorship-code" path="sponsorshipCode" width="10%"/>
	</jstl:if>
	<acme:list-column code="sponsor.invoice.list.label.registration-time" path="registrationTime" width="10%"/>
	<acme:list-column code="sponsor.invoice.list.label.due-date" path="dueDate" width="10%"/>
	<acme:list-column code="sponsor.invoice.list.label.total-amount" path="totalAmount" width="10%"/>
	<acme:list-column code="sponsor.invoice.list.label.published" path="published" width="10%"/>
</acme:list>

<jstl:if test="${param.sponsorshipId != null}">
	<acme:button code="sponsor.invoice.list.button.create" action="/sponsor/invoice/create?sponsorshipId=${param.sponsorshipId}"/>
</jstl:if>
