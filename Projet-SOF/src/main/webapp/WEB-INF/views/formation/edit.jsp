<%@ page language="Java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>

<spring:message code="formation.select.diplomatype" var="selectdiptype" htmlEscape="false" />
<spring:message code="formation.select.formationfield" var="selectformfield" htmlEscape="false" />

<tiles:insertDefinition name="master.page">
	<tiles:putAttribute name="title">
		<spring:message code="title.formation.edit" />
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<link href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">
		<c:if test="${pageContext.response.locale == \'en\'}">
		<script type="text/javascript" src="scripts/scripts_perso/editLogin.js"></script>
		</c:if>
		<c:if test="${pageContext.response.locale != \'en\'}">
		<script type="text/javascript" src="scripts/scripts_perso/editLoginFR.js"></script>
		</c:if>
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

		<form:form id="signupForm" methode="post" modelAttribute="formation">
			<c:if test="${!empty param.cobject}">
				<div hidden="true">
					<tag:textbox name="code" code="formation.code" path="code" />
				</div>
			</c:if>
			<c:if test="${empty param.cobject}">
				<tag:textbox name="code" code="formation.code" path="code" />
			</c:if>
			<tag:textbox name="name" code="formation.nom" path="name" />
			<div class="ui-widget">
				<tag:textbox id="automplete-1" name="responsable.login" code="responsable.login" path="responsable.login" />
			</div>
			<div class="form-group">
				<form:label class="col-sm-4 control-label" path="diplomeType">
						<spring:message code="formation.select.diplomatype"></spring:message>
					</form:label>
				<div class="col-sm-8">
					<form:select class="form-control" path="diplomeType" multiple="false" modelAttribute="formation">
						<form:option value="" label="${selectdiptype}" />
						<form:options items="${diplomaTypeList}" />
					</form:select>
				</div>
			</div>
			<div class="form-group">
				<form:label class="col-sm-4 control-label" path="formationField">
						<spring:message code="formation.select.formationfield"></spring:message>
					</form:label>
				<div class="col-sm-8">
					<form:select class="form-control" path="formationField" multiple="false" modelAttribute="formation">
						<form:option value="" label="${selectformfield}" />
						<form:options items="${formationFieldList}" />
					</form:select>
				</div>
			</div>


			<tag:checkbox code="checkbox.visible" value="checkbox.visible" path="visible" />
			<tag:submitAndCancel nameSubmit="save" codeCancel="cancel" codeSubmit="save" urlCancel="" />

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
