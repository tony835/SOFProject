<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

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

	<display:table name="contibuteurs" class="displaytag" id="row">
			    <tag:column code="contributeur.code" property="login" sortable="true" />
				<tag:column code="contributeur.name" property="name" sortable="true" />
				<display:column>
						<a href="formation/contributeur/delete.htm?login=${row.login}"> x </a>
				</display:column>
				
	</display:table>
			
<head>
<meta charset="utf-8">
<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<!-- Javascript -->
<script>
         $(function() {
            var availableTutorials = [
<jstl:forEach var="element" items= "${contibuteurs}">
"${element.name}", 
</jstl:forEach>  
            ];
            $( "#automplete-1" ).autocomplete({
               source: availableTutorials
            });
         });
      </script>
</head>
		<form id="signupForm" action="arbreFormation/contributeur/edit.htm"
			class="form-horizontal" method="post">
			<div class="form-group">

				<div class="ui-widget">
					<label for="automplete-1">Responsable: </label> 
					<input name="contrib" id="automplete-1">
				</div>
				<spring:message code="save" var="var" />
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<input class="btn btn-default" type="submit" value="${var}" />
					</div>
				</div>
			</div>
		</form>
	</tiles:putAttribute>
</tiles:insertDefinition>

