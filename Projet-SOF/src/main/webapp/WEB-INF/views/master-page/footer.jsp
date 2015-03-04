<jsp:root version="2.0" xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
	xmlns="http://www.w3.org/1999/xhtml">


<jsp:useBean id="date" class="java.util.Date" />

<hr />
<div class="footerC">
	<b>Copyright  <fmt:formatDate value="${date}" pattern="yyyy" />
		Projet SOF : Maria - Quentin - Antony - Seddik - Salahedine - Alexandre
	</b>
</div>
<script type="text/javascript" src="scripts/commun.js">h</script>

</jsp:root>