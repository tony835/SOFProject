<%@ page language="Java" contentType="text/html; charset=UTF-8"%>


<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>



<tiles:insertDefinition name="master.page">
	<tiles:putAttribute name="title">



	</tiles:putAttribute>
	<tiles:putAttribute name="body">

			 <jstl:if test="${!fn:contains(header.referer, 'objectVisualisation/details.htm')}">
			 <jstl:set var="entre" value="true"></jstl:set> 			 
			 </jstl:if> 
  
		<body>
		<head>
<link rel="stylesheet" href="styles/Base.css" type="text/css">
<link rel="stylesheet" href="styles/BreadCrumb.css" type="text/css">
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js">
	
</script>
<script src="scripts/jquery.easing.1.3.js" type="text/javascript"
	language="JavaScript">
	
</script>
<script src="scripts/jquery.jBreadCrumb.1.1.js" type="text/javascript"
	language="JavaScript">
	
</script>
<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery("#breadCrumb0").jBreadCrumb();
	})
</script>
		</head>

		<div id="container">
			<div class="breadCrumbHolder module">
				<div id="breadCrumb0" class="breadCrumb module" style="height: 33px">
					<ul>

							<li>
							<a	href="objectVisualisation/details.htm?code=${object.contexte.code}">${object.contexte.name}</a>
							</li>
						<jstl:if test="${entre!=true}">
						<jstl:forEach var="item" items="${navigation}">
						<jstl:if test="${!item.contains(object.code) && item!=null }">
						<li><a href="${item}">${item.substring(item.indexOf("=")+1)}</a></li>
						</jstl:if>
								</jstl:forEach>
								
						</jstl:if>

						<li>${object.code} - ${object.name}</li>
					</ul>
				</div>
			</div>
		</div>
		<br />
		<div class="bs-example">
			<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#sectionA">Information
						géneral</a></li>
				<jstl:forEach var="item" items="${maps.keySet()}">
					<li><a data-toggle="tab" href="#section${item}"> <jstl:out
								value="${item}"></jstl:out>
					</a></li>
				</jstl:forEach>
				<jstl:if test="${!object.allFils.isEmpty()}">
					<li><a data-toggle="tab" href="#listFils"> Structure </a></li>
				</jstl:if>

				<jstl:if test="${!objectMemeTypeSize}">
					<li><a data-toggle="tab" href="#objetMemeType"> Objet du
							meme type </a></li>
				</jstl:if>

			</ul>
			<div class="tab-content">
				<div id="sectionA" class="tab-pane fade in active">
					<br />
					<tag:showtext code="object.code" value="${object.code}"></tag:showtext>
					<tag:showtext code="object.name" value="${object.name}"></tag:showtext>
					<br />
					<jstl:forEach var="item" items="${fIListGeneral}">
						<jstl:choose>
							<jstl:when test="${!item.value.isEmpty()}">
								<jstl:out value="${item.field.name}:"></jstl:out>
								<jstl:out value="${item.value}"></jstl:out>
								<a href="#" class="btn btn-xs btn-success" data-toggle="modal"
									data-target="#modal${item.field.id}${object.code}"> edit </a>
								<br />
								<script>
									$(document)
											.ready(
													function() {
														$(
																"#saveButon${item.field.id}${object.code}")
																.click(
																		function() {
																			var value = document
																					.getElementById("inputValue${item.field.id}${object.code}").value;
																			$
																					.ajax({
																						url : 'modal/ajax.htm',
																						data : {
																							'codeObjet' : "${object.code}",
																							'idField' : "${item.field.id}",
																							'item.value' : value
																						},
																						success : function(
																								data) {
																							if (data.length != 0)
																								document
																										.getElementById("spanValue${item.field.id}${object.code}").innerHTML = data;
																							$(
																									"#modal${item.field.id}${object.code}")
																									.modal(
																											"hide");
																						},
																						error : function() {
																							alert("error");
																						}
																					});
																		});
													});
								</script>
								<div class="modal fade" id="modal${item.field.id}${object.code}"
									tabindex="-1" role="dialog" aria-labelledby="basicModal"
									aria-hidden="true">
									<div class="modal-dialog">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal"
													aria-hidden="true"></button>
											</div>
											<div class="modal-body">
												<input type="text" placeholder="Description"
													id="inputValue${item.field.id}${object.code}"
													value="${item.value}" />
											</div>
											<div class="modal-footer">
												<button type="button" class="btn btn-default"
													data-dismiss="modal">Close</button>
												<button type="button"
													id="saveButon${item.field.id}${object.code}"
													class="btn btn-primary save">Save changes</button>
											</div>
										</div>
									</div>
								</div>
							</jstl:when>
						</jstl:choose>

					</jstl:forEach>
				</div>

				<jstl:forEach var="item" items="${maps.keySet()}">
					<div id="section${item}" class="tab-pane fade">
						<jstl:forEach var="itemh" items="${maps.get(item)}">
							<br />

							<jstl:out value="${itemh.value}"></jstl:out>
						</jstl:forEach>

					</div>
				</jstl:forEach>


				<div id="listFils" class="tab-pane fade">

					<jstl:forEach var="fils" items="${object.allFils}">


						<a href="objectVisualisation/details.htm?code=${fils.fils.code}">
							<jstl:out value="${fils.fils.code}"></jstl:out>(<jstl:out
								value="${fils.fils.name}"></jstl:out>)
						</a>
						</br>
					</jstl:forEach>
				</div>



				<div id="objetMemeType" class="tab-pane fade">

					<jstl:forEach var="objet" items="${objectMemeType}">


						<a href="objectVisualisation/details.htm?code=${objet.code}">
							<jstl:out value="${objet.code}"></jstl:out>(<jstl:out
								value="${objet.name}"></jstl:out>)
						</a>
						</br>

					</jstl:forEach>
				</div>

			</div>

		</div>


		</body>

	</tiles:putAttribute>
</tiles:insertDefinition>


