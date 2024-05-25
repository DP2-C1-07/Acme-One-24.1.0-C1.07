
<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
	<acme:message code="sponsor.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.invoices-with-tax-less-or-equal-than-21-percent"/>
		</th>
		<td>
			<acme:print value="${invoicesWithTaxLessOrEqualThan21Percent}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.sponsorships-with-link"/>
		</th>
		<td>
			<acme:print value="${sponsorshipsWithLink}"/>
		</td>
	</tr>
	
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.average-sponsorship-amount"/>
		</th>
		<td>
			<acme:print value="${averageSponsorshipAmount}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.deviation-sponsorship-amount"/>
		</th>
		<td>
			<acme:print value="${deviationSponsorshipAmount}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.minimum-sponsorship-amount"/>
		</th>
		<td>
			<acme:print value="${minimumSponsorshipAmount}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.maximum-sponsorship-amount"/>
		</th>
		<td>
			<acme:print value="${maximumSponsorshipAmount}"/>
		</td>
	</tr>
	
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.average-invoice-quantity"/>
		</th>
		<td>
			<acme:print value="${averageInvoiceQuantity}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.deviation-invoice-quantity"/>
		</th>
		<td>
			<acme:print value="${deviationInvoiceQuantity}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.minimum-invoice-quantity"/>
		</th>
		<td>
			<acme:print value="${minimumInvoiceQuantity}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.maximum-invoice-quantity"/>
		</th>
		<td>
			<acme:print value="${maximumInvoiceQuantity}"/>
		</td>
	</tr>
</table>
