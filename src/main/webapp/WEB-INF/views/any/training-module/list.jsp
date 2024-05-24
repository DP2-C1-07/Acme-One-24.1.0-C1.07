<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.training-module.list.label.code" path="code" width="25%"/>
	<acme:list-column code="any.training-module.list.label.creation-moment" path="creationMoment" width="25%"/>
	<acme:list-column code="any.training-module.list.label.difficulty-level" path="difficultyLevel" width="25%"/>
	<acme:list-column code="any.training-module.list.label.total-time" path="totalTime" width="25%"/>
</acme:list>
