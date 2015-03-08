<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	<tiles:insertDefinition name="master.page">
		<tiles:putAttribute name="title">
		
			<spring:message code="title.authentification.login" />
		</tiles:putAttribute>
		<tiles:putAttribute name="body">
			<form:form method="POST" commandName="user">
			<table>
			<tr>
			<td><spring:message code="authentification.login" /></td>
			<td><form:input path="login" /></td>
			</tr>
			<tr>
			<td><spring:message code="authentification.password" /></td>
			<td><form:input path="password" /></td>
			</tr>
			<tr>
			<td colspan="3"><input type="submit" /></td>
			</tr>
			</table>
			</form:form>
				<div class="center">
					<c:if test="${!empty error}">
						<p><c:out value="${error}"/></p>
					</c:if>
					<c:if test="${user.connected}">
						<c:redirect url="/formation/list.htm" />
					</c:if>
				
					
				</div>
	</tiles:putAttribute>
</tiles:insertDefinition>