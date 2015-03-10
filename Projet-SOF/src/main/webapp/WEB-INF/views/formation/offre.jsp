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
			<spring:message code="offre.list" />
		</tiles:putAttribute>
		<tiles:putAttribute name="body">

			<!-- afficher les formations en version visiteur  -->

			<jstl:if test="${user.isConnected()}">
				<a href="formation/audit.htm"> 
					<spring:message code="formation.audit" />
				</a>
			</jstl:if>

			<display:table name="FormationVisible" pagesize="20"
				class="displaytag" id="row" requestURI="visualisation/formation/offre.htm">
				<jstl:url var="details" value="/details.htm?code=${row.code}" />
				<display:column title="Nom" property="name" sortable="true"
					href="${details}">
				</display:column>
			</display:table>

		</tiles:putAttribute>
	</tiles:insertDefinition>

</jsp:root>
