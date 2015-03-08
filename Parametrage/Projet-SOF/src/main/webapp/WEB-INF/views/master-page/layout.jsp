<jsp:root version="2.0" xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
	xmlns:jstl="http://java.sun.com/jsp/jstl/core"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:form="http://www.springframework.org/tags/form"
	xmlns:display="http://displaytag.sf.net"
	xmlns:tiles="http://tiles.apache.org/tags-tiles"
	xmlns:tag="urn:jsptagdir:/WEB-INF/tags"
	xmlns="http://www.w3.org/1999/xhtml">



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
</jsp:root>