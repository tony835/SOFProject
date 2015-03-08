<jsp:root version="2.0" xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:tiles="http://tiles.apache.org/tags-tiles"
	
	xmlns="http://www.w3.org/1999/xhtml">

	<jsp:output omit-xml-declaration="false" doctype-root-element="html"
		doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
		doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" />
			<jsp:directive.page contentType="text/html" />
<html><head><title>403</title>
</head>
<body>
<p><spring:message code="url.notFount"></spring:message></p> 
<div>
<img src="../images/oops.jpg" alt=""/></div>
<spring:url value='/' var="var"/>

<p><a href="${var}">Return to index page</a></p>
</body>
</html>
</jsp:root>