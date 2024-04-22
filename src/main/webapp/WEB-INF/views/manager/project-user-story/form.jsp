<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-select code="manager.project-user-story.form.label.project" path="project" choices="${projects}"/>
	<acme:input-select code="manager.project-user-story.form.label.user-stories" path="user-story" choices="${userstories}"/>
	
	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="manager.project-user-story.form.button.create" action="/manager/project-user-story/create"/>
		</jstl:when>
	</jstl:choose>
</acme:form>