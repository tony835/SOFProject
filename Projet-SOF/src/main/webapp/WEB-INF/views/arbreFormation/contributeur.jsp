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
		
			<spring:message code="title.conributeur.edit" />
			
			
		</tiles:putAttribute>
		<tiles:putAttribute name="body">
		
	<form id="signupForm" action="formation/editContributeur.htm"
				class="form-horizontal" method="post">
				<div class="form-group">
				
					<div class="col-sm-7">
						<input class="form-control" name="contributeur" />
						<form:errors class="error" path="login" />
					</div>
				</div>
				<spring:message code="master.page.send" var="var" />
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<input class="btn btn-default" type="submit" value="${var}" />
					</div>
				</div>
			</form>
		</tiles:putAttribute>
	</tiles:insertDefinition>
