<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%
	request.setCharacterEncoding("UTF-8");
%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>




<tiles:insertDefinition name="master.page">

	<tiles:putAttribute name="title">

		<jstl:out value="${object.code}-${object.name}" />

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
						<li><a href="visualisation/formation/offre.htm"><spring:message code="offre.list" /></a></li>
						<jstl:if test="${user.isConnected()}">
							<li><a href="visualisation/formation/audit/offre.htm"><spring:message code="visualisation.formation.audit" /></a></li>
						</jstl:if>
					</ul>
				</div>
			</div>
		</nav>
		<jstl:choose>
			<jstl:when test="${Audit==true}">
				<jstl:if
					test="${!fn:contains(header.referer, 'objectVisualisation/audit/details.htm')}">
					<jstl:set var="entre" value="true"></jstl:set>
				</jstl:if>
			</jstl:when>
			<jstl:otherwise>
				<jstl:if
					test="${!fn:contains(header.referer, 'objectVisualisation/details.htm')}">
					<jstl:set var="entre" value="true"></jstl:set>
				</jstl:if>
			</jstl:otherwise>

		</jstl:choose>







		<div id="container">
			<div class="breadCrumbHolder module">
				<div id="breadCrumb" class="breadCrumb module" style="height: 33px">
					<ul>

						<jstl:choose>
							<jstl:when test="${Audit==true}">
								<li><a
									href="objectVisualisation/audit/details.htm?code=${object.contexte.code}">${object.contexte.name}</a>
								</li>
							</jstl:when>
							<jstl:otherwise>
								<li><a
									href="objectVisualisation/details.htm?code=${object.contexte.code}">${object.contexte.name}</a>
								</li>
							</jstl:otherwise>

						</jstl:choose>

						<jstl:if test="${entre!=true}">

							<jstl:forEach var="item" items="${navigation}">
								<jstl:if test="${!item.contains(object.code)}">
									<li><a href="${item}">${item.substring(item.indexOf("=")+1)}</a></li>
								</jstl:if>

							</jstl:forEach>
						</jstl:if>

						<li>${object.code}-${object.name}</li>
					</ul>
				</div>
			</div>
		</div>

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
					<li><a data-toggle="tab" href="#listFils"> <spring:message code= "composants.structure"></spring:message> </a></li>
				</jstl:if>

				<jstl:if test="${!objectMemeTypeSize}">
					<li><a data-toggle="tab" href="#objetMemeType"><spring:message code= "visualisation.objet.same"/></a></li>
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

							<jstl:when test="${user.isConnected()}">
								<jstl:choose>

									<jstl:when test="${Audit==true}">

										<jstl:choose>
											<jstl:when test="${Contributor==true}">
												<a href="#" class="btn btn-xs btn-success"
													data-toggle="modal"
													data-target="#modal${item.field.id}${object.code}">
													edit </a>
											</jstl:when>
										</jstl:choose>

										<b>${item.field.name}:</b>
										<span id="spanValue${item.field.id}${object.code}">${item.value}</span>

										<br />
										<script charset="UTF-8">
										function d2h(d) {
										    return d.toString(16);
										}
										function h2d (h) {
										    return parseInt(h, 16);
										}
										function stringToHex (tmp) {
										    var str = '',
										        i = 0,
										        tmp_len = tmp.length,
										        c;
										    
										    for (; i < tmp_len; i += 1) {
										        c = tmp.charCodeAt(i);
										        str += d2h(c) + ' ';
										    }
										    return str;
										}
										function hexToString (tmp) {
										    var arr = tmp.split(' '),
										        str = '',
										        i = 0,
										        arr_len = arr.length,
										        c;
										    
										    for (; i < arr_len; i += 1) {
										        c = String.fromCharCode( h2d( arr[i] ) );
										        str += c;
										    }
										    
										    return str;
										}
										
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
																								contentType : "application/x-www-form-urlencoded; charset=UTF-8",
																								scriptCharset : "UTF-8",
																								data : {
																									'codeObjet' : "${object.code}",
																									'idField' : "${item.field.id}",
																									'value' : stringToHex(value)
																								},

																								success : function(
																										data) {
																									if (data.length != 0)
																										document
																												.getElementById("spanValue${item.field.id}${object.code}").innerHTML = value;
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
										<div class="modal fade"
											id="modal${item.field.id}${object.code}" tabindex="-1"
											role="dialog" aria-labelledby="basicModal" aria-hidden="true">
											<div class="modal-dialog">
												<div class="modal-content">
													<div class="modal-header">
														<button type="button" class="close" data-dismiss="modal"
															aria-hidden="true"></button>
													</div>
													<div class="modal-body">
														<jstl:if test="${item.field.getTypeContenu()=='STRING'}">
															<input type="text" placeholder="Description"
																id="inputValue${item.field.id}${object.code}"
																value="${item.value}" size=50 />
														</jstl:if>
														<jstl:if
															test="${item.field.getTypeContenu()=='STRUCTURE'}">
															<textarea placeholder="Description"
																id="inputValue${item.field.id}${object.code}"
																value="${item.value}" rows="30" cols="90">${item.value}</textarea>
														</jstl:if>
														<jstl:if test="${item.field.getTypeContenu()=='INT'}">
															<input type="number" placeholder="Description"
																id="inputValue${item.field.id}${object.code}"
																value="${item.value}" size=50 />
														</jstl:if>
													</div>
													<div class="modal-footer">
														<button type="button" class="btn btn-default"
															data-dismiss="modal"><spring:message code="visualisation.close"/></button>
														<button type="button"
															id="saveButon${item.field.id}${object.code}"
															class="btn btn-primary save" data-dismiss="modal"><spring:message code="visualisation.save"/></button>
													</div>
												</div>
											</div>
										</div>
									</jstl:when>
									<jstl:when test="${Audit==false}">
										<jstl:choose>
											<jstl:when test="${!item.value.isEmpty()}">
												<b>${item.field.name}:</b>
												<span id="spanValue${item.field.id}${object.code}">${item.value}</span>

												<br />
											</jstl:when>
											<jstl:otherwise>
												<b>${item.field.name}:</b>
												<span id="spanValue${item.field.id}${object.code}"><spring:message code="visualisation.information"/></span>
											</jstl:otherwise>

										</jstl:choose>


									</jstl:when>
								</jstl:choose>

							</jstl:when>
							<jstl:when test="${!user.isConnected()}">
								<jstl:choose>

									<jstl:when test="${Audit==false}">
										<jstl:choose>
											<jstl:when test="${!item.value.isEmpty()}">
												<b>${item.field.name}:</b>
												<span id="spanValue${item.field.id}${object.code}">${item.value}</span>

												<br />
											</jstl:when>
											<jstl:otherwise>
												<b>${item.field.name}:</b>
												<span id="spanValue${item.field.id}${object.code}"><spring:message code="visualisation.information"/></span>
											</jstl:otherwise>

										</jstl:choose>

									</jstl:when>
								</jstl:choose>
							</jstl:when>
						</jstl:choose>

					</jstl:forEach>
					<jstl:choose>
						<jstl:when test="${!user.isConnected()}">
							<jstl:choose>
								<jstl:when test="${Audit==true}">
									<jstl:out
										value="Vous n'êtes pas connecté, merci de bien vouloir vous authentifier pour accéder à cette version d'audit." />
									<a href="/Projet_SOF/auth/login.htm"><spring:message
											code="login" /></a>

								</jstl:when>

							</jstl:choose>
						</jstl:when>
					</jstl:choose>


				</div>

				<jstl:forEach var="item" items="${maps.keySet()}">
					<div id="section${item}" class="tab-pane fade">
						<jstl:forEach var="itemh" items="${maps.get(item)}">
							<br />
							<jstl:choose>

								<jstl:when test="${user.isConnected()}">
									<jstl:choose>

										<jstl:when test="${Audit==true}">

											<jstl:choose>
												<jstl:when test="${Contributor==true}">
													<a href="#" class="btn btn-xs btn-success"
														data-toggle="modal"
														data-target="#modal${itemh.field.id}${object.code}">
														edit </a>
												</jstl:when>
											</jstl:choose>

											<b>${itemh.field.name}:</b>
											<span id="spanValue${itemh.field.id}${object.code}">${itemh.value}</span>

											<br />
											<script>
											function d2h(d) {
											    return d.toString(16);
											}
											function h2d (h) {
											    return parseInt(h, 16);
											}
											function stringToHex (tmp) {
											    var str = '',
											        i = 0,
											        tmp_len = tmp.length,
											        c;
											    
											    for (; i < tmp_len; i += 1) {
											        c = tmp.charCodeAt(i);
											        str += d2h(c) + ' ';
											    }
											    return str;
											}
											function hexToString (tmp) {
											    var arr = tmp.split(' '),
											        str = '',
											        i = 0,
											        arr_len = arr.length,
											        c;
											    
											    for (; i < arr_len; i += 1) {
											        c = String.fromCharCode( h2d( arr[i] ) );
											        str += c;
											    }
											    
											    return str;
											}
												$(document)
														.ready(
																function() {
																	$(
																			"#saveButon${itemh.field.id}${object.code}")
																			.click(
																					function() {
																						var value = document
																								.getElementById("inputValue${itemh.field.id}${object.code}").value;
																						$
																								.ajax({
																									url : 'modal/ajax.htm',
																									contentType : "charset=utf-8",
																									data : {
																										'codeObjet' : "${object.code}",
																										'idField' : "${itemh.field.id}",
																										'value' : stringToHex(value)
																									},

																									success : function(
																											data) {
																										if (data.length != 0)
																											document
																													.getElementById("spanValue${itemh.field.id}${object.code}").innerHTML = value;
																										$(
																												"#modal${itemh.field.id}${object.code}")
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
											<div class="modal fade"
												id="modal${itemh.field.id}${object.code}" tabindex="-1"
												role="dialog" aria-labelledby="basicModal"
												aria-hidden="true">
												<div class="modal-dialog">
													<div class="modal-content">
														<div class="modal-header">
															<button type="button" class="close" data-dismiss="modal"
																aria-hidden="true"></button>
														</div>
														<div class="modal-body">
															<jstl:if test="${itemh.field.getTypeContenu()=='STRING'}">
																<input type="text" placeholder="Description"
																	id="inputValue${itemh.field.id}${object.code}"
																	value="${itemh.value}" size=50 />
															</jstl:if>
															<jstl:if
																test="${itemh.field.getTypeContenu()=='STRUCTURE'}">
																<textarea placeholder="Description"
																	id="inputValue${itemh.field.id}${object.code}"
																	value="${itemh.value}" rows="30" cols="90">${itemh.value}</textarea>
															</jstl:if>
															<jstl:if test="${itemh.field.getTypeContenu()=='INT'}">
																<input type="number" placeholder="Description"
																	id="inputValue${itemh.field.id}${object.code}"
																	value="${itemh.value}" size=50 />
															</jstl:if>
														</div>
														<div class="modal-footer">
															<button type="button" class="btn btn-default"
																data-dismiss="modal"><spring:message code="visualisation.close"/></button>
															<button type="button"
																id="saveButon${itemh.field.id}${object.code}"
																class="btn btn-primary save" data-dismiss="modal"><spring:message code="visualisation.save"/></button>
														</div>
													</div>
												</div>
											</div>
										</jstl:when>
										<jstl:when test="${Audit==false}">

											<jstl:choose>
												<jstl:when test="${!itemh.value.isEmpty()}">
													<b>${itemh.field.name}:</b>
													<span id="spanValue${itemh.field.id}${object.code}">${itemh.value}</span>

													<br />
												</jstl:when>
												<jstl:otherwise>
													<b>${itemh.field.name}:</b>
													<span id="spanValue${itemh.field.id}${object.code}"><spring:message code="visualisation.information"/></span>
												</jstl:otherwise>

											</jstl:choose>

										</jstl:when>
									</jstl:choose>
								</jstl:when>
								<jstl:when test="${!user.isConnected()}">
									<jstl:choose>
										<jstl:when test="${Audit==false}">
											<jstl:choose>
												<jstl:when test="${!itemh.value.isEmpty()}">
													<b>${itemh.field.name}:</b>
													<span id="spanValue${itemh.field.id}${object.code}">${itemh.value}</span>

													<br />
												</jstl:when>
												<jstl:otherwise>
													<b>${itemh.field.name}:</b>
													<span id="spanValue${itemh.field.id}${object.code}"><spring:message code="visualisation.information"/></span>
												</jstl:otherwise>

											</jstl:choose>

										</jstl:when>

									</jstl:choose>
								</jstl:when>
							</jstl:choose>

						</jstl:forEach>


					</div>
				</jstl:forEach>

				<jstl:choose>
					<jstl:when test="${Audit==true}">

						<jstl:choose>
							<jstl:when test="${user.isConnected()}">
								<div id="listFils" class="tab-pane fade">
								<ul>
									<jstl:forEach var="fils" items="${object.allFils}">
										<li><a
											href="objectVisualisation/audit/details.htm?code=${fils.fils.code}">
											<jstl:out value="${fils.fils.code}"/>: <jstl:out
												value="${fils.fils.name}"/>:
										</a>
										</li>
									</jstl:forEach>
									</ul>
								</div>
							</jstl:when>
						</jstl:choose>
					</jstl:when>
					<jstl:otherwise>
						<div id="listFils" class="tab-pane fade">
						<ul>
							<jstl:forEach var="fils" items="${object.allFils}">
								<li><a href="objectVisualisation/details.htm?code=${fils.fils.code}">
									<jstl:out value="${fils.fils.code}"/>: <jstl:out
										value="${fils.fils.name}"/>
								</a></li>
								
							</jstl:forEach>
							</ul>
						</div>
					</jstl:otherwise>
				</jstl:choose>




				<div id="objetMemeType" class="tab-pane fade">
<ul>
					<jstl:forEach var="objet" items="${objectMemeType}">


						<li><a href="objectVisualisation/details.htm?code=${objet.code}">
							<jstl:out value="${objet.code}"/>: <jstl:out
								value="${objet.name}"/>
						</a>
						</li>

					</jstl:forEach>
					</ul>
				</div>

			</div>

		</div>


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
		jQuery(document).ready(function()
				{
					jQuery("#breadCrumb").jBreadCrumb({easing:'swing'});
				})

		</script>


	</tiles:putAttribute>
</tiles:insertDefinition>


