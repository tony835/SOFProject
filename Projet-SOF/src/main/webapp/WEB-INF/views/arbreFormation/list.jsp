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
			<spring:message code="title.arbre.list" /> ${param.code}
		</tiles:putAttribute>
		<tiles:putAttribute name="body">
			<a class="btn btn-default btn-sm" href="arbreFormation/create.htm?context=${param.code}"><spring:message
					code="arbreFormation.creerObj" /></a>
			<a class="btn btn-default btn-sm" href="formation/contributeur/edit.htm?cobject=${param.code}"><spring:message
					code="arbreFormation.gererContributeur" /></a>
			<a class="btn btn-default btn-sm" href="formation/list.htm"><spring:message code="formation.lister" /></a>
			<display:table name="formations" pagesize="20" class="displaytag" id="row" requestURI="arbreFormation/list.htm">
				<jstl:set value="${row.getValue1()}" var="val"></jstl:set>


				<display:column title="Code">
					<jstl:if test="${row.getValue1() != -1}">
						<jstl:forEach begin="0" end="${row.getValue1()}" step="1">....</jstl:forEach>${row.getValue0().getCode()}
					</jstl:if>
					<jstl:if test="${row.getValue1() == -1}">
						${row.getValue0().getCode()}
					</jstl:if>
				</display:column>
				<display:column title="Name">${row.getValue0().getName()}</display:column>
				<display:column title="Actions">
					<a class="btn btn-default btn-xs" href="arbreFormation/gestionFils.htm?cobject=${row.getValue0().getCode()} "><spring:message
							code="objet.modifierFils" /></a>
					<jstl:if test="${(row_rowNum - 1) != 0}"><a class="btn btn-default btn-xs"
						href="arbreFormation/create.htm?context=${param.code}&amp;cobject=${row.getValue0().getCode()}"><spring:message
							code="objet.modifier" /></a></jstl:if>
				</display:column>

			</display:table>

			<b>Objet non lies</b>
			<display:table name="ObjetNonLie" pagesize="20" class="displaytag" id="row" requestURI="arbreFormation/list.htm">
				<tag:column code="formation.code" property="code" sortable="true" />
				<tag:column code="formation.name" property="name" sortable="true" />
				<tag:column code="formation.type" property="typeObject.name" sortable="true" />
				<tag:column code="formation.mutualisable" property="mutualisable" sortable="true" />
				<display:column title="Actions">
					<a class="btn btn-default btn-xs"
						href="arbreFormation/create.htm?context=${param.code}&amp;cobject=${row.getCode()}"><spring:message
							code="objet.modifier" /></a>
				</display:column>
			</display:table>

		</tiles:putAttribute>
	</tiles:insertDefinition>

</jsp:root>
