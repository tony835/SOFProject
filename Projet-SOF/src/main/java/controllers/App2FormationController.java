package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	 * Liste des types de diplomes distinct de l'offre de formation
	 * 
	 * @return
	 */
	@RequestMapping("formation/offre")
	public ModelAndView allFormationVisitor() {

		
		ModelAndView result;
		Collection<String> diploma_type_exist = new ArrayList<String>();
		diploma_type_exist = formationService.findAllDistinctDiplomeVisitor();


		result = new ModelAndView("formation/offre");
		result.addObject("DiplomaTypeExist", diploma_type_exist);

		return result;
	}

	/**
	 * 
	 * @param diploma
	 * @return Map<String, String>
	 */
	@RequestMapping(value = { "/formation/field" }, method = RequestMethod.GET)
	public ModelAndView allFieldOfType(
			@RequestParam(value = "diploma", required = true) String diploma) {
		ModelAndView result;
		Map<String, String> formation_field_exist = new HashMap<String, String>();

		Collection<String> field = formationService
				.findbyDomaineByDiplomeVisitor(diploma);
		for (String f : field) {
			formation_field_exist.put(asciiToHex(f), f);
		}

		result = new ModelAndView("formation/offreDomaine");

		result.addObject("Diploma", diploma);
		result.addObject("FormationFieldExist", formation_field_exist);
		return result;
	}

	/**
	 * 
	 * @param diploma
	 * @param field
	 * @return
	 */
	@RequestMapping(value = "/formation/listformation"
			 , method = RequestMethod.GET)
	public ModelAndView allFormOfFieldAndDiploma(
			@RequestParam(value = "diploma", required = true) String diploma,
			@RequestParam(value = "field", required = true) String field) {
		ModelAndView result;
		Boolean audit = false;

		Collection<Formation> formation_diploma_and_field = new ArrayList<Formation>();

		String fieldDecode = decode(field);
		formation_diploma_and_field = formationService
				.findbyDomaineByDiplomeAndByTypeVisitor(diploma, fieldDecode);
		
		result = new ModelAndView("formation/FormationDiplomaField");
		
		result.addObject("Audit", audit);
		result.addObject("FormationOfDiplomaAndField",
				formation_diploma_and_field);
		return result;
	}

	// ------------------------------------ Contributor audit
	// --------------------------------------------

	@RequestMapping("formation/audit/offre")
	public ModelAndView allFormationContributor() {

		ModelAndView result;
		Collection<Formation> formation_of_contributor = new ArrayList<Formation>();
		Map<String, String> formation_identification = new HashMap<String, String>();
		Boolean audit = true;
		if(!user.getLogin().isEmpty())
		{
			formation_of_contributor = formationService.formationWithContributeur(user.getLogin());
			for (Formation f : formation_of_contributor)
			{
				formation_identification.put(f.getCode(), f.getName());
			}
		}
			
		Collection<String> diploma_type_exist = new ArrayList<String>();
		diploma_type_exist = formationService.findAllDistinctDiplome();

		result = new ModelAndView("formation/offreAudit");

		result.addObject("DiplomaTypeExist", diploma_type_exist);
		result.addObject("Audit", audit);
		result.addObject("FormationOfConnectedContributor", formation_identification);

		return result;
	}

	/**
	 * 
	 * @param diploma
	 * @return Map<String, String>
	 */
	@RequestMapping(value = "/formation/audit/field", method = RequestMethod.GET)
	public ModelAndView allFieldOfTypeContributor(
			@RequestParam(value = "diploma", required = true) String diploma) {
		ModelAndView result;
		Map<String, String> formation_field_exist = new HashMap<String, String>();

		Collection<String> field = formationService
				.findbyDomaineByDiplome(diploma);
		for (String f : field) {
			formation_field_exist.put(asciiToHex(f), f);
		}

		// a modifier utiliser la meme page que le visiteur mais juste
		// passer une variable disant que l'on est en version d'audit et
		// connecté au moins pas de page doublon
		result = new ModelAndView("formation/offreDomaineAudit");

		result.addObject("Diploma", diploma);
		result.addObject("FormationFieldExist", formation_field_exist);
		return result;
	}

	@RequestMapping(value = "/formation/audit/listformation", method = RequestMethod.GET)
	public ModelAndView allFormOfFieldAndDiplomaContributor(
			@RequestParam(value = "diploma", required = true) String diploma,
			@RequestParam(value = "field", required = true) String field) {
		ModelAndView result;
		Boolean audit = true;

		Collection<Formation> formation_diploma_and_field = new ArrayList<Formation>();

		String fieldDecode = decode(field);
		formation_diploma_and_field = formationService
				.findbyDomaineByDiplomeAndByType(diploma, fieldDecode);
		
		result = new ModelAndView("formation/FormationDiplomaFieldAudit");
		result.addObject("Audit",audit);
		result.addObject("FormationOfDiplomaAndField",
				formation_diploma_and_field);
		return result;
	}


//	/**
//	 * 
//	 * @return
//	 */
//	private Collection<String> getFormation_Field() {
//		SAXBuilder sxb = new SAXBuilder();
//		Document document = null;
//		try {
//			document = sxb.build(getClass().getResource("/configApp.xml"));
//		} catch (JDOMException | IOException e) {
//			e.printStackTrace();
//		}
//
//		Element racine = document.getRootElement();
//
//		List<String> f = getAll(racine, "diploma_type");
//
//		List<String> g = getAll(racine, "formation_field");
//
//		for (String e : f) {
//			System.out.println(e);
//		}
//		for (String e : g) {
//			System.out.println(e);
//		}
//
//		return null;
//
//	}

//	/**
//	 * 
//	 * @param racine
//	 * @param branche
//	 * @return
//	 */
//	private List<String> getAll(Element racine, String branche) {
//		List<Element> listDiploma = racine.getChild(branche)
//				.getChildren("name");
//
//		Iterator<Element> i = listDiploma.iterator();
//		List<String> ret = new ArrayList<String>();
//
//		while (i.hasNext()) {
//			Element courant = (Element) i.next();
//			ret.add(courant.getValue());
//		}
//		return ret;
//	}

	/**
	 * 
	 * @param ascii
	 * @return 
	 */
	public static String asciiToHex(String ascii) {
		StringBuilder hex = new StringBuilder();

		for (int i = 0; i < ascii.length(); i++) {
			hex.append(Integer.toHexString(ascii.charAt(i)));
		}
		return hex.toString();
	}

	/**
	 * 
	 * @param hexString
	 * @return
	 */
	public static String decode(final String hexString) {
		final int len = hexString.length();
		if (len % 2 != 0) {
			throw new RuntimeException("bad length");
		}
		final StringBuilder sb = new StringBuilder(len / 2);
		for (int i = 0; i < len; i += 2) {
			final String code = hexString.substring(i, i + 2);
			sb.append((char) Integer.parseInt(code, 16));
		}
		return sb.toString();
	}

	/**
	 * 
	 * @return
	 */
	@ModelAttribute("user")
	public User newUser() {
		return user;
	}

}
