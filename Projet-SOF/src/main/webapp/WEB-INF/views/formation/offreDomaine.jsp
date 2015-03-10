<jsp:root version="2.0" xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
	xmlns:jstl="http://java.sun.com/jsp/jstl/core"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:form="http://www.springframework.org/tags/form"
	xmlns:security="http://www.springframework.org/security/tags"
	xmlns:display="http://displaytag.sf.net"
	xmlns:tiles="http://tiles.apache.org/tags-tiles"
	xmlns:tag="urn:jsptagdir:/WEB-INF/tags"
	xmlns:fn="http://java.sun.com/jsp/jstl/functions"
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


			
			

			<display:table name="FormationFieldExist" requestURI="visualisation/formation/offreDomaine.htm" id="field">
				<display:column title="Nom"  sortable="true">
					<jstl:forEach items="${field}" var="s" varStatus="loop">
						<jstl:url  var="fieldlink" value="/details.htm?field=${s} "/>
						<a href="${fieldlink}"> <jstl:out value="${fn:replace(s,';',',')}" escapeXml="true"/> </a>
					</jstl:forEach>
				</display:column>
				
			</display:table>

		</tiles:putAttribute>
	</tiles:insertDefinition>

</jsp:root>
