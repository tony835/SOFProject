<jsp:root version="2.0" xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
	xmlns:jstl="http://java.sun.com/jsp/jstl/core"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:form="http://www.springframework.org/tags/form"
	xmlns:security="http://www.springframework.org/security/tags"
	xmlns:display="http://displaytag.sf.net"
	xmlns:tiles="http://tiles.apache.org/tags-tiles"
	xmlns:tag="urn:jsptagdir:/WEB-INF/tags"
	xmlns="http://www.w3.org/1999/xhtml">

	<jsp:output omit-xml-declaration="false" doctype-root-element="html"
		doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
		doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" />


	<jsp:directive.page contentType="text/html" />

	<tiles:insertDefinition name="master.page">
		<tiles:putAttribute name="title">
				<spring:message code="title.arbre.editchildren" /> ${param.cobject}: ${objEnCours.name}
		</tiles:putAttribute>
		
		<tiles:putAttribute name="body">
			<jstl:if test="${!empty descError}">
				<p style="color: Red">${descError}</p>
			</jstl:if>
						<jstl:if test="${!listFils.isEmpty()}">
			
			
<display:table name="listFils" pagesize="20" class="displaytag"
				id="row" requestURI="arbreFormation/gestionFils.htm">
				<display:column title="Code">${row.getValue1().getFils().getCode()}</display:column>
				<display:column title="Name">${row.getValue1().getFils().getName()}</display:column>

				<display:column title="Rang">
					<form action="arbreFormation/gestionFilsEditRang.htm" method="post">
						<input hidden="hidden" readonly="false" name="codeEnCours" value="${objEnCours.getCode()}"></input> <input
							hidden="hidden" readonly="false" name="cobject" value="${row.getValue1().getFils().getCode()}"> 
							</input> 
							<input type="number" name="rang"  value="${row.getValue1().getRang()}" style="width: 3em"> </input>
						<button type="submit" name="Valider">Valider</button>
					</form>
				</display:column>
				<display:column title="Action">
					<jstl:if test="${row.getValue0()}">
								<a class="btn btn-default btn-xs"
									href="arbreFormation/supprimer.htm?pere=${objEnCours.getCode()}&amp;fils=${row.getValue1().getFils().getCode()}"><spring:message
										code="arbreFormation.supprimer" /></a>
					</jstl:if>
				</display:column>
			</display:table>
	</jstl:if>

	    
	    <fieldset>

					<legend>
                     Choisisez le type et  ajoutez un fils
					</legend>
			<form:form
				action="arbreFormation/sortNLObject.htm?cobject=${objEnCours.getCode()}"
				method="post" modelAttribute="typeobject">
				<div class="form-group">
					<form:label class="col-sm-2 control-label" path="">
						Tous les types
					</form:label>
					<div class="col-sm-6">
						<form:select class="form-control" name="code" path="code"
							multiple="false" onchange="this.form.submit()">
							<form:option value="" label="Tous les types" />
							<form:options items="${typesList}" itemLabel="name"
								itemValue="code" />
						</form:select>
					</div>

				</div>
			</form:form>
			<br />
	



			<form:form
				action="arbreFormation/addFils.htm?cobject=${objEnCours.getCode()}&amp;typeobject=${param.typeobject}"
				method="post" modelAttribute="selectedFils">
				<div class="form-group">
					<form:label class="col-sm-2 control-label" path="">
						Liste des objets non lies
					</form:label>
					<div class="col-sm-6">
						<form:select class="form-control" name="code" path="code"
							multiple="false" required="true">
							<form:option value="" label="Liste des objets non lies" />
							<form:options items="${NonLinkedObjectList}"
								itemLabel="catCodeName" itemValue="code" />
						</form:select>
							</div>
						<div class="col-sm-2">
						<input type="number" name="rang" value="1" style="width: 3em"> </input>
						<button type="submit" name="save" class="btn btn-default btn-sm">
							<spring:message code="save" />
						</button>
							</div>
				
				</div>
			</form:form>

			<br />
	
			<form:form
				action="arbreFormation/addFils.htm?cobject=${objEnCours.getCode()}&amp;typeobject=${param.typeobject}"
				method="post" modelAttribute="selectedFils">
				<div class="form-group">
					<form:label class="col-sm-2 control-label" path="">
						Liste des objets mutualises
					</form:label>
					<div class="col-sm-6">
						<form:select class="form-control" name="code" path="code"
							multiple="false" required="true">
							<form:option value="" label="Liste des objets mutualises" />
							<form:options items="${mutualisableObjectList}"
								itemLabel="catCodeName" itemValue="code" />
						</form:select>
							</div>
						<div class="col-sm-2">
						<input width="1%" type="number" name="rang" value="1" style="width: 3em"> </input>
						<button type="submit" name="save" class="btn btn-default btn-sm">
							<spring:message code="save" />
						</button>
						</div>
						
				
				</div>
			</form:form>
			
			</fieldset>
			
				<a class="btn btn-default btn-sm"
				href="arbreFormation/list.htm?code=${objEnCours.getContexte().getCode()}#${objEnCours.getCode()}"><spring:message
					code="arbreFormation.lister" /></a>
					


			<div class="center">
		
					
				<jstl:if test="${!empty error}">
					<p style="color: Red">
						<spring:message code="${error}" />
					</p>
				</jstl:if>
			</div>
		</tiles:putAttribute>
	</tiles:insertDefinition>

</jsp:root>
