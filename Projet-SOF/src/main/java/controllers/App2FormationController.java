package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.FormationService;
import services.ObjectService;
import services.PersonService;
import domain.Formation;
import domain.Object;
import domain.Person;
import domain.User;

@Controller
@RequestMapping("/visualisation")
public class App2FormationController {

	// Constructors -----------------------------------------------------------

	public App2FormationController() {
		super();
	}
	@Autowired()
	User user;

	@Autowired
	FormationService formationService;

	@Autowired
	ObjectService objectService;
	@Autowired
	PersonService personService;
	
	/**
	 * Liste des formations en version visiteur
	 * @return 
	 */
//	@RequestMapping("formation/offre")
//	public ModelAndView allFormationVisitor() {
//		ModelAndView result;
//		Collection<Formation> formations = formationService.findAll();
//		Collection<Object> objetsVisible = new ArrayList<Object>();
//		
//		
//		for (Formation formation : formations) {
//			if(formation.isVisible()){
//				objetsVisible.add(objectService.findOne(formation.getCode()));
//			}
//		}
//		
//		formations.clear();
//		formations = null;
//
//		result = new ModelAndView("formation/offre");
//		result.addObject("FormationVisible", objetsVisible);
//
//		return result;
//	}
	
	
	@RequestMapping("formation/offre")
	public ModelAndView allFormationVisitor() {
		ModelAndView result;
		Collection<Formation> formations = formationService.findAll();
		Collection<Object> objetsVisible = new ArrayList<Object>();
		
		
		for (Formation formation : formations) {
			if(formation.isVisible()){
				objetsVisible.add(objectService.findOne(formation.getCode()));
			}
		}
		
		formations.clear();
		formations = null;

		getFormation_Field();
		
		result = new ModelAndView("formation/offre");
		result.addObject("FormationVisible", objetsVisible);

		return result;
	}
	
	
	
	private Collection <String> getFormation_Field(){
		SAXBuilder sxb = new SAXBuilder();
		Document document = null;
		try { 
		document = sxb.build(getClass().getResource("/configApp.xml"));
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		
		Element racine = document.getRootElement();
		
		List <String> f = getAllDesigner(racine);
		
		for(String e : f){
			System.out.println(e);
		}
		
		return null;
		
	}
	
	private List<String> getAllDesigner(Element racine) {
		List<Element> listDiploma = racine.getChild("diploma_type").getChildren(
				"name");

		Iterator<Element> i = listDiploma.iterator();
		List<String> ret = new ArrayList<String>();

		while (i.hasNext()) {
			Element courant = (Element) i.next();
			ret.add(courant.getValue());
		}
		return ret;
	}

	
	
	@ModelAttribute("user")
	public User newUser() {
		return user;
	}
	
}
