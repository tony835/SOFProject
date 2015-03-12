package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.jdom2.JDOMException;
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
import domain.FieldObject;
import domain.FieldObjectId;
import domain.Formation;
import domain.Object;
import domain.Person;
import domain.TypeObject;
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
	 * @return La vue qui mene au jsp traitant cet action
	 * @throws IOException
	 * @throws JDOMException
	 */
	@RequestMapping("/list")
	public ModelAndView allFormation() throws JDOMException, IOException {
		ModelAndView result;
				Collection<Formation> formations = null;
		if (user.isConceptor())
			formations = formationService.findAll();
		else if (user.isConnected())
			formations = formationService.findByResponsable(user.getLogin());

		result = new ModelAndView("formation/list");
		result.addObject("formations", formations);

		return result;
	}

	/**
	 * Liste des formations en version visiteur
	 * 
	 * @return
	 */
	@RequestMapping("/offre")
	public ModelAndView allFormationVisitor() {
		ModelAndView result;
		Collection<Formation> formations = formationService.findAll();
		Collection<Object> objetsVisible = new ArrayList<Object>();

		for (Formation formation : formations) {
			if (formation.isVisible()) {
				objetsVisible.add(objectService.findOne(formation.getCode()));
			}
		}

		formations.clear();
		formations = null;

		result = new ModelAndView("formation/offre");
		result.addObject("FormationVisible", objetsVisible);

		return result;
	}

	/**
	 * Edition d'une formation
	 * 
	 * @param formationId
	 *            l'id de la formation que nous souhaitons modifier
	 * @return La vue qui mène au jsp traitant cet action
	 * @throws IOException
	 * @throws JDOMException
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required = false) String cobject) throws JDOMException, IOException {
		ModelAndView result;
		Formation formation = null;

		try {
			formation = formationService.create();
			if (formation == null) {
				throw new Exception("Erreur à la création de la formation");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("master-page/error", "error", "erreur.BD");
		}

		Collection<Person> allPersons = personService.findAll();
		if (!user.isConceptor())
			return new ModelAndView("authentification/login");
		else if (cobject != null && cobject != "") {
			formation = formationService.findOne(cobject);
			// Erreur pour trouver la formation
			if(formation == null){
				return new ModelAndView("redirect:edit.htm");
			}
		}

		result = new ModelAndView("formation/edit");
		result.addObject("formation", formation);
		result.addObject("allPersons", allPersons);

		return result;
	}

	/**
	 * Sauvegarde d'une formation
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(@RequestParam(required = false) String cobject, @Valid @ModelAttribute Formation formation,
			BindingResult bindingResult) {
		
		System.out.println("type de diplome : " + formation.getDiplomeType());
		
		ModelAndView result = new ModelAndView("formation/edit");
		if (bindingResult.hasErrors()) {
			System.out.println(formation.getName());
			result = new ModelAndView("formation/edit");
		} else {
			try {
				if (user.isConceptor()) {
					// Erreur : la clé ne peut être vide
					if (formation.getCode().length() == 0) { // error
						result = new ModelAndView("formation/edit");
						result.addObject("formation", formation);
						result.addObject("error", "formation.edit.codeNotEmpty");
						return result;
					}

					// On regarde si un responsable est rempli et aussi s'il existe pour pouvoir être associé.
					if (formation.getResponsable() == null || formation.getResponsable().getLogin() == null
							|| formation.getResponsable().getLogin().equals("")) {
						formation.setResponsable(personService.findOne(user.getLogin()));
					}
					Person p = null;
					Formation f = null;
					try {
						p = personService.findOne(formation.getResponsable().getLogin());
					} catch (Exception e) {
						e.printStackTrace();
						return new ModelAndView("master-page/error", "error", "erreur.BD");
					}
					if (p != null) {
						formation.setResponsable(p);
					} else {
						result = new ModelAndView("formation/edit");
						// Si un responsable n'est pas associé on défait le responsable créé par jsp.
						formation.setResponsable(null);
						result.addObject("error", "formation.edit.unknowResponsable");
						result.addObject("formation", formation);
						return result;
					}

					if (cobject == null || cobject.length() == 0) {
						// il s'agit alors d'une création, on vérifie que le code n'existe pas déjà
						try {
							if (objectService.findOne(formation.getCode()) != null) {
								// Il existe déja, on renvoit dans la même page, avec une erreur en essayant de donner
								// un nouveau code
								result = new ModelAndView("formation/edit");
								result.addObject("formation", formation);
								int tmp, nb = 0;
								Random rand = new Random();
								do {
									tmp = (int) rand.nextInt(1000);
									++nb;
									if (nb > 10)
										break;
								} while (objectService.findOne(formation.getCode() + "" + tmp) != null);
								if (nb < 10) {
									formation.setCode(formation.getCode() + "" + tmp);
									result.addObject("error", "formation.edit.codeAlwaysExistingProposingNew");
									return result;
								} else {
									result.addObject("error", "formation.edit.codeAlwaysExisting");
									return result;
								}
							}
							// on sauvegarde la formation
							else{
								formationService.save(formation);
								formation.setContexte(formation);
								formationService.save(formation);
								result = new ModelAndView("redirect:list.htm");
								return result;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					try {
						f = formationService.findOne(formation.getCode());
						if(f == null){
							result.addObject("error", "formation.edit.notExisting");
							return result;
						}

						f.setName(formation.getName());
						f.setVisible(formation.isVisible());
						f.setResponsable(formation.getResponsable());
						f.setDiplomeType(formation.getDiplomeType());
						f.setFormationField(formation.getFormationField());;
						formationService.save(f);

						result = new ModelAndView("redirect:list.htm");
					} catch (Exception e) {
						e.printStackTrace();
						return new ModelAndView("master-page/error", "error", "erreur.BD");
					}
				}
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

		/*
		 * domain.Object o = objectService.create(); o.setCode("Rendu_ProcheYes"); objectService.save(o);
		 * 
		 * //formationService.findOne("FORM1"); objectService.addLinkFils(objectService.findOne("Rendu_Proche"), o, 0);
		 */
		formationService.getListFormationIndente("FORM1");

		result = new ModelAndView("formation/list");
		result.addObject("formations", formations);

		return result;
	}
	
	@ModelAttribute("diplomaTypeList")
	public List<String> diplomaTypeList() {
		return (List<String>) formationService.findDiplomaType();
	}
	
	@ModelAttribute("formationFieldList")
	public List<String> formationFieldList() {
		return (List<String>) formationService.findFormationFieldList();
	}

	@ModelAttribute("user")
	public User newUser() {
		return user;
	}
}
