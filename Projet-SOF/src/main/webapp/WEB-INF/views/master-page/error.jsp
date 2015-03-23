<%@ page language="Java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tiles:insertDefinition name="master.page">
	<tiles:putAttribute name="title">
		<spring:message code="title.error" />
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<div class="center">
			<c:if test="${!empty error}">
				<p style="color: Red">
					<spring:message code="${error}" />
				</p>
			</c:if>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
