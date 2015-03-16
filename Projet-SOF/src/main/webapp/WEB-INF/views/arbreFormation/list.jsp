<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>

<tiles:insertDefinition name="master.page">
	<tiles:putAttribute name="title">
		<spring:message code="title.arbre.list" /> ${param.code}
		</tiles:putAttribute>
	<tiles:putAttribute name="body">

		<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
		<link rel="stylesheet" href="styles/style.css">
		<link href="http://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css">
		<a class="btn btn-default btn-sm" href="arbreFormation/create.htm?context=${param.code}"><spring:message
				code="arbreFormation.creerObj" /></a>
		<a class="btn btn-default btn-sm" href="formation/contributeur/edit.htm?code=${param.code}"><spring:message
				code="arbreFormation.gererContributeur" /></a>
		<a class="btn btn-default btn-sm" href="formation/list.htm"><spring:message code="formation.lister" /></a>

       <div class="scroll">
		<jstl:if test="${!empty descErrors}">
			<p style="color: Red">${descErrors}</p>
		</jstl:if>
		</div>

		<div class="container">${arbre}</div>

		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
		<script src="scripts/MultiNestedList.js"></script>
		<script type="text/javascript">
			var _gaq = _gaq || [];
			_gaq.push([ '_setAccount', 'UA-36251023-1' ]);
			_gaq.push([ '_setDomainName', 'jqueryscript.net' ]);
			_gaq.push([ '_trackPageview' ]);

			(function() {
				var ga = document.createElement('script');
				ga.type = 'text/javascript';
				ga.async = true;
				ga.src = ('https:' == document.location.protocol ? 'https://ssl'
						: 'http://www')
						+ '.google-analytics.com/ga.js';
				var s = document.getElementsByTagName('script')[0];
				s.parentNode.insertBefore(ga, s);
			})();
			

		</script>

		<b>Objet non lies</b>
		<display:table name="ObjetNonLie" pagesize="20" class="displaytag" id="row" requestURI="arbreFormation/list.htm">
			<tag:column code="formation.code" property="code" sortable="true" />
			<tag:column code="formation.name" property="name" sortable="true" />
			<tag:column code="formation.type" property="typeObject.name" sortable="true" />
			<tag:column code="formation.mutualisable" property="mutualisable" sortable="true" />
			<display:column title="Actions">
				<a class="btn btn-default btn-xs"
					href="arbreFormation/create.htm?context=${param.code}&amp;cobject=${row.getCode()}"><spring:message
						code="objet.modifier" /></a>
			</display:column>
		</display:table>

	</tiles:putAttribute>
</tiles:insertDefinition>
