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


<tiles:insertDefinition name="master.page">
	<tiles:putAttribute name="title">
		<spring:message code="offre.list" />
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
			<nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target="#myNavbar">
						<span class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="/Projet_SOF/welcome/index.htm">SOF</a>
				</div>
				<div class="collapse navbar-collapse" id="myNavbar">
					<ul class="nav navbar-nav">
						<li><a href="visualisation/formation/offre.htm">Offre de formation</a></li>
						<jstl:if test="${user.isConnected()}">
						<li><a href="visualisation/formation/audit/offre.htm">Version d'audit</a></li>
						</jstl:if>
					</ul>
				</div>
			</div>
		</nav>

		<jstl:choose>

			<jstl:when test="${Audit}">
				<jstl:choose>
					<jstl:when test="${!FormationOfConnectedContributor.isEmpty()}">
						<a href="visualisation/formation/offre.htm"> <jstl:out
								value="Version visiteur" />
						</a>
						</br>
						<jstl:out
							value="Vous êtes contributeurs des formations suivantes:" />

						<display:table name="FormationOfConnectedContributor"
							requestURI="visualisation/formation/audit/offre.htm"
							id="formation">
							<display:column title="Nom" sortable="true">

								<jstl:url var="objectlink"
									value="objectVisualisation/audit/details.htm?code=${FormationOfConnectedContributor.keySet().toArray()[formation_rowNum - 1]}" />
								<a href="${objectlink}"> <jstl:out value="${formation}"
										escapeXml="true" />
								</a>

							</display:column>

						</display:table>

					</jstl:when>
					<jstl:otherwise>
						<jstl:out value="Vous n'êtes contributeur d'aucune formation." />
					</jstl:otherwise>
				</jstl:choose>
				<display:table name="DiplomaTypeExist"
					requestURI="visualisation/formation/audit/offre.htm" id="field">
					<display:column title="Nom" sortable="true">
						<jstl:forEach items="${field}" var="s" varStatus="loop">
							<jstl:url var="fieldlink"
								value="visualisation/formation/audit/field.htm?diploma=${s} " />
							<a href="${fieldlink}"> <jstl:out value="${s}" />
							</a>
						</jstl:forEach>
					</display:column>
				</display:table>
			</jstl:when>
			<jstl:otherwise>
				<jstl:out
					value="Vous n'êtes pas connecté, merci de bien vouloir vous authentifier" />
				<a href="/Projet_SOF/auth/login.htm"><spring:message
						code="login" /></a>
			</jstl:otherwise>
		</jstl:choose>
	</tiles:putAttribute>
</tiles:insertDefinition>
