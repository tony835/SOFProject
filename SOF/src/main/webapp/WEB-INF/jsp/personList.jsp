<%@ include file="/WEB-INF/jsp/include.jsp"%>


<html>
<body>
	<h1>Persons</h1>


	<ul>
		<c:forEach items="${persons}" var="m">
			<li>${m.login}: ${m.name}</li>
		</c:forEach>
	</ul>

	<h1>Objects</h1>
	<ul>
		<c:forEach items="${objects}" var="m">
			<li>${m.code}: ${m.name}</li>
		</c:forEach>
	</ul>

	<h1>formations</h1>
	<ul>
		<c:forEach items="${formations}" var="m">
			<li>${m.name}: ${m.numError}</li>
		</c:forEach>
	</ul>
	<h1>typeObjects</h1>
	<ul>
		<c:forEach items="${typeObjects}" var="m">
			<li>${m.name}: ${m.code}</li>
		</c:forEach>
	</ul>
	<h1>fields</h1>
	<ul>
		<c:forEach items="${fields}" var="m">
			<li>${m.id}: ${m.name}</li>
		</c:forEach>
	</ul>


</body>
</html>