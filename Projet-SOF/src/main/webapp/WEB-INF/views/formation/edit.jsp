<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tiles:insertDefinition name="master.page">
	<tiles:putAttribute name="title">
		<spring:message code="title.formation.edit" />
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<link href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">
		<script type="text/javascript" src="scripts/scripts_perso/editLogin.js"></script>
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

		<form:form id="signupForm" methode="post" modelAttribute="formation">
			<tag:textbox name="code" code="formation.code" path="code" />
			<tag:textbox name="name" code="formation.nom" path="name" />
			<div class="ui-widget">
				<tag:textbox id="automplete-1" name="responsable.login" code="responsable.login" path="responsable.login" />
			</div>
			<tag:checkbox code="checkbox.visible" value="checkbox.visible" path="visible" />
			<tag:submit name="save" code="save" />
			<tag:cancel url="" code="cancel" />
		</form:form>
		<div class="center">
			<c:if test="${!empty error}">
				<p style="color: Red">
					<spring:message code="${error}" />
				</p>
			</c:if>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
