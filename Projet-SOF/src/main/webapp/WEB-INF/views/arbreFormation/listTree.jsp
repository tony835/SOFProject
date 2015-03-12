<%@page import="domain.Object"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>

<%-- <tiles:insertDefinition name="master.page" flush="true"> --%>
<%-- 	<tiles:putAttribute name="title"  type="string"> --%>
<%-- 		<spring:message code="title.arbre.list" /> ${param.code} --%>
<%-- 		</tiles:putAttribute> --%>
<%-- 	<tiles:putAttribute name="body"  type="string"> --%>

		<link rel="stylesheet"
			href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
		<link rel="stylesheet" href="styles/style.css">
		<link href="http://www.jqueryscript.net/css/jquerysctipttop.css"
			rel="stylesheet" type="text/css">
		<link rel="stylesheet"
			href="http://netdna.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css">

		<body>



			<div class="container">
				<ul>
					<li><a>${formation.code}</a> 
					<jstl:forEach var="object" items="${formation.allFils}">
							<ul>
								<li><a>${object.fils.code}</a> <jstl:if
										test="${!object.fils.allFils.isEmpty()}">
										<jstl:forEach var="objecti" items="${object.fils.allFils}">

											<ul>
												<li><a>${objecti.fils.code}</a>
												<jstl:set var="objectR" value="objecti"></jstl:set>
<%-- 												<% 	while(! objectR.fils.allFils.isEmpty()) { %> --%>
													<jstl:forEach var="objectf" items="${objectR.fils.allFils}">
													

													<ul>
														<li><a>${objectf.fils.code}</a>
														<jstl:set var="objectR" value="objectf"></jstl:set>
																									
													</li>
										
													</ul>
												</jstl:forEach>
<%-- 												<% } %>											 --%>
												</li>
								
											</ul>
										</jstl:forEach>

									</jstl:if></li>
							</ul>
						</jstl:forEach></li>

				</ul>
			</div>
		</body>
		</html>
		<script
			src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
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

<%-- 	</tiles:putAttribute> --%>
<%-- </tiles:insertDefinition> --%>

