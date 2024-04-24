<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
    <acme:input-select code="manager.project-user-story.list.label.project" path="project" choices="${projects}"/>
    <acme:input-select code="manager.project-user-story.list.label.userStory" path="userStory" choices="${userStories}"/>
    
    <acme:submit code="manager.project-user-story.list.button.create" action="/manager/project-user-story/create"/>
</acme:form>