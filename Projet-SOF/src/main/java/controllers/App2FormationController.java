package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
import domain.User;

@Controller
@RequestMapping("/visualisation")
public class App2FormationController {

	// Constructors -----------------------------------------------------------

	/**
	 * Constructeur par defaut
	 */
	public App2FormationController() {
		super();
	}

	@Autowired
	User user;

	@Autowired
	FormationService formationService;

	@Autowired
	ObjectService objectService;
	@Autowired
	PersonService personService;

	/**
	 * Liste des types de diplomes distinct de l'offre de formation
	 * @return Un ModelAndView contenant les diplomes distincts
	 * @version 2.1
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
	 * Map les champs des types par diplome.
	 * @param diploma
	 * @return ModelAndView contenant l'offre par domaine de formation 
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
	 * Tous les champs des formations et des diplomes.
	 * @param diploma
	 * @param field
	 * @return ModelAndView 
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

	
	/**
	 * Permet de récupérer tous les formations auxquelles un contributeur contribue.
	 * @return ModelAndView
	 */
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
	 * Permet de recuperer tous les types de champs d'une formation pour la contribution.
	 * @param diploma Un diplome
	 * @return ModelAndView
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
		// connectï¿½ au moins pas de page doublon
		result = new ModelAndView("formation/offreDomaineAudit");

		result.addObject("Diploma", diploma);
		result.addObject("FormationFieldExist", formation_field_exist);
		return result;
	}

	
	/**
	 * Permet de recuperer un formulaire d'un champs et d'un diplome pour la contribution.
	 * @param diploma Un diplome
	 * @param field Un champ
	 * @return ModelAndView
	 */
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

	/**
	 * Permet d'encoder une chaine de caractere.
	 * @param ascii
	 * @return String  Chaine de caractere encodée
	 */
	public static String asciiToHex(String ascii) {
		StringBuilder hex = new StringBuilder();

		for (int i = 0; i < ascii.length(); i++) {
			hex.append(Integer.toHexString(ascii.charAt(i)));
		}
		return hex.toString();
	}

	/**
	 * Permet de décoder une chaine de caractere.
	 * @param hexString
	 * @return String  Chaine de caractere décodée
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
	 * Permet de recuperer l'utilisateur courant.
	 * @return User l'utilisateur courant.
	 */
	@ModelAttribute("user")
	public User newUser() {
		return user;
	}

}
