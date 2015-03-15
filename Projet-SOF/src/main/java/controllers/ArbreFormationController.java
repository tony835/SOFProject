package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.FilsService;
import services.FormationService;
import services.ObjectService;
import services.PersonService;
import services.TypeObjectService;
import domain.FieldObject;
import domain.Fils;
import domain.Formation;
import domain.Person;
import domain.TypeObject;
import domain.User;

@Transactional
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

	@Autowired
	TypeObjectService typeService;

	@Autowired
	User user;

	/**
	 * Liste touts les formations.
	 * 
	 * @return La vue qui mène au jsp traitant cet action
	 */
	@RequestMapping("/list")
	public ModelAndView allFormation(@RequestParam String code,
			RedirectAttributes redirectAttributes) {
		ModelAndView result;

		// La formation n'existe pas
		if (!formationService.isFormation(code)) {
			result = new ModelAndView("redirect:/formation/list.htm");
			redirectAttributes.addFlashAttribute("error",
					"arbreformation.unknow");
			return result;
		}
		Collection<domain.Object> objects = objectService.objectsNonLiee(code);
		result = new ModelAndView("arbreFormation/listTree");
		result.addObject("ObjetNonLie", objects);
		Formation formation = formationService.findOne(code);
		List<Pair<domain.Object, Integer>> arbreFormations = formationService
				.getListFormationIndente(code);

		String descErrors = formationService.checkContentModel(code);

		result.addObject("formations", arbreFormations);
		result.addObject("formation", formation);
		result.addObject("descErrors", descErrors);

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
	public ModelAndView save(@Valid @ModelAttribute Formation formation,
			BindingResult bindingResult) {
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

				// On regarde si un responsable est rempli et aussi s'il existe
				// pour pouvoir être associé.
				if (formation.getResponsable() != null
						&& formation.getResponsable().getLogin() != null) {
					Person p = personService.findOne(formation.getResponsable()
							.getLogin());
					if (p != null) {
						responsableSave = true;
						formation.setResponsable(p);
					}
				}
				// Si un responsable n'est pas associé on défait le responsable
				// créé par jsp.
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

	@RequestMapping(value = "/gestionFils", method = RequestMethod.GET)
	public ModelAndView gestionFils(@RequestParam String cobject) {

		ModelAndView result;
		result = new ModelAndView("arbreFormation/gestionFils");
		domain.Object o = null;
		try {
			o = objectService.findOne(cobject);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("master-page/error", "error", "erreur.BD");
		}
		if (o == null) {
			return new ModelAndView("redirect:/formation/list.htm", "error",
					"arbreformation.cObjectUnknow");
		}
		List<Fils> list = null;
		try {
			list = objectService.getChild(cobject);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("master-page/error", "error", "erreur.BD");
		}
		if (list == null) {
			return new ModelAndView("master-page/error", "error", "erreur.BD");
		}
		domain.Object selectedFils = new domain.Object();
		result.addObject("objEnCours", o);
		result.addObject("listFils", list);
		result.addObject("typeobject", new TypeObject());
		result.addObject("selectedFils", selectedFils);

		String descError = objectService.checkContentModel(o);
		result.addObject("descError", descError);
		return result;
	}

	@RequestMapping("/supprimer")
	public ModelAndView suppressionLiens(
			@RequestParam(value = "pere", required = true) String pere,
			@RequestParam(value = "fils", required = true) String fils) {
		try {
			domain.Object pereO = objectService.findOne(pere);
			domain.Object filsO = objectService.findOne(fils);
			if (pereO == null || filsO == null) {
				return new ModelAndView("arbreFormation/gestionFils", "error",
						"arbreformation.cObjectUnknow");
			}
			objectService.delLienFils(pereO, filsO);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("master-page/error", "error", "erreur.BD");
		}
		ModelAndView result = new ModelAndView(
				"redirect:gestionFils.htm?cobject=" + pere);
		return result;
	}

	@RequestMapping(value = "/gestionFilsEditRang", method = RequestMethod.POST)
	public ModelAndView gestionFilsEditRang(
			@RequestParam(value = "codeEnCours", required = true) String codeEnCours,
			@RequestParam(value = "rang", required = true) String rang,
			@RequestParam(value = "cobject", required = true) String code) {
		domain.Object obj = null;
		try {
			obj = objectService.findOne(codeEnCours);
		} catch (Exception e) {
			return new ModelAndView("master-page/error", "error", "erreur.BD");
		}
		if (obj == null) {// erreur
			return new ModelAndView("arbreFormation/gestionFils", "error",
					"arbreformation.cObjectUnknow");
		}
		Fils tmp = null;
		if (obj != null) {
			for (Fils f : obj.getAllFils()) {
				if (f.getFils().getCode().equals(code)) {
					tmp = f;
					break;
				}
			}
			if (tmp != null) {
				try {
					Integer rangInt = new Integer(rang);
					if (rangInt != null) {
						tmp.setRang(rangInt);
					}
				} catch (Exception e) {
					ModelAndView result = new ModelAndView(
							"redirect:gestionFils.htm?cobject=" + codeEnCours);
					return result;
				}
				pereFilsService.save(tmp);
			}
		}
		ModelAndView result = new ModelAndView(
				"redirect:gestionFils.htm?cobject=" + codeEnCours);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(@RequestParam(required = false) String context,
			@RequestParam(required = false) String cobject) {
		if (cobject != null) {
			try {
				if (objectService.findOne(cobject) == null
						|| formationService.isFormation(cobject))
					return "redirect:create.htm?context=" + context;
			} catch (Exception e) {
				return "redirect:create.htm?context=" + context;
			}
		}
		return "tmpObjectCreation/createObject";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView saveNewObject(
			@RequestParam(required = false) String context,
			@RequestParam(required = false) String cobject,
			@Valid @ModelAttribute domain.Object myobject,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ModelAndView m = new ModelAndView("tmpObjectCreation/createObject");
			return m;
		}
		// On créé un objet
		if (cobject == null || cobject.equals("")) {
			Formation form = null;
			try {
				form = formationService.findOne(context);
			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("master-page/error", "error",
						"erreur.BD");
			}
			// On regarde que la formation existe déjà pour pouvoir l'avouter au
			// context du nouvel objet.
			if (form == null) {
				System.out.println("cas1");
				ModelAndView resultat = new ModelAndView(
						"redirect:create.htm?context="
								+ ((context == null) ? "" : context)
								+ "&cobject="
								+ ((cobject == null) ? "" : cobject));
				// redirectAttributes.addFlashAttribute("error",
				// "arbreformation.formUnknow");
				// redirectAttributes.addFlashAttribute("myobject", myobject);
				return resultat;
			}
			myobject.setContexte(form);
			try {
				TypeObject type = null;
				type = typeService.findOne(myobject.getTypeObject().getCode());
				// Type non reconnu
				if (type == null) {
					ModelAndView resultat = new ModelAndView(
							"redirect:create.htm?context="
									+ ((context == null) ? "" : context)
									+ "&cobject="
									+ ((cobject == null) ? "" : cobject));
					// redirectAttributes.addFlashAttribute("myobject",
					// myobject);
					// redirectAttributes.addFlashAttribute("error",
					// "arbreformation.typeUnknow");
					return resultat;
				}
				domain.Object obj = null;
				obj = objectService.findOne(myobject.getCode());
				// On vérifie que l'objet n'existe pas déjà
				if (obj != null) {
					System.out.println("cas3");
					ModelAndView resultat = new ModelAndView(
							"tmpObjectCreation/createObject");
					resultat.addObject("myobject", myobject);
					try {
						// Il existe déja, on renvoit dans la même page, avec
						// une erreur en essayant de donner
						// un nouveau code
						int tmp, nb = 0;
						Random rand = new Random();
						do {
							System.out.println("ppppp");
							tmp = (int) rand.nextInt(1000);
							++nb;
							if (nb > 10)
								break;
							System.out.println("essai");
							System.out.println(myobject.getCode() + "" + tmp);
						} while (objectService.findOne(myobject.getCode() + ""
								+ tmp) != null);
						if (nb < 10) {
							myobject.setCode(myobject.getCode() + "" + tmp);
							System.out.println(myobject.getCode() + "" + tmp);
							resultat.addObject("error",
									"arbreformation.edit.codeAlreadyExistingProposingNew");
							return resultat;
						} else {
							System.out.println("ici");
							resultat.addObject("error",
									"arbreformation.codeAlreadyExisting");
							return resultat;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					return resultat;
				}
				objectService.save(myobject, user); // Tester les droits
			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("master-page/error", "error",
						"erreur.BD");
			}
		} else {// L'objet existe déjà
			domain.Object obj = null;
			try {
				obj = objectService.findOne(cobject);
				// On vérifie que l'objet existe déjà
				// TODO A tester car passe mais n'affiche pas l'erreur
				if (obj == null) {
					System.out.println("cas2");
					ModelAndView resultat = new ModelAndView(
							"redirect:create.htm?context="
									+ ((context == null) ? "" : context)
									+ "&cobject="
									+ ((cobject == null) ? "" : cobject));
					// redirectAttributes.addFlashAttribute("error",
					// "arbreformation.cObjectUnknow");
					// redirectAttributes.addFlashAttribute("myobject",
					// myobject);
					return resultat;
				}
				myobject.setContexte(obj.getContexte());
				obj.setName(myobject.getName());
				obj.setMutualisable(myobject.isMutualisable());
				objectService.save(obj, user); // Tester les droits
			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("master-page/error", "error",
						"erreur.BD");
			}
		}
		return new ModelAndView("redirect:list.htm?code="
				+ myobject.getContexte().getCode());
	}

	@ModelAttribute("myobject")
	public domain.Object newObject(
			@RequestParam(value = "cobject", required = false) String code) {

		if (code != null) {
			domain.Object o = objectService.findOne(code);
			return o;
		}

		domain.Object o = new domain.Object();
		o.setCode("");
		o.setName("");
		o.setVersion("");
		o.setMutualisable(false);
		o.setContexte(null);
		o.setTypeObject(new TypeObject());
		o.setAllFils(new ArrayList<Fils>());
		o.setFieldObjects(new ArrayList<FieldObject>());

		return o;
	}

	@RequestMapping(value = "/sortNLObject", method = RequestMethod.POST)
	public ModelAndView sortNLObject(
			@RequestParam(value = "cobject", required = true) String code,
			@Valid @ModelAttribute TypeObject typeobject,
			BindingResult bindingResult) {
		return new ModelAndView("redirect:gestionFils.htm?cobject=" + code
				+ "&typeobject=" + typeobject.getCode());
	}

	@ModelAttribute("typesList")
	public List<TypeObject> productTypes() {
		return (List<TypeObject>) typeService.findAll();
	}

	@ModelAttribute("NonLinkedObjectList")
	public List<domain.Object> nlObjectList(
			@RequestParam(value = "typeobject", required = false) String typeobject,
			@RequestParam(value = "cobject", required = false) String cobject) {
		if (cobject == null || cobject.equals(""))
			return new ArrayList<domain.Object>();
		String type;
		String context;
		try {
			domain.Object o = objectService.findOne(cobject);
			if (o == null) {
				return new ArrayList<domain.Object>();
			}
			context = o.getContexte().getCode();
		} catch (Exception e) {
			return new ArrayList<domain.Object>();
		}
		if (typeobject == null || typeobject.equals("")) {
			try {
				return (List<domain.Object>) objectService
						.objectsNonLiee(context);
			} catch (Exception e) {
				return new ArrayList<domain.Object>();
			}
		} else {
			try {
				TypeObject t = typeService.findOne(typeobject);
				if (t == null) {
					return (List<domain.Object>) objectService
							.objectsNonLiee(context);
				}
				type = t.getCode();
			} catch (Exception e) {
				return (List<domain.Object>) objectService
						.objectsNonLiee(context);
			}
		}
		return (List<domain.Object>) objectService.findTypedNonLinkedObject(
				context, type);
	}

	@ModelAttribute("mutualisableObjectList")
	public List<domain.Object> mObjectList(
			@RequestParam(value = "typeobject", required = false) String typeobject,
			@RequestParam(value = "cobject", required = false) String cobject) {
		if (cobject == null || cobject.equals(""))
			return new ArrayList<domain.Object>();
		String type;
		domain.Object o = null;
		try {
			o = objectService.findOne(cobject);
			if (o == null) {
				return new ArrayList<domain.Object>();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<domain.Object>();
		}
		String context = (o.getContexte() == null) ? "" : o.getContexte()
				.getCode();
		if (typeobject == null || typeobject.equals("")) {
			try {
				Collection<domain.Object> obj = objectService
						.findMutualisableObjects(context);
				if (obj == null) {
					return new ArrayList<domain.Object>();
				}
				return (List<domain.Object>) obj;
			} catch (Exception e) {
				e.printStackTrace();
				return new ArrayList<domain.Object>();
			}
		} else {
			try {
				TypeObject t = typeService.findOne(typeobject);
				if (t == null) {
					return new ArrayList<domain.Object>();
				}
				type = t.getCode();
			} catch (Exception e) {
				e.printStackTrace();
				return new ArrayList<domain.Object>();
			}
		}
		Collection<domain.Object> obj = null;
		try {
			obj = objectService.findTypedMutualisableObjects(context, type);
			if (obj == null) {
				return new ArrayList<domain.Object>();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<domain.Object>();
		}
		return (List<domain.Object>) obj;
	}

	@RequestMapping(value = "/addFils", method = RequestMethod.POST)
	public ModelAndView addFils(
			@RequestParam(value = "cobject", required = true) String cobject,
			@Valid @ModelAttribute domain.Object selectedFils,
			BindingResult result) {
		if (result.hasErrors()) {
			System.out.println(" --- " + result.toString());
			return new ModelAndView("redirect:gestionFils.htm?cobject="
					+ cobject);
		}
		domain.Object o = objectService.findOne(cobject);
		Fils tmpF = new Fils();
		tmpF.setRang(1);
		tmpF.setFils(selectedFils);
		pereFilsService.save(tmpF);
		o.getAllFils().add(tmpF);
		objectService.save(o, user);
		String type = "";
		if (o.getTypeObject() != null && o.getTypeObject().getCode() != null
				&& o.getTypeObject().getCode().equals(""))
			type = "";
		return new ModelAndView("redirect:gestionFils.htm?cobject=" + cobject
				+ "&typeobject=" + type);
	}

	@ModelAttribute("user")
	public User newUser() {
		return user;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(List.class, "typeObject",
				new CustomCollectionEditor(List.class) {
					@Override
					protected Object convertElement(Object element) {
						List<TypeObject> retour = new ArrayList<TypeObject>();
						retour.add(typeService.findOne((String) element));
						return retour;
					}
				});
		// binder.registerCustomEditor(Collection.class, "NonLinkedObjectList",
		// new
		// CustomCollectionEditor(Collection.class)
		// {
		// @Override
		// protected Object convertElement(Object element)
		// {
		//
		// System.out.println("init ... BENDER !!!");
		//
		// Collection <Fils> retour = new ArrayList<Fils>();
		// Fils f = new Fils();
		// f.setRang(1);
		// f.setFils(objectService.findOne((String)element));
		// retour.add(f);
		// return retour;
		// }
		//
		// });
	}
}
