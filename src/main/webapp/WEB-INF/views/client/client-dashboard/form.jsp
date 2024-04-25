<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
    <acme:message code="client.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
        <th scope="row">
            <acme:message code="client.dashboard.form.label.progress-log-less-25"/>
        </th>
        <td>
            <acme:print value="${progressLogByCompletenessRate == null? '-' : progressLogByCompletenessRate['25']}"/>
        </td>
    </tr>
    <tr>
        <th scope="row">
            <acme:message code="client.dashboard.form.label.progress-log-between-25-50"/>
        </th>
        <td>
            <acme:print value="${progressLogByCompletenessRate == null? '-': progressLogByCompletenessRate['50']}"/>
        </td>
    </tr>
    <tr>
        <th scope="row">
            <acme:message code="client.dashboard.form.label.progress-log-between-50-75"/>
        </th>
        <td>
            <acme:print value="${progressLogByCompletenessRate == null? '-': progressLogByCompletenessRate['75']}"/>
        </td>
    </tr>
    <tr>
        <th scope="row">
            <acme:message code="client.dashboard.form.label.progress-log-more-75"/>
        </th>
        <td>
            <acme:print value="${progressLogByCompletenessRate == null? '-': progressLogByCompletenessRate['100']}"/>
        </td>
    </tr>  
    <tr>
        <th scope="row">
            <acme:message code="client.dashboard.form.label.contract-average"/>
        </th>
        <td>
            <acme:print value="${averageBudget == null? '-' : averageBudget}"/>
        </td>
    </tr>
    <tr>
        <th scope="row">
            <acme:message code="client.dashboard.form.label.contract-deviation"/>
        </th>
        <td>
            <acme:print value="${deviationBudget == null? '-' : deviationBudget}"/>
        </td>
    </tr>
    <tr>
        <th scope="row">
            <acme:message code="client.dashboard.form.label.contract-minimum"/>
        </th>
        <td>
            <acme:print value="${minimumBudget == null? '-' : minimumBudget}"/>
        </td>
    </tr>    
    <tr>
        <th scope="row">
            <acme:message code="client.dashboard.form.label.contract-maximum"/>
        </th>
        <td>
            <acme:print value="${maximumBudget == null? '-' : maximumBudget}"/>
        </td>
    </tr>
     
</table>

<div>
    <canvas id="canvas"></canvas>
</div>

<acme:return/>