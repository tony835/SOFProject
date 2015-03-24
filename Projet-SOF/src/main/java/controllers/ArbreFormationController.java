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
	domain.Object formation;

	/**
	 * Liste touts les formations.
	 * @return ModelAndView qui m�ne au jsp traitant cet action
	 */
	@Transactional
	@RequestMapping("/list")
	public ModelAndView allFormation(
			@RequestParam(required = false) String code,
			RedirectAttributes redirectAttributes) {
		ModelAndView result;
		if (code == null || code.length() == 0) {
			result = new ModelAndView("redirect:/formation/list.htm");
			redirectAttributes.addFlashAttribute("error",
					"arbreformation.ArgInvalide");
			return result;
		}
		Formation formation = null;
		// La formation n'existe pas
		if ((formation = formationService.findOne(code)) == null) {
			result = new ModelAndView("redirect:/formation/list.htm");
			redirectAttributes.addFlashAttribute("error",
					"arbreformation.unknow");
			return result;
		}

		// Si la personne n'est pas un responsable
		if (!user.isResponsable(formation)) {
			if (user.isConnected()) {
				result = new ModelAndView("redirect:/formation/list.htm");
			} else {
				result = new ModelAndView("redirect:/auth/login.htm");
			}
			redirectAttributes.addFlashAttribute("error",
					"ArbreFormation.noResponsable");
			return result;
		}

		Collection<domain.Object> objects = objectService.objectsNonLiee(code);
		objects.addAll(objectService.objectsNonLieeM(formation, code));
		result = new ModelAndView("arbreFormation/list");
		result.addObject("ObjetNonLie", objects);
		formation = formationService.findOne(code);

		if (formation == null) {// erreur

		}
		result.addObject("formation", formation);

		formationService.getArbre(formation);
		result.addObject("arbre", formationService.getArbreRetour());
		return result;
	}

	/**
	 * Edition d'une formation
	 * 
	 * @param formationId
	 *            l'id de la formation que nous souhaitons modifier
	 * @return ModelAndView qui m�ne au jsp traitant cet action
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
	 * 
	 * @param formation
	 *            La formation à sauvegarder
	 * @param bindingResult
	 *            les r�sultats d'erreurs
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	// TODO
	public ModelAndView save(@Valid @ModelAttribute Formation formation,
			BindingResult bindingResult) {
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
				// Si un responsable n'est pas associé on défait le
				// responsable créé par jsp.
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

	/**
	 * Permet de la gestion des fils
	 * @param cobject
	 * @param typeobject
	 * @param redirectAttributes 
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/gestionFils", method = RequestMethod.GET)
	public ModelAndView gestionFils(
			@RequestParam(required = false) String cobject,
			@RequestParam(required = false) String typeobject,
			RedirectAttributes redirectAttributes) {
		ModelAndView result;

		if (cobject == null || cobject.length() == 0) {
			result = new ModelAndView("redirect:/formation/list.htm");
			redirectAttributes.addFlashAttribute("error",
					"arbreformation.ArgInvalide");
			return result;
		}

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

		if (!user.isResponsable(o.getContexte())) {
			result = new ModelAndView("redirect:/auth/login.htm");
			redirectAttributes.addFlashAttribute("error",
					"ArbreFormation.noResponsable");
			return result;
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

		List<Pair<Boolean, Fils>> listP = new ArrayList<Pair<Boolean, Fils>>();
		for (Fils f : list) {
			// Si on peut modifier le fils (le père est dans le même arbre)
			if (objectService.canBeDeleted(o, f.getFils())) {
				listP.add(new Pair<Boolean, Fils>(true, f));
			} else {
				listP.add(new Pair<Boolean, Fils>(false, f));
			}
		}

		result.addObject("objEnCours", o);
		result.addObject("listFils", listP);
		for (Fils fils : list) {
			fils.getFils().getAllFils().size();
		}
		if (typeobject != null && !typeobject.equals("")
				&& typeService.findOne(typeobject) != null) {
			result.addObject("typeobject", typeService.findOne(typeobject));
		} else {
			result.addObject("typeobject", new TypeObject());
		}
		result.addObject("selectedFils", selectedFils);

		String descError = objectService.checkContentModel(o);
		result.addObject("descError", descError);

		return result;
	}

	/**
	 * Permet la suppression de liens
	 * @param pere
	 * @param fils
	 * @param redirectAttributes
	 * @return ModelAndView
	 */
	@RequestMapping("/supprimer")
	public ModelAndView suppressionLiens(
			@RequestParam(value = "pere", required = false) String pere,
			@RequestParam(value = "fils", required = false) String fils,
			RedirectAttributes redirectAttributes) {
		if ((pere == null || pere.length() == 0)
				|| (fils == null || fils.length() == 0)) {
			ModelAndView result = new ModelAndView(
					"redirect:/arbreFormation/gestionFils.htm?cobject=" + pere);
			redirectAttributes.addFlashAttribute("error",
					"arbreformation.ArgInvalide");
			return result;
		}
		try {
			domain.Object pereO = objectService.findOne(pere);
			domain.Object filsO = objectService.findOne(fils);
			if (pereO == null || filsO == null) {
				return new ModelAndView("arbreFormation/gestionFils", "error",
						"arbreformation.cObjectUnknow");
			}
			// Si on peut modifier le fils (le père est dans le même arbre)
			if (!objectService.canBeDeleted(pereO, filsO)) {
				ModelAndView result = new ModelAndView(
						"redirect:/arbreFormation/gestionFils.htm?cobject="
								+ pere);
				redirectAttributes.addFlashAttribute("error",
						"arbreformation.contientFeuille");
				return result;
			}
			if (!user.isResponsable(pereO.getContexte())) {
				ModelAndView result = new ModelAndView(
						"redirect:/arbreFormation/gestionFils.htm?cobject="
								+ pere);
				redirectAttributes.addFlashAttribute("error",
						"ArbreFormation.noResponsable");
				return result;
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

	/**
	 * Permet l'�dition d'un rang d'un fils.
	 * @param codeEnCours
	 * @param rang
	 * @param code
	 * @param redirectAttributes
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/gestionFilsEditRang", method = RequestMethod.POST)
	  public ModelAndView gestionFilsEditRang(
			@RequestParam(value = "codeEnCours", required = false) String codeEnCours,
			@RequestParam(value = "rang", required = false) String rang,
			@RequestParam(value = "cobject", required = false) String code,
			RedirectAttributes redirectAttributes) {

		if ((codeEnCours == null || codeEnCours.length() == 0)
				|| (rang == null || rang.length() == 0)
				|| (code == null || code.length() == 0)) {
			ModelAndView resultat = new ModelAndView(
					"redirect:/arbreFormation/gestionFils.htm?cobject="
							+ codeEnCours);
			redirectAttributes.addFlashAttribute("error",
					"arbreformation.ArgInvalide");
			return resultat;
		}

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
		if (!user.isResponsable(obj.getContexte())) {
			ModelAndView resultat = new ModelAndView(
					"redirect:/arbreFormation/gestionFils.htm?cobject="
							+ codeEnCours);
			redirectAttributes.addFlashAttribute("error",
					"ArbreFormation.noResponsable");
			return resultat;
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
						pereFilsService.save(tmp);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		ModelAndView result = new ModelAndView(
				"redirect:gestionFils.htm?cobject=" + codeEnCours);
		return result;
	}

	/**
	 * Fonction de creation
	 * @param context
	 * @param cobject
	 * @param redirectAttributes
	 * @return String
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(@RequestParam(required = false) String context,
			@RequestParam(required = false) String cobject,
			RedirectAttributes redirectAttributes) {
		if (cobject != null) {
			try {
				if (objectService.findOne(cobject) == null) {
					if (!user.isResponsable(objectService.findOne(cobject)
							.getContexte())) {
						redirectAttributes.addFlashAttribute("error",
								"ArbreFormation.noResponsable");
						return "redirect:/auth/login.htm";
					}
					return "redirect:create.htm?context=" + context;
				} else {
					formation = objectService.findOne(cobject).getContexte();
					redirectAttributes.addFlashAttribute("formationName",
							objectService.findOne(cobject).getContexte()
									.getName());
					if (!user.isResponsable(objectService.findOne(cobject)
							.getContexte())) {
						redirectAttributes.addFlashAttribute("error",
								"ArbreFormation.noResponsable");
						return "redirect:/auth/login.htm";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "redirect:create.htm?context=" + context;
			}
		}
		try {
			if (!user.isResponsable(formationService.findOne(context))) {
				redirectAttributes.addFlashAttribute("error",
						"ArbreFormation.noResponsable");
				return "redirect:/auth/login.htm";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "master-page/error";
		}
		if (context != null) {
			formation = objectService.findOne(context);
			redirectAttributes.addFlashAttribute("formationName", objectService
					.findOne(context).getName());
		}

		return "tmpObjectCreation/createObject";
	}

	/**
	 * Permet de sauvegarder un nouveau objet
	 * @param context
	 * @param cobject
	 * @param myobject
	 * @param bindingResult
	 * @param redirectAttributes
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView saveNewObject(
			@RequestParam(required = false) String context,
			@RequestParam(required = false) String cobject,
			@Valid @ModelAttribute domain.Object myobject,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
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
			// On regarde que la formation existe déjà pour pouvoir l'avouter
			// au context du nouvel objet.
			if (form == null) {
				ModelAndView resultat = new ModelAndView(
						"tmpObjectCreation/createObject");
				resultat.addObject("error", "arbreformation.formUnknow");
				return resultat;
			}
			if (!user.isResponsable(form.getContexte())) {
				ModelAndView result = new ModelAndView(
						"redirect:/auth/login.htm");
				redirectAttributes.addFlashAttribute("error",
						"ArbreFormation.noResponsable");
				return result;
			}

			myobject.setContexte(form);

			try {
				TypeObject type = null;
				type = typeService.findOne(myobject.getTypeObject().getCode());
				// Type non reconnu
				if (type == null) {
					ModelAndView resultat = new ModelAndView(
							"tmpObjectCreation/createObject");
					resultat.addObject("error", "arbreformation.typeUnknow");
					return resultat;
				}
				domain.Object obj = null;
				obj = objectService.findOne(myobject.getCode());
				// On vérifie que l'objet n'existe pas déjà
				if (obj != null) {
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
							tmp = (int) rand.nextInt(1000);
							++nb;
							if (nb > 10)
								break;

						} while (objectService.findOne(myobject.getCode() + ""
								+ tmp) != null);
						if (nb < 10) {
							myobject.setCode(myobject.getCode() + "" + tmp);
							resultat.addObject("error",
									"arbreformation.edit.codeAlreadyExistingProposingNew");
							return resultat;
						} else {
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
					ModelAndView resultat = new ModelAndView(
							"tmpObjectCreation/createObject");
					resultat.addObject("error", "arbreformation.cObjectUnknow");
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

	/**
	 * Creation d'un nouveau objet
	 * @param code
	 * @return Object
	 */
	@ModelAttribute("myobject")
	public domain.Object newObject(
			@RequestParam(value = "cobject", required = false) String code) {

		if (code != null) {
			domain.Object o = objectService.findOne(code);
			if (o != null) {
				return o;
			}
		}

		domain.Object o = new domain.Object();
		o.setCode("");
		o.setName("");
		o.setMutualisable(false);
		o.setContexte(null);
		o.setTypeObject(new TypeObject());
		o.setAllFils(new ArrayList<Fils>());
		o.setFieldObjects(new ArrayList<FieldObject>());

		return o;
	}

	/**
	 * Permet de trier des objets
	 * @param code
	 * @param typeobject
	 * @param bindingResult
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/sortNLObject", method = RequestMethod.POST)
	public ModelAndView sortNLObject(
			@RequestParam(value = "cobject", required = false) String code,
			@Valid @ModelAttribute TypeObject typeobject,
			BindingResult bindingResult) {
		if (code == null || code.length() == 0) {
			ModelAndView resultat = new ModelAndView(
					"redirect:gestionFils.htm?cobject=" + code + "&typeobject=");
			return resultat;
		}
		if (typeobject != null && typeobject.getCode() != null
				&& !typeobject.getCode().equals("")) {
			return new ModelAndView("redirect:gestionFils.htm?cobject=" + code
					+ "&typeobject=" + typeobject.getCode());
		}
		return new ModelAndView("redirect:gestionFils.htm?cobject=" + code
				+ "&typeobject=");
	}

	/**
	 * @return
	 */
	@ModelAttribute("formationName")
	public domain.Object formationName() {
		return formation;
	}

	/**
	 * @return
	 */
	@ModelAttribute("typesList")
	public List<TypeObject> productTypes() {
		return (List<TypeObject>) typeService.findTypeFils();
	}

	/**
	 * @param typeobject
	 * @param cobject
	 * @return
	 */
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
						.findNonFLinkedObject(context, cobject);
			} catch (Exception e) {
				return new ArrayList<domain.Object>();
			}
		} else {
			try {
				TypeObject t = typeService.findOne(typeobject);
				if (t == null) {
					return (List<domain.Object>) objectService
							.findNonFLinkedObject(context, cobject);
				}
				type = t.getCode();
			} catch (Exception e) {
				return (List<domain.Object>) objectService
						.findNonFLinkedObject(context, cobject);
			}

		}
		return (List<domain.Object>) objectService.findTypedNonLinkedObject(
				context, type, cobject);
	}

	/**
	 * @param typeobject
	 * @param cobject
	 * @return
	 */
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
						.findMutualisableObjects(context, cobject);
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
			obj = objectService.findTypedMutualisableObjects(context, type,
					cobject);
			if (obj == null) {
				return new ArrayList<domain.Object>();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<domain.Object>();
		}
		return (List<domain.Object>) obj;
	}

	/**
	 * @param cobject
	 * @param typeobject
	 * @param rang
	 * @param selectedFils
	 * @param result
	 * @param redirectAttributes
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/addFils", method = RequestMethod.POST)
	public ModelAndView addFils(
			@RequestParam(value = "cobject", required = false) String cobject,
			@RequestParam(value = "typeobject", required = false) String typeobject,
			@RequestParam(value = "rang", required = false) String rang,
			@Valid @ModelAttribute domain.Object selectedFils,
			BindingResult result, RedirectAttributes redirectAttributes) {

		if (cobject == null || cobject.length() == 0) {
			ModelAndView resultat = new ModelAndView(
					"redirect:gestionFils.htm?cobject=" + cobject
							+ "&typeobject=");
			redirectAttributes.addFlashAttribute("error",
					"arbreformation.ArgInvalide");
			return resultat;
		}
		if (result.hasErrors()) {
			return new ModelAndView("redirect:gestionFils.htm?cobject="
					+ cobject);
		}
		try {

			domain.Object o = objectService.findOne(cobject);
			domain.Object selectF = objectService.findOne(selectedFils
					.getCode());

			if (o != null && selectF != null) {
				if (!user.isResponsable(o.getContexte())) {
					ModelAndView resultat = new ModelAndView(
							"redirect:/arbreFormation/gestionFils.htm?cobject="
									+ o.getCode());
					redirectAttributes.addFlashAttribute("error",
							"ArbreFormation.noResponsable");
					return resultat;
				}

				if (rang == null || rang.equals("")) {
					rang = "1";
				}
				objectService.addLinkFils(o, selectF, new Integer(rang));
			}
			String type = "";
			if (typeobject != null && typeobject.length() != 0) {
				type = typeobject;
			}

			return new ModelAndView("redirect:gestionFils.htm?cobject="
					+ cobject + "&typeobject=" + type);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("master-page/error", "error", "erreur.BD");
		}
	}

	/**
	 * @return
	 */
	@ModelAttribute("user")
	public User newUser() {
		return user;
	}

	/**
	 * @param binder
	 */
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
	}
}
