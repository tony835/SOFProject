<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>


	<tiles:insertDefinition name="master.page">
		<tiles:putAttribute name="title">
			<spring:message code="offre.list" />
		</tiles:putAttribute>
		<tiles:putAttribute name="body">

			<jstl:choose>
			<jstl:when test="${user.isConnected()} && ${Audit}">
			<display:table name="FormationOfDiplomaAndField" requestURI="visualisation/formation/audit/listformation.htm" id="formation">
				<display:column title="Nom"  sortable="true">

						<jstl:url  var="fieldlink" value="objectVisualisation/audit/details.htm?code=${formation.getCode()} "/>
						<a href="${fieldlink}"> <jstl:out value="${formation.getName()}" escapeXml="true"/> </a>
						
				</display:column>
				
			</display:table>
			</jstl:when>
			<jstl:otherwise>
        			<jstl:out value="Vous n'êtes pas connecté, merci de bien vouloir vous authentifier" />
        			<a href="/Projet_SOF/auth/login.htm"><spring:message code="login" /></a>
   			 </jstl:otherwise>
		</jstl:choose>

		</tiles:putAttribute>
	</tiles:insertDefinition>
