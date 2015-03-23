<%@ page language="Java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 


	<html>
<head>


<base
	href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" />



<script type="text/javascript" src="scripts/jquery-2.1.1.min.js">n</script>
<script type="text/javascript" src="scripts/jmenu.js">n</script>
<script type="text/javascript" src="scripts/bootstrap.js">n</script>
<script type="text/javascript" src="scripts/dist/jquery.validate.min.js">n</script>

<link rel="stylesheet" href="styles/common.css" type="text/css" />
<link rel="stylesheet" href="styles/jmenu.css" media="screen"
	type="text/css" />
<link rel="stylesheet" href="styles/displaytag.css" type="text/css" />
<link href="styles/bootstrap.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="styles/Base.css" type="text/css"/>
<link rel="stylesheet" href="styles/BreadCrumb.css" type="text/css"/>

<title><tiles:insertAttribute name="title" /></title>


</head>

<body>
	<div>
		<tiles:insertAttribute name="header" />
	</div>
	<div>
		<div class="panel panel-default baseBody">
			<div class="panel-heading">
				<h3 class="panel-title" id="titleDoc">
				</h3>
			</div>
			<div class="panel-body">
				<tiles:insertAttribute name="body" />
			</div>
		</div>
		<jstl:if test="${message != null}">
			<div class="panel panel-default baseBody">
				<span class="message"><spring:message code="${message}" /></span>
			</div>
		</jstl:if>
	</div>

	<div>
		<tiles:insertAttribute name="footer" />

	</div>
</body>

	</html>
