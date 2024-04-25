<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>


<acme:list>
    <acme:list-column code="manager.project-user-story.list.label.project" path="project"/>
    <acme:list-column code="manager.project-user-story.list.label.userStory" path="userStory"/>
    </acme:list>
    
    <jstl:if test="${_command == 'list'}">
    <acme:button code="manager.project-user-story.list.button.create" action="/manager/project-user-story/create"/>
</jstl:if>