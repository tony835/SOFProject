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
		
			<spring:message code="title.object.create" />
			
		</tiles:putAttribute>
		<tiles:putAttribute name="body">
		
			<form:form action="object/create.htm" modelAttribute="object">
			<tag:textbox name="name" code="object.name" path="name" />
			<tag:textbox name="code" code="object.code" path="code" />
			
			<form:select path="typeObject" multiple="false">
			<form:option value="" label="Séléctionner un type" />
			<form:options items="${typeList}" itemLabel="name"
			itemValue="name" />
			</form:select>

			<form:checkbox path="mutualisable" value="mutualisable" />Mutualisable
			<tag:submit name="save" code="save" />
			<tag:cancel url="" code="cancel" />
		</form:form>
		</tiles:putAttribute>
	</tiles:insertDefinition>
