<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list navigable="true">
	<acme:list-column code="authenticated.notice.list.label.instantiationMoment" path="instantiationMoment" width="10%"/>
	<acme:list-column code="authenticated.notice.list.label.title" path="title" width="10%"/>
	<acme:list-column code="authenticated.notice.list.label.author" path="author" width="10%"/>
	<acme:list-column code="authenticated.notice.list.label.message" path="message" width="10%"/>		
	<acme:list-column code="authenticated.notice.list.label.email" path="email" width="10%"/>		
	<acme:list-column code="authenticated.notice.list.label.link" path="link" width="10%"/>				
</acme:list>

<jstl:if test="${_command == 'list'}">
	<acme:button code="authenticated.notice.list.button.create" action="/authenticated/notice/create"/>
</jstl:if>