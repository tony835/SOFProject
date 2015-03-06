package controllers;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FilsService;
import services.FormationService;
import services.ObjectService;
import services.PersonService;
import domain.Fils;
import domain.Formation;
import domain.Object;
import domain.Person;

@Controller
@RequestMapping("/arbreFormation")
public class ArbreFormationController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public ArbreFormationController() {
		super();
	}

	@Autowired
	FormationService formationService;

	@Autowired
	PersonService personService;

	@Autowired
	ObjectService objectService;

	@Autowired
	FilsService pereFilsService;

	/**
	 * Liste touts les formations.
	 * 
	 * @return La vue qui mène au jsp traitant cet action
	 */
	@RequestMapping("/list")
	public ModelAndView allFormation(@RequestParam String code) {
		ModelAndView result;

		Collection<Object> objects = objectService.objectsNonLiee(code);
		result = new ModelAndView("arbreFormation/list");
		result.addObject("ObjetNonLie", objects);
		List<Pair<domain.Object, Integer>> arbreFormations = formationService.getListFormationIndente(code);

		result.addObject("formations", arbreFormations);
		return result;
	}

	@RequestMapping("/gestionFils")
	public ModelAndView gestionFils(@RequestParam String code) {
		ModelAndView result;
		result = new ModelAndView("arbreFormation/gestionFils");
		List<Fils> list = objectService.getShild(code);
		domain.Object o = objectService.findOne("code");
		result.addObject("objEnCours", o);
		result.addObject("listFils", list);
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
		result = new ModelAndView("arbreFormation/edit");
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
	
	@RequestMapping(value = "/gestionFilsEditRang", method = RequestMethod.POST)
	public ModelAndView gestionFilsEditRang(@RequestParam(value="codeEnCours",required=true) String codeEnCours, @RequestParam(value="rang",required=true) String rang, @RequestParam(value="code",required=true) String code) {
		domain.Object obj = objectService.findOne(codeEnCours);
		Fils tmp = null;
		if(obj != null){
			for (Fils f : obj.getAllFils()){
				if(f.getFils().getCode().equals(code)){
					tmp = f;
					break;
				}
			}

			if(tmp != null){
				Integer rangInt = new Integer(rang);
				if(rangInt != null){
					tmp.setRang(rangInt);
					pereFilsService.save(tmp);
				}
			}
		}
		ModelAndView result = new ModelAndView("redirect:gestionFils.htm?code="+codeEnCours);
		return result;
	}
}