package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
import domain.Person;
import domain.User;
 
@Controller
@RequestMapping("/formation")
public class FormationController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public FormationController() {
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
	 * Liste touts les formations.
	 * 
	 * @return La vue qui mène au jsp traitant cet action
	 */
	@RequestMapping("/list")
	public ModelAndView allFormation() {
		ModelAndView result;
		Collection<Formation> formations = formationService.findAll();

		result = new ModelAndView("formation/list");
		result.addObject("formations", formations);

		return result;
	}

	/**
	 * Edition d'une formation
	 * 
	 * @param formationId
	 *            l'id de la formation que nous souhaitons modifier
	 * @return La vue qui mène au jsp traitant cet action
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required=false) String code) {
		ModelAndView result;
		Formation formation = formationService.create();
		if(code!=null){
			 formation = formationService.findOne(code);
		}
		result = new ModelAndView("formation/edit");
		result.addObject("formation", formation);

		return result;
	}

	/**
	 * Sauvegarde d'une formation
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(@Valid @ModelAttribute Formation formation, BindingResult bindingResult) {
		System.out.println("passe");
		System.out.println("-----------");

		ModelAndView result;
		if (bindingResult.hasErrors()) {
			result = new ModelAndView("formation/edit");
			result.addObject("formation", formation);
		} else {
			try {
				boolean responsableSave = false;

				// Erreur : la clé ne peut être vide
				if (formation.getCode().length() == 0) { // error
					result = new ModelAndView("formation/edit");
					result.addObject("formation", formation);
				}

				// On regarde si un responsable est rempli et aussi s'il existe pour pouvoir être associé.
				if (formation.getResponsable() == null || formation.getResponsable().getLogin() == null) {
					System.out.println("erreur");
					result = new ModelAndView("formation/edit");
					result.addObject("formation", formation);
					return result;
				}
				
				Person p = personService.findOne(formation.getResponsable().getLogin());
				if (p != null) {
					responsableSave = true;
					formation.setResponsable(p);
				}else{
					result = new ModelAndView("formation/edit");
					result.addObject("formation", formation);
					return result;
				}
				// Si un responsable n'est pas associé on défait le responsable créé par jsp.
				if (!responsableSave)
					formation.setResponsable(null);

				formationService.save(formation);
				result = new ModelAndView("redirect:list.htm");

			} catch (Throwable oops) {
				oops.printStackTrace();
				result = new ModelAndView("formation/list");
				result.addObject("message", "commit.formation.error");
			}
		}
		return result;
	}
	
	@RequestMapping("/test")
	public ModelAndView test() {
		ModelAndView result;
		Collection<Formation> formations = formationService.findAll();
		
		/*domain.Object o = objectService.create();
		o.setCode("Rendu_ProcheYes");
		objectService.save(o);

		//formationService.findOne("FORM1");
		objectService.addLinkFils(objectService.findOne("Rendu_Proche"), o, 0);
		*/
		formationService.getListFormationIndente("FORM1");
		
		result = new ModelAndView("formation/list");
		result.addObject("formations", formations);

		
		return result;
	}
	
	
	
	@ModelAttribute("user")
	public User newUser() {
		return user;
	}
}