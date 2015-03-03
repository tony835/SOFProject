package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FormationService;
import domain.Formation;


@Controller
@RequestMapping("/formation")
public class FormationController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public FormationController() {
		super();
	}


	@Autowired
	FormationService formationService;

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
	public ModelAndView edit() {
		ModelAndView result;
		Formation formation = formationService.create();
		result = new ModelAndView("groupe/edit");
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
			result = new ModelAndView("groupe/edit");
			result.addObject("formation", formation);
		} else {
			try{

				formationService.save(formation);
				result = new ModelAndView("redirect:list.htm");
			
			
			} catch (Throwable oops) {
				oops.printStackTrace();
				result = new ModelAndView("groupe/formation");
				result.addObject("message", "commit.formation.error");
				
			}
				
		}
		
		return result;
	}


}