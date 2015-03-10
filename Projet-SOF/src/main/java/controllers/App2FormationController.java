package controllers;

import java.util.ArrayList;
import java.util.Collection;

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

		result = new ModelAndView("formation/offre");
		result.addObject("FormationVisible", objetsVisible);

		return result;
	}
	
	@ModelAttribute("user")
	public User newUser() {
		return user;
	}
	
}
