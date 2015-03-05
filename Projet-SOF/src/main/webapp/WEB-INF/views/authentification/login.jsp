<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>

	<tiles:insertDefinition name="master.page">
		<tiles:putAttribute name="title">
		
			<spring:message code="title.authentification.login" />
			
			
		</tiles:putAttribute>
		<tiles:putAttribute name="body">
		
			<form:form action="login.htm"  modelAttribute="authentification">
				<tag:textbox name="login" code="authentification.login" path="login" />
				<tag:textbox name="password" code="authentification.password" path="password" />
				<tag:submit name="valider" code="valid" />
				
			</form:form>
			<div class="center">
				<c:if test="${!empty error}">
					<p>
						<c:out value="${error}"/>
					</p>
				</c:if>
			</div>
		</tiles:putAttribute>
	</tiles:insertDefinition>