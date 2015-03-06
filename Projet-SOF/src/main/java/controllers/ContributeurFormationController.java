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

import services.PersonService;
import domain.Formation;

import domain.Person;

@Controller
@RequestMapping("/formation/contributeur")
public class ContributeurFormationController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public ContributeurFormationController() {
		super();
	}

	@Autowired
	FormationService formationService;

	@Autowired
	PersonService personService;



	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam String code) {
		ModelAndView result;
		Collection<Person> contibuteurs= formationService.findOne(code).getContributeurs();
		result = new ModelAndView("arbreFormation/contributeur");
		result.addObject("contibuteurs", contibuteurs);
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
			result = new ModelAndView("arbreFormation/edit");
			result.addObject("formation", formation);
		} else {
			try {
				boolean responsableSave = false;

				// Erreur : la clé ne peut être vide
				if (formation.getCode().length() == 0) { // error
					result = new ModelAndView("arbreFormation/edit");
					result.addObject("formation", formation);
				}

				// On regarde si un responsable est rempli et aussi s'il existe pour pouvoir être associé.
				if (formation.getResponsable() != null && formation.getResponsable().getLogin() != null) {
					Person p = personService.findOne(formation.getResponsable().getLogin());
					if (p != null) {
						responsableSave = true;
						formation.setResponsable(p);
					}
				}
				// Si un responsable n'est pas associé on défait le responsable créé par jsp.
				if (!responsableSave)
					formation.setResponsable(null);

				formationService.save(formation);
				result = new ModelAndView("redirect:list.htm");

			} catch (Throwable oops) {
				oops.printStackTrace();
				result = new ModelAndView("arbreFormation/list");
				result.addObject("message", "commit.formation.error");
			}
		}
		return result;
	}
	


}