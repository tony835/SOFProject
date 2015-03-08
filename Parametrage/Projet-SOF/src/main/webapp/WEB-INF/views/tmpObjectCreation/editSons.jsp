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

	</tiles:putAttribute>
	<tiles:putAttribute name="body">

		<form:form method="post" commandName="myobject">
			<form:select path="typeObject.code" multiple="false">
				<form:option value="" label="Séléctionner un type" />
				<form:options items="${typesList}" itemLabel="name" itemValue="code" />
			</form:select>
			<tag:submit name="save" code="save" />
			<tag:cancel url="" code="cancel" />
		</form:form>

		<form:form method="post" commandName="myobject">
			<form:select path="allFils" multiple="false">
				<form:option value="" label="Liste des objets non lies" />
				<form:options items="${NonLinkedObjectList}" itemLabel="name" itemValue="code" />
			</form:select>
			<tag:submit name="save" code="save" />
			<tag:cancel url="" code="cancel" />
		</form:form>
		
				<form:form method="post" commandName="myobject">
			<form:select path="typeObject.code" multiple="false">
				<form:option value="" label="Séléctionner un type" />
				<form:options items="${typesList}" itemLabel="name" itemValue="code" />
			</form:select>
			<tag:submit name="save" code="save" />
			<tag:cancel url="" code="cancel" />
		</form:form>

		<form:form method="post" commandName="myobject">
			<form:select path="allFils" multiple="false">
				<form:option value="" label="Liste des objets mutualisables" />
				<form:options items="${NonLinkedObjectList}" itemLabel="name" itemValue="code" />
			</form:select>
			<tag:submit name="save" code="save" />
			<tag:cancel url="" code="cancel" />
		</form:form>
		
	</tiles:putAttribute>
</tiles:insertDefinition>
