<%@ page language="Java" contentType="text/html; charset=UTF-8"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>


	<tiles:insertDefinition name="master.page">
	    <link href="styles/stylish-portfolio.css" rel="stylesheet">
		<tiles:putAttribute name="title">
		Vous etes ...
		</tiles:putAttribute>
		<tiles:putAttribute name="body">
			
		<section id="portfolio" class="portfolio">
        <div class="container">
            <div class="row">
                <div class="col-lg-8 col-lg-offset-2 text-center">
                    <hr class="small">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="portfolio-item">
                                 <a href="/Projet_SOF/auth/login.htm">Concepteur ou responsable
                                    <img class="img-portfolio img-responsive" src="images/casque.jpg">
                                </a>
                            </div>
                        </div>
                        <div class=col-md-7>
                            <div class="portfolio-item">
                                <a href="/Projet_SOF/visualisation/formation/offre.htm">Contributeur 
                                    <img class="img-portfolio img-responsive" src="images/contributeur.jpg">
                                </a>
                            </div>
                        </div>

             
                    </div>
                </div>
          
            </div>
        </div>
   
    </section>
		</tiles:putAttribute>
	</tiles:insertDefinition>

