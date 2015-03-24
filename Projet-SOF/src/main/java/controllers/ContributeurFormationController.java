package controllers;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import services.FormationService;
import services.PersonService;
import domain.Formation;
import domain.Person;
import domain.User;

@Transactional
@Controller
@RestController
@RequestMapping("/formation/contributeur")
public class ContributeurFormationController extends AbstractController {

	// Constructors -----------------------------------------------------------

	/**
	 * Constructeur par defaut
	 */
	public ContributeurFormationController() {
		super();
	}

	@Autowired
	FormationService formationService;

	@Autowired
	PersonService personService;

	@Autowired
	User user;

	/**
	 * Permet d'editer
	 * @param code
	 * @return ModelAndView
	 */
	@Transactional
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = false) String code) {
		ModelAndView result;
		result = new ModelAndView("arbreFormation/contributeur");
		if(code == null || code.length() == 0){
			return result;
		}
		try {
			Formation formation = formationService.findOne(code);
			if(formation == null){
				return result;
			}
			if (!user.isResponsable(formation)) {
				result = new ModelAndView("redirect:/formation/list.htm");
			}
			formation.getContributeurs().size();
			Collection<Person> contibuteurs = formation.getContributeurs();
			contibuteurs.size();
			result.addObject("contibuteurs", contibuteurs);
			Collection<Person> persons = personService.findAll();
			result.addObject("allcontributeurs", persons);

			result.addObject("code", code);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Permet de lister les contributeurs d'une formation
	 * @param code Un code objet
	 * @return Collection<Person>
	 */
	@RequestMapping(value = "/listJs")
	public Collection<Person> list(@RequestParam String code) {
		Collection<Person> contibuteurs = formationService.findOne(code).getContributeurs();
		return contibuteurs;
	}

	/**
	 * Sauvegarde d'une formation
	 * @param contrib
	 * @param code
	 * @return ModelAndView
	 */
	@Transactional
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(@RequestParam String contrib, @RequestParam String code) {
		ModelAndView result;
		try {
			Formation formation = formationService.findOne(code);
			if (formation == null || !user.isResponsable(formation)) {
				result = new ModelAndView("redirect:/formation/list.htm");
			}
			formation.getContributeurs().size();

			for(Person f : formation.getContributeurs()){
				if(f.getLogin().equals(contrib)){
					result = edit(code);
					result.addObject("message", "commit.formation.contributeurAlreadyExisting");
					return result;
				}
			}
			Person contributeur = personService.findOne(contrib);
			if(contributeur != null){
				formation.getContributeurs().add(contributeur);
				formationService.save(formation);
			}
		} catch (Exception oops) {
			oops.printStackTrace();
			result = edit(code);
			result.addObject("message", "commit.formation.contributeur");
			return result;
		}

		return edit(code);
	}

	/**
	 * Permet de supprimer un contributeur sur une formation
	 * @param contrib
	 * @param code
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam String contrib, @RequestParam String code) {

		ModelAndView result;
		try {
			Formation formation = formationService.findOne(code);
			if (formation == null || !user.isResponsable(formation)) {
				result = new ModelAndView("redirect:/formation/list.htm");
			}
			Person contributeur = personService.findOne(contrib);
			Collection<Person> persons = formation.getContributeurs();
			Person tmp = null;
			for (Person per : persons) {
				if (per.getLogin().equals(contributeur.getLogin())) {
					tmp = per;
				}
			}
			if (tmp != null)
				persons.remove(tmp);
			formation.setContributeurs(persons);
			formationService.save(formation);
		} catch (Throwable oops) {
			oops.printStackTrace();
			result = edit(code);
			result.addObject("message", "commit.contributeur.delete");
			return result;
		}

		return edit(code);
	}

}