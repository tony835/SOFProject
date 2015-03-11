<jsp:root version="2.0" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
	xmlns:jstl="http://java.sun.com/jsp/jstl/core" xmlns:spring="http://www.springframework.org/tags"
	xmlns:form="http://www.springframework.org/tags/form" xmlns:security="http://www.springframework.org/security/tags"
	xmlns:display="http://displaytag.sf.net" xmlns:tiles="http://tiles.apache.org/tags-tiles"
	xmlns:tag="urn:jsptagdir:/WEB-INF/tags" xmlns="http://www.w3.org/1999/xhtml">
	<jsp:output omit-xml-declaration="false" doctype-root-element="html" doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
		doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" />
	<jsp:directive.page contentType="text/html" />
	
	
	<tiles:insertDefinition name="master.page">
		<tiles:putAttribute name="title">
			<spring:message code="title.formation.list" />
		</tiles:putAttribute>
		
		<tiles:putAttribute name="body">
			<display:table name="fields" pagesize="20" class="displaytag" id="row" requestURI="formation/list.htm">
				<display:column property="field.id" title="ID champs" />
    			<display:column property="field.name" title="Nom champs" />
    			<display:column property="field.typeContenu" title="Type" nulls="true"/>
    			<display:column title="Valeur" nulls="true"> 
    				<span id="spanValue${row.field.id}${row.object.code}">
    					${row.value}
    				</span>
    			</display:column>
    			<display:column title="edit" nulls="true">
    			    <a href="#" class="btn btn-xs btn-success" data-toggle="modal" data-target="#modal${row.field.id}${row.object.code}">
    			    	edit
    			    </a>
    			    <script>
			    		$(document).ready(function() {
			       	 		$("#saveButon${row.field.id}${row.object.code}").click(function() {
			       	 			var value = document.getElementById("inputValue${row.field.id}${row.object.code}").value ;
			        	        $.ajax({
			        	            url : 'modal/ajax.htm',
			        	            data: {'codeObjet':"${row.object.code}",'idField':"${row.field.id}",'value':value},
			        	            success : function(data) {
			        	            	document.getElementById("spanValue${row.field.id}${row.object.code}").innerHTML = data ;
			        	            	$("#modal${row.field.id}${row.object.code}").modal("hide");
			        	            },
			        	            error: function(){
			        	            	alert("error") ;
			        	    		}
			        	        });
			        		});
			    		});
		    		</script>
    				<div class="modal fade" id="modal${row.field.id}${row.object.code}" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
					  <div class="modal-dialog">
					    <div class="modal-content">
					      <div class="modal-header">
					        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
					      </div>
					      <div class="modal-body">
					        <input type="text" placeholder="Description" id="inputValue${row.field.id}${row.object.code}" value="${row.value}"/>
					      </div>
					      <div class="modal-footer">
					        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					        <button type="button" id="saveButon${row.field.id}${row.object.code}" class="btn btn-primary save">Save changes</button>
					      </div>
					    </div>
					  </div>
					</div>
				</display:column>
  			</display:table>
		</tiles:putAttribute>
	</tiles:insertDefinition>
</jsp:root>