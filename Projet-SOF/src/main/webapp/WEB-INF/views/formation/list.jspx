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
			<spring:message code="title.groupe.list" />
		</tiles:putAttribute>
		<tiles:putAttribute name="body">
			<display:table name="groupes" pagesize="20" class="displaytag"
				id="row" requestURI="groupe/list.htm">
				<tag:column code="groupe.nom" property="nom" sortable="true" />

				<security:authorize access="hasRole('ADMIN')">
					<spring:message code="action.message" var="var" />
					<display:column title="${var}">
						<a href="groupe/edit.htm?groupeId=${row.id}"><spring:message
								code="action.groupe.edit" /></a> - 
						<a href="groupe/remove.htm?groupeId=${row.id}"><spring:message
								code="action.groupe.remove" /></a>
					</display:column>
				</security:authorize>
			</display:table>

			<a href="groupe/edit.htm"><spring:message
					code="action.groupe.add" /></a>
		</tiles:putAttribute>
	</tiles:insertDefinition>

</jsp:root>
