
	
<%@ page language="Java" contentType="text/html; charset=UTF-8"%>


<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>



<tiles:insertDefinition name="master.page">
	<tiles:putAttribute name="title">

		<spring:message code="title.conributeur.edit" />	
		</tiles:putAttribute>
	<tiles:putAttribute name="body">
<div id="contributeurList">

    <h2>Liste des contributeurs</h2>

    <ul>
        <li ng-repeat="person in persons">
            <span class="todoTitle">{{person.name}},{{person.surname}}</span> :
            <span class="todoDescription">{{person.login}}</span> &nbsp;
            <button class="button" ng-click="deleteContributeur(person.login)">X</button>
        </li>
    </ul>

    <hr/>

    <button ng-click="gotoTodoNewPage()" class="button">Ajouter</button>

</div>

</body>

	</tiles:putAttribute>
</tiles:insertDefinition>




