<jsp:root version="2.0" xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
	xmlns:jstl="http://java.sun.com/jsp/jstl/core"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:form="http://www.springframework.org/tags/form"
	xmlns:security="http://www.springframework.org/security/tags"
	xmlns:display="http://displaytag.sf.net"
	xmlns:tiles="http://tiles.apache.org/tags-tiles"
	xmlns:tag="urn:jsptagdir:/WEB-INF/tags"
	xmlns="http://www.w3.org/1999/xhtml">

	<jsp:output omit-xml-declaration="false" doctype-root-element="html"
		doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
		doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" />


	<jsp:directive.page contentType="text/html" />

	<tiles:insertDefinition name="master.page">
		<tiles:putAttribute name="title">
			<spring:message code="title.arbre.list" />
		</tiles:putAttribute>
		<tiles:putAttribute name="body">

			<display:table name="formations" pagesize="20" class="displaytag"
				id="row" requestURI="arbreFormation/list.htm">
				<jstl:set value="${row.getValue1()}" var="val"></jstl:set>
				
		
				<display:column title="Login"><jstl:forEach begin="0" end="${row.getValue1()}" step="1">....</jstl:forEach>${row.getValue0().getCode()}</display:column>
				<display:column title="Name">${row.getValue0().getName()}</display:column>
				<display:column title="Actions">Editer - Supprimer</display:column>

			</display:table>
			
		Objet non lies
			<display:table name="ObjetNonLie" pagesize="20" class="displaytag"
				id="row" requestURI="arbreFormation/list.htm">
				<tag:column code="formation.code" property="code" sortable="true" />
			</display:table>

		</tiles:putAttribute>
	</tiles:insertDefinition>

</jsp:root>
