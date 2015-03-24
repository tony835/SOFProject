<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tiles:insertDefinition name="master.page">
	<tiles:putAttribute name="title">

		<spring:message code="title.authentification.login" />
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<c:if test="${pageContext.response.locale == \'en\'}">
			<script type="text/javascript" src="scripts/scripts_perso/login.js"></script>
		</c:if>
		<c:if test="${pageContext.response.locale != \'en\'}">
			<script type="text/javascript" src="scripts/scripts_perso/loginFR.js"></script>
		</c:if>
		<form:form id="signupForm" method="POST" commandName="user">
			<tag:textboxMini name="login" code="authentification.login" path="login" /><div><br/><br/><br/></div>
			<tag:textboxMini type="password" size="8" name="password" code="authentification.password" path="password" />
			<div><br/><br/></div>
			<tag:submit name="valid" code="valid"></tag:submit>
		</form:form>
		<div class="center">
			<c:if test="${!empty error}">
				<p style="color: Red">
					<spring:message code="${error}" />
				</p>
			</c:if>
			<c:if test="${user.connected}">
				<c:redirect url="/formation/list.htm" />
			</c:if>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>