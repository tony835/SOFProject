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
			<spring:message code="title.formation.list" />
		</tiles:putAttribute>

		<tiles:putAttribute name="body">

			<jstl:if test="${user.isConceptor()}">
				<a class="btn btn-default btn-sm" href="formation/edit.htm"><spring:message
						code="formation.create" /></a>
			</jstl:if>

					<jstl:if test="${!formations.isEmpty()}">

			<display:table name="formations" pagesize="20" class="displaytag"
				id="row" requestURI="formation/list.htm">
				<tag:column code="formation.code" property="code" sortable="true" />
				<tag:column code="formation.name" property="name" sortable="true" />		
				<spring:message code="formation.visibilite" var="var" />
				<display:column title="${var}" sortable="${sortable}">
				<jstl:if test="${row.visible==true}">
				<spring:message code="formation.visible"></spring:message>
				</jstl:if>
					<jstl:if test="${row.visible==false}">
				--
				</jstl:if>
				</display:column>

				<tag:column code="responsable.login" property="responsable.name"
					sortable="true" />
				<tag:column code="formation.numError" property="numError"
					sortable="true" />

				<jstl:if test="${user.isConceptor()}">
					<display:column>
						<a class="btn btn-default btn-xs"
							href="formation/edit.htm?cobject=${row.code}"><spring:message
								code="formation.edit" /></a>
					</display:column>
				</jstl:if>

				<jstl:if test="${row.responsable.login==user.login}">
					<display:column>
						<a class="btn btn-default btn-xs"
							href="arbreFormation/list.htm?code=${row.code}"><spring:message
								code="formation.editStructure" /></a>
					</display:column>
				</jstl:if>
			</display:table>
			
				</jstl:if>

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
