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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	 * 
	 * @return
	 */
	@RequestMapping("formation/offre")
	public ModelAndView allFormationVisitor() {
		ModelAndView result;
		Collection<Formation> formations = formationService.findAll();
		List<String> diploma_type_exist = new ArrayList<String>();
		for (Formation f : formations) {
			if (!diploma_type_exist.contains(f.getDiplomeType()))
				diploma_type_exist.add(f.getDiplomeType());

		}
		Collection<Object> objetsVisible = new ArrayList<Object>();

		for (Formation formation : formations) {
			if (formation.isVisible()) {
				objetsVisible.add(objectService.findOne(formation.getCode()));
			}
		}

		formations.clear();
		formations = null;
		getFormation_Field();
		result = new ModelAndView("formation/offre");

		result.addObject("FormationVisible", objetsVisible);
		result.addObject("DiplomaTypeExist", diploma_type_exist);

		return result;
	}

	@RequestMapping(value = "/formation/field", method = RequestMethod.GET)
	public ModelAndView allFieldOfType(
			@RequestParam(value = "field", required = true) String nom) {
		System.out.println("-----------------------------");
		System.out.println(nom);
		System.out.println("-----------------------------");
		ModelAndView result;
		Collection<Formation> formations = formationService.findAll();
		List<String> formation_field_exist = new ArrayList<String>();
		System.out.println(formations.size());
		for (Formation f : formations) {

			if (f.getDiplomeType() != null)
				if (f.getDiplomeType().equals(nom)) {
					 if(!formation_field_exist.contains(f.getFormationField().replace(",", ";")))
					 {
						System.out.println(f.getFormationField());
						String newField=f.getFormationField().replace(",", ";");
						System.out.println(newField);
						formation_field_exist.add(newField);
						
					 }
					System.out.println("OK");
				}

		}

		result = new ModelAndView("formation/offreDomaine");

		result.addObject("FormationFieldExist", formation_field_exist);
		return result;
	}

	// inutile
	private Collection<String> getFormation_Field() {
		SAXBuilder sxb = new SAXBuilder();
		Document document = null;
		try {
			document = sxb.build(getClass().getResource("/configApp.xml"));
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}

		Element racine = document.getRootElement();

		List<String> f = getAll(racine, "diploma_type");

		List<String> g = getAll(racine, "formation_field");

		for (String e : f) {
			System.out.println(e);
		}
		for (String e : g) {
			System.out.println(e);
		}

		return null;

	}

	private List<String> getAll(Element racine, String branche) {
		List<Element> listDiploma = racine.getChild(branche)
				.getChildren("name");

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
