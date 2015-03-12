<jsp:root version="2.0" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
	xmlns:jstl="http://java.sun.com/jsp/jstl/core" xmlns:spring="http://www.springframework.org/tags"
	xmlns:form="http://www.springframework.org/tags/form" xmlns:security="http://www.springframework.org/security/tags"
	xmlns:display="http://displaytag.sf.net" xmlns:tiles="http://tiles.apache.org/tags-tiles"
	xmlns:tag="urn:jsptagdir:/WEB-INF/tags" xmlns="http://www.w3.org/1999/xhtml">

	<jsp:output omit-xml-declaration="false" doctype-root-element="html" doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
		doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" />


	<jsp:directive.page contentType="text/html" />

	<tiles:insertDefinition name="master.page">
		<tiles:putAttribute name="title">
			<spring:message code="title.arbre.list" />
		</tiles:putAttribute>
		<tiles:putAttribute name="body">

			<display:table name="listFils" pagesize="20" class="displaytag" id="row" requestURI="arbreFormation/gestionFils.htm">

				<display:column title="Code">${row.getFils().getCode()}</display:column>
				<display:column title="Name">${row.getFils().getName()}</display:column>

				<display:column title="Rang">
					<form action="arbreFormation/gestionFilsEditRang.htm" method="post">
						<input hidden="hidden" readonly="false" name="codeEnCours" value="${objEnCours.getCode()}"></input> <input
							hidden="hidden" readonly="false" name="cobject" value="${row.getFils().getCode()}"> </input> <input name="rang"
							value="${row.getRang()}"> </input>
						<button type="submit" name="Valider">Valider</button>
					</form>

				</display:column>
				<display:column title="Action">
					<a class="btn btn-default btn-xs"
						href="arbreFormation/supprimer.htm?pere=${objEnCours.getCode()}&amp;fils=${row.getFils().getCode()}"><spring:message
							code="arbreFormation.supprimer" /></a>
				</display:column>

			</display:table>


			<form:form action="arbreFormation/sortNLObject.htm?cobject=${objEnCours.getCode()}" method="post"
				modelAttribute="typeobject">
				<form:select name="code" path="code" multiple="false">
					<form:option value="" label="Tous les types" />
					<form:options items="${typesList}" itemLabel="name" itemValue="code" />
				</form:select>
				<tag:submit name="save" code="save" />
			</form:form>

			<form:form action="arbreFormation/addFils.htm?cobject=${objEnCours.getCode()}" method="post"
				modelAttribute="selectedFils">
				<form:select name="code" path="code" multiple="false">
					<form:option value="" label="Liste des objets non lies" />
					<form:options items="${NonLinkedObjectList}" itemLabel="name" itemValue="code" />
				</form:select>
				<tag:submit name="save" code="save" />
			</form:form>

			<form:form action="arbreFormation/addFils.htm?cobject=${objEnCours.getCode()}" method="post"
				modelAttribute="selectedFils">
				<form:select name="code" path="code" multiple="false">
					<form:option value="" label="Liste des objets non lies" />
					<form:options items="${mutualisableObjectList}" itemLabel="name" itemValue="code" />
				</form:select>
				<tag:submit name="save" code="save" />
			</form:form>



			<a class="btn btn-default btn-sm" href="arbreFormation/list.htm?code=${objEnCours.getContexte().getCode()}"><spring:message
					code="arbreFormation.lister" /></a>

			<div class="center">
				<jstl:if test="${!empty error}">
					<p style="color: Red">
						<spring:message code="${error}" />
					</p>
				</jstl:if>
			</div>
		</tiles:putAttribute>
	</tiles:insertDefinition>

</jsp:root>
