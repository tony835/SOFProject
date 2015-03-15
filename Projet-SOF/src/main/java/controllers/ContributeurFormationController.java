package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.text.StyledEditorKit.BoldAction;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.mapper.Mapper;

import services.FormationService;
import services.PersonService;
import domain.Formation;
import domain.Person;
@Transactional
@Controller
@RestController
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
	
	@Transactional
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam String code) {
		ModelAndView result;
		Formation f = formationService.findOne(code);
		f.getContributeurs().size();
		Collection<Person> contibuteurs = f.getContributeurs();
		result = new ModelAndView("arbreFormation/contributeur");
		result.addObject("contibuteurs", contibuteurs);
		Collection<Person> persons = personService.findAll();
		result.addObject("allcontributeurs", persons);

		result.addObject("code", code);

		return result;
	}
	@RequestMapping(value = "/listJs")
	public  Collection<Person> list(@RequestParam String code) {
		Collection<Person> contibuteurs = formationService.findOne(code)
				.getContributeurs();
		return contibuteurs;
	}

	/**
	 * Sauvegarde d'une formation
	 */
	@Transactional
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(@RequestParam String contrib,
			@RequestParam String code) {

		ModelAndView result;System.out.println("lppppmpmpmpm");
		try {
			Formation formation = formationService.findOne(code);
			formation.getContributeurs().size();
			Person contributeur = personService.findOne(contrib);
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

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam String contrib,
			@RequestParam String code) {

		ModelAndView result;
		try {
			Formation formation = formationService.findOne(code);
			Person contributeur = personService.findOne(contrib);
			Collection<Person> persons = formation.getContributeurs();
			Person tmp = null;
			for (Person per : persons) {
				if (per.getLogin().equals(contributeur.getLogin())){
					tmp = per;
				}
			}
			if(tmp != null)
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