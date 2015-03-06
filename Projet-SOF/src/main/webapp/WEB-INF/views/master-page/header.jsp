<jsp:root version="2.0" xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:form="http://www.springframework.org/tags/form"
	xmlns:display="http://displaytag.sf.net"
	xmlns:tiles="http://tiles.apache.org/tags-tiles"
	xmlns:tag="urn:jsptagdir:/WEB-INF/tags"
	xmlns="http://www.w3.org/1999/xhtml">





	<div>
		<div class="logo">
			<img src="images/logo.png" alt="Gestion Annuaire Co., Inc." />
			<div class="internationalisation">
				<a href="?language=en"><img src="images/gb.png" alt="English" /></a>
				| <a href="?language=fr_FR"><img src="images/fr.png"
					alt="Francais" /> </a> |
					<c:choose>
				<c:when test="${user.isConnected()}">
					<a href="/Projet_SOF/auth/logout.htm">Logout</a></c:when>
					<c:otherwise>
						<a href="/Projet_SOF/auth/login.htm">Login</a>
					</c:otherwise>
				</c:choose>



			</div>
		</div>


		<div class="baseMenu">
			<div id="menu"></div>
		</div>

	</div>
</jsp:root>