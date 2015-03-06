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
		
			<a href="arbreFormation/edit.htm?code=${row.code}"><spring:message
								code="formation.create" /></a>
								
										${user.login}
								
			<display:table name="formations" pagesize="20" class="displaytag" id="row" requestURI="formation/list.htm">
				<tag:column code="formation.code" property="code" sortable="true" />
				<tag:column code="formation.visible" property="visible" sortable="true" />
				<tag:column code="responsable.login" property="responsable.login" sortable="true" />
				<tag:column code="formation.numError" property="numError" sortable="true" />
		
					
				
				<jstl:if test="${user.isConceptor()}">
				<display:column>
						<a href="formation/edit.htm?code=${row.code}"><spring:message
								code="formation.edit" /></a>
				</display:column>
				</jstl:if>
			
				<display:column>
						<a href="arbreFormation/edit.htm?code=${row.code}"><spring:message
								code="formation.editStructure" /></a>
				</display:column>
				
			</display:table>
			
	

		</tiles:putAttribute>
	</tiles:insertDefinition>

</jsp:root>
