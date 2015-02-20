<jsp:root version="2.0" xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:tiles="http://tiles.apache.org/tags-tiles"
	xmlns="http://www.w3.org/1999/xhtml">

	<jsp:output omit-xml-declaration="false" doctype-root-element="html"
		doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
		doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" />
	<jsp:directive.page contentType="text/html" />

	<tiles:insertDefinition name="master.page">
		<tiles:putAttribute name="title">
			<spring:message code="title.accueil" />
		</tiles:putAttribute>
		<tiles:putAttribute name="body">
			<p>
				<spring:message code="welcome.greeting.prefix" />
				${name}
			</p>


		</tiles:putAttribute>
	</tiles:insertDefinition>

</jsp:root>

