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

		<spring:message code="title.formation.edit" />


	</tiles:putAttribute>
	<tiles:putAttribute name="body">
	
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
<jstl:forEach var="element" items= "${allPersons}">
{ label: "${element.name} ${element.firstName}", value: "${element.login}" },
</jstl:forEach>  
            ];
            $( "#automplete-1" ).autocomplete({
               source: availableTutorials
            });
         });
      </script>
</head>

		<form:form action="formation/edit.htm" modelAttribute="formation">
			<tag:textbox name="code" code="formation.code" path="code" />
			<tag:textbox name="name" code="formation.nom" path="name" />
<%-- 			<tag:textbox name="responsable" code="responsable.login" --%>
<%-- 				path="responsable.login" /> --%>
				
			<div class="ui-widget">
				<form:label for="automplete-1" path="responsable.login">
					<spring:message code="responsable.login" />
				</form:label>
				<form:input class="form-control" path="responsable.login" id="automplete-1" ></form:input>
				<form:errors name="responsable.login" path="responsable.login" cssClass="error" />
			</div>

			<form:checkbox path="visible" value="visible" />Visible
				<br>

			<tag:submit name="save" code="save" />
			<tag:cancel url="" code="cancel" />
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
