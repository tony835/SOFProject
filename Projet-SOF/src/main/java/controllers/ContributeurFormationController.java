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
		Collection<Person> persons =personService.findAll();
		result.addObject("allcontributeurs", persons);

		result.addObject("code", code);

		return result;
	}

	/**
	 * Sauvegarde d'une formation
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save( @RequestParam String contrib,@RequestParam String code) {


		ModelAndView result;
		try{
			Formation formation= formationService.findOne(code);
			Person contributeur= personService.findOne(contrib);
			formation.getContributeurs().add(contributeur);
			formationService.save(formation);
		} catch (Throwable oops) {
			oops.printStackTrace();
			result = edit(code);
			result.addObject("message", "commit.formation.contributeur");
			return result;
		}

		return edit(code);
	}
	


}