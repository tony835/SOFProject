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
		<jstl:if test="${contibuteurs != null}">

			<jstl:set var="formation" value="${code}"></jstl:set>
			<div id="scroll" class="scroll">
				<display:table name="contibuteurs" class="displaytag" id="row" requestURI="formation/contributeur/edit.htm">
					<tag:column code="contributeur.code" property="login" sortable="true" />
					<tag:column code="contributeur.name" property="name" sortable="true" />
					<display:column>
						<a class="btn btn-default btn-xs"
									href="formation/contributeur/delete.htm?contrib=${row.login}&code=${formation}"><spring:message
										code="arbreFormation.supprimer" /></a>
					</display:column>

				</display:table>
			</div>

			<head>
<meta charset="utf-8">
<link href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<!-- Javascript -->
<script>
         $(function() {
            var availableTutorials = [
<jstl:forEach var="element" items= "${allcontributeurs}">
{ label: "${element.name} ${element.firstName}", value: "${element.login}" },
</jstl:forEach>  
            ];
            $( "#automplete-1" ).autocomplete({
               source: availableTutorials
            });
         });
      </script>
			</head>
			<form id="signupForm" action="formation/contributeur/edit.htm" class="form-horizontal" method="post">
				<div class="form-group">
					<div class="ui-widget">
						<label class="col-sm-2 control-label" for="automplete-1">Contributeur: </label>
						<div class="col-sm-10">
							<input name="contrib" id="automplete-1">
						</div>
					</div>
					<input hidden="hidden" name="code" value="${formation}">
					<tag:submitAndCancel nameSubmit="save" codeCancel="cancel" codeSubmit="save" urlCancel="" />
				</div>
			</form>
			<a class="btn btn-default btn-sm" href="arbreFormation/list.htm?code=${param.code}"><spring:message
					code="arbreFormation.lister" /></a>
		</jstl:if>
		<jstl:if test="${contibuteurs == null}">
			<spring:message code="contributeur.edit.erreur.formErr" />
			<tag:back codeBack="general.back" />
		</jstl:if>
	</tiles:putAttribute>
</tiles:insertDefinition>
