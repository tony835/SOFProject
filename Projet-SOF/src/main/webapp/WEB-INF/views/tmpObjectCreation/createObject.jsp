<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<tiles:insertDefinition name="master.page">
	<tiles:putAttribute name="title">

		<jstl:choose>
			<jstl:when test="${empty (param.cobject)}">
				<spring:message code="title.object.create" />
			</jstl:when>
			<jstl:when test="${not empty (param.cobject)}">
				<spring:message code="title.object.edit" /> ${param.cobject}
			</jstl:when>

		</jstl:choose>

	</tiles:putAttribute>
	<tiles:putAttribute name="body">


		<form:form method="post" commandName="myobject">
			<tag:textbox name="name" code="object.name" path="name" />

			<jstl:if test="${empty (param.cobject)}">
				<tag:textbox name="code" code="object.code" path="code" />
				<div class="form-group">
					<form:label class="col-sm-2 control-label" path="${path}">
						<spring:message code="createObject.select" />
					</form:label>
					<div class="col-sm-10">
						<form:select class="form-control" path="typeObject.code" multiple="false">
							<form:option value="" label="Séléctionner un type" />
							<form:options items="${typesList}" itemLabel="name" itemValue="code" />
						</form:select>
					</div>
				</div>
			</jstl:if>
			<tag:checkbox code="checkbox.mutualisable" value="mutualisable" path="mutualisable" />

			<tag:submit name="save" code="save" />
			<tag:cancel url="" code="cancel" />
		</form:form>
		<div class="center">
			<c:if test="${!empty error}">
				<p style="color: Red">
					<spring:message code="${error}" />
				</p>
			</c:if>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
