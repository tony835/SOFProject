<%@ page language="Java" contentType="text/html; charset=UTF-8"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<spring:message code="createObject.select" var="selecttype"/>

<tiles:insertDefinition name="master.page">
	<tiles:putAttribute name="title">

		<jstl:choose>
			<jstl:when test="${empty (param.cobject)}">
				<spring:message code="title.object.create" /> ${param.context} : 
							 ${formationName.name}
				
			</jstl:when>
			<jstl:when test="${not empty (param.cobject)}">
				<spring:message code="title.object.edit" /> ${myobject.catCodeName}
			</jstl:when>
		</jstl:choose>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
	<c:if test="${pageContext.response.locale == \'en\'}">
		<script type="text/javascript" src="scripts/scripts_perso/editObject.js"></script>
		</c:if>
		<c:if test="${pageContext.response.locale != \'en\'}">
		<script type="text/javascript" src="scripts/scripts_perso/editObjectFR.js"></script>
		</c:if>


		<form:form id="signupForm" method="post" commandName="myobject">
			<tag:textbox name="name" code="object.name" path="name" />

			<jstl:if test="${empty (param.cobject)}">
				<tag:textbox name="code" code="object.code" path="code" />
				<div class="form-group">
					<form:label class="col-sm-2 control-label" path="${path}">
						<spring:message code="createObject.select" />
					</form:label>
					<div class="col-sm-10">
						<form:select class="form-control" path="typeObject.code" multiple="false">
							<form:option value="" label="${selecttype}" />
							<form:options items="${typesList}" itemLabel="name" itemValue="code" />
						</form:select>
					</div>
				</div>
			</jstl:if>
			<jstl:if test="${myobject.mutualisable==true}">
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<div class="checkbox">
							<label> <form:checkbox path="mutualisable"
									value="mutualisable" onclick="return false;" />
									 <spring:message code="checkbox.mutualisable" />
							</label>
						</div>
					</div>
				</div>
			</jstl:if>
			<jstl:if test="${myobject.mutualisable!=true}">
				<tag:checkbox code="checkbox.mutualisable" value="mutualisable"
					path="mutualisable" />
			</jstl:if>






			<tag:submitAndCancel nameSubmit="save" codeCancel="cancel"
				codeSubmit="save" urlCancel="" />
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
