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

			<jstl:if test="${user.isConnected()}">
				<a href="visualisation/formation/audit/offre.htm"> 
					<spring:message code="formation.audit" />
				</a>
			</jstl:if>

			<display:table name="DiplomaTypeExist" requestURI="visualisation/formation/offre.htm" id="field">
				<display:column title="Nom"  sortable="true">
					<jstl:forEach items="${field}" var="s" varStatus="loop">
						<jstl:url  var="fieldlink" value="visualisation/formation/field.htm?diploma=${s} "/>
						<a href="${fieldlink}"> <jstl:out value="${s}"/> </a>
					</jstl:forEach>
				</display:column>
			</display:table>
			<a href="downloadfile/xml.htm"> <spring:message code="telecharger.formation"></spring:message></a>
		</tiles:putAttribute>
	</tiles:insertDefinition>
