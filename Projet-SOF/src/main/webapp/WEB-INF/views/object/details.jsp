<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>



<tiles:insertDefinition name="master.page">
	<tiles:putAttribute name="title">



	</tiles:putAttribute>
	<tiles:putAttribute name="body">
	
	
	
	<body>
<div class="bs-example">
    <ul class="nav nav-tabs">
        <li class="active">
        <a data-toggle="tab" href="#sectionA">Information géneral</a></li>
    <jstl:forEach var="item" items="${maps.keySet()}">
            <li><a data-toggle="tab" href="#section${item}"> <jstl:out value="${item}"></jstl:out> </a></li>
    </jstl:forEach>
          <li><a data-toggle="tab" href="#listFils"> Liste des fils </a></li>
    

    </ul>
    <div class="tab-content">
        <div id="sectionA" class="tab-pane fade in active">
        <br/>      <br/>
            <tag:showtext code="object.code" value="${object.code}"></tag:showtext>	
	        <tag:showtext code="object.name" value="${object.name}"></tag:showtext>
	      <jstl:forEach var="item" items="${fIListGeneral}">
    <br/>
           <jstl:out value="${item.value}"></jstl:out>
          </jstl:forEach>
        </div>
        
     <jstl:forEach var="item" items="${maps.keySet()}">
     <div id="section${item}" class="tab-pane fade">
          <jstl:forEach var="itemh" items="${maps.get(item)}">
    <br/>
            <jstl:out value="${itemh.value}"></jstl:out> 
          </jstl:forEach>
            
        </div>   
       </jstl:forEach>

   
    <div id="listFils" class="tab-pane fade">
  
	      <jstl:forEach var="fils" items="${object.allFils}">
 
          
           			<a  href="objectVisualisation/details.htm?code=${fils.fils.code}"> <jstl:out value="${fils.fils.name}"></jstl:out></a>
          </jstl:forEach>
        </div>
        
  </div>
</div>
</body>
	
	</tiles:putAttribute>
</tiles:insertDefinition>


