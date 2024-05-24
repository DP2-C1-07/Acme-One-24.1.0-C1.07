<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.training-module.form.label.code" path="code" readonly="true"/>
	<acme:input-select code="any.training-module.form.label.difficulty-level" path="difficultyLevel" readonly="true" choices="${difficultyLevels}"/>	
	<acme:input-moment code="any.training-module.form.label.creation-moment" path="creationMoment" readonly="true"/>
	<acme:input-moment code="any.training-module.form.label.update-moment" path="updateMoment" readonly="true"/>
	<acme:input-textarea code="any.training-module.form.label.details" path="details" readonly="true"/>	
	<acme:input-url code="any.training-module.form.label.link" path="link" readonly="true"/>
	<acme:input-integer code="any.training-module.form.label.total-time" path="totalTime" readonly="true"/>
	<acme:input-select code="any.training-module.form.label.project" path="project" choices="${projects}"/>	
</acme:form>