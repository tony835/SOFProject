<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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

		<jstl:choose>
			<jstl:when test="${user.isConnected()}">
				<jstl:choose>
					<jstl:when test="${!FormationOfConnectedContributor.isEmpty()}">
						<a href="visualisation/formation/offre.htm"> <jstl:out
								value="Version visiteur" />
						</a>
						</br>
						<jstl:out
							value="Vous �tes contributeurs des formations suivantes:" />

						<display:table name="FormationOfConnectedContributor"
							requestURI="visualisation/formation/audit/offre.htm"
							id="formation">
							<display:column title="Nom" sortable="true">

								<jstl:url var="objectlink"
									value="objectVisualisation/details.htm?code=${FormationOfConnectedContributor.keySet().toArray()[formation_rowNum - 1]}" />
								<a href="${objectlink}"> <jstl:out value="${formation}"
										escapeXml="true" />
								</a>

							</display:column>

						</display:table>

					</jstl:when>
					<jstl:otherwise>
						<jstl:out value="Vous n'�tes contributeur d'aucune formation." />
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
					value="Vous n'�tes pas connect�, merci de bien vouloir vous authentifier" />
				<a href="/Projet_SOF/auth/login.htm"><spring:message
						code="login" /></a>
			</jstl:otherwise>
		</jstl:choose>
	</tiles:putAttribute>
</tiles:insertDefinition>
