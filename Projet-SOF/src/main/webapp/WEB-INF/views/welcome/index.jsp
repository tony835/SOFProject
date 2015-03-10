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
			Vous etes ...
		</tiles:putAttribute>
		<tiles:putAttribute name="body">
			<a href="/Projet_SOF/auth/login.htm">Concepteur ou responsable</a> </br>
			<a href="/Projet_SOF/visualisation/formation/offre.htm">Contributeur ou visiteur</a>
		</tiles:putAttribute>
	</tiles:insertDefinition>

