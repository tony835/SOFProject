package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	public ModelAndView allFormation(@RequestParam String code) {


		/*domain.Object objSem = objectService.findOne("SEM1");
		domain.Object objIsl = objectService.findOne("ISL");
		objectService.addLinkFils(objIsl, objSem, 0);*/
		/*domain.Object obj1;
		obj1 = objectService.findOne("aaaa");
		if(obj1.getAllFils() == null) System.out.println("mince");
		domain.Object obj2 = new domain.Object();
		obj2.setCode("bbbbap");
		objectService.save(obj2);

		objectService.addLinkFils(obj1, obj2, 0);

		obj1 = objectService.findOne("aaaa");
		for (Fils f : obj1.getAllFils()){
			System.out.println("=======>"+f.getFils().getCode());
		}*/
		/*domain.Object objIsl = objectService.findOne("ISL");
		Formation form = formationService.findOne("FORM1");*/
		/*objectService.addLinkFils(form, objIsl, 0);*/
		/*System.out.println(objIsl.getCode()+"===>"+objIsl.getAllFils().size());
		for(Fils f:form.getAllFils()){
			System.out.println(f.getFils().getCode()+"===>"+f.getFils().getAllFils().size());
		}*/
		ModelAndView result;

		Collection<domain.Object> objects = objectService.objectsNonLiee(code);
		result = new ModelAndView("arbreFormation/list");
		result.addObject("ObjetNonLie", objects);
		List<Pair<domain.Object, Integer>> arbreFormations = formationService.getListFormationIndente(code);

		result.addObject("formations", arbreFormations);
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



	@RequestMapping(value = "/gestionFils", method = RequestMethod.GET)
	public ModelAndView gestionFils(@RequestParam String cobject) {

		ModelAndView result;
		result = new ModelAndView("arbreFormation/gestionFils");
		List<Fils> list = objectService.getChild(cobject);
		domain.Object selectedFils = new domain.Object();
		domain.Object o = objectService.findOne(cobject);
		result.addObject("objEnCours", o);
		result.addObject("listFils", list);
		result.addObject("typeobject", new TypeObject());
		result.addObject("selectedFils", selectedFils);
		
		return result;
	}

	@RequestMapping("/supprimer")
	public ModelAndView suppressionLiens(@RequestParam(value="pere",required=true) String pere, @RequestParam(value="fils",required=true) String fils) {
		objectService.delLienFils(objectService.findOne(pere), objectService.findOne(fils));
		ModelAndView result = new ModelAndView("redirect:gestionFils.htm?cobject="+pere);
		return result;
	}


	@RequestMapping(value = "/gestionFilsEditRang", method = RequestMethod.POST)
	public ModelAndView gestionFilsEditRang(@RequestParam(value="codeEnCours",required=true) String codeEnCours, @RequestParam(value="rang",required=true) String rang, @RequestParam(value="cobject",required=true) String code) {
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
		ModelAndView result = new ModelAndView("redirect:gestionFils.htm?cobject="+codeEnCours);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create() {
		return "tmpObjectCreation/createObject";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String saveNewObject(@ModelAttribute @Valid domain.Object o, BindingResult result, @RequestParam(required=false) String context, @RequestParam(required=false) String cobject) {
		if(result.hasErrors()) {// TODO
			return "redirect:create";
		}
		if(o.getAllFils() == null) System.out.println("Alexandre a raison");

		domain.Object tmp = objectService.findOne(cobject);
		o.setAllFils(tmp.getAllFils());
		o.setContexte(tmp.getContexte());

		objectService.save(o, user); // Tester les droits

		return "redirect:list.htm?code="+o.getContexte().getCode();

	}


	@RequestMapping(value = "/editsons.htm", method = RequestMethod.GET)
	public String manageSons() {
		return "tmpObjectCreation/editSons";
	}

	@RequestMapping(value = "/editsons.htm", method = RequestMethod.POST)
	public String saveObjectSons(@ModelAttribute @Valid domain.Object o, BindingResult result) {
		if(result.hasErrors()) {
			return "formation/list.htm";
		}
		//objectService.save(o, user);
		return "formation/list";
	}

	@ModelAttribute("myobject")
	public domain.Object newObject(
			@RequestParam(value = "cobject", required = false) String code) {

		if (code != null) {
			return objectService.findOne(code);
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
	public ModelAndView sortNLObject(@RequestParam(value="cobject",required=true) String code, @Valid @ModelAttribute TypeObject typeobject, BindingResult bindingResult){
		return new ModelAndView("redirect:gestionFils.htm?cobject=" + code + "&typeobject="+typeobject.getCode()) ;
	}

	@ModelAttribute("typesList")
	public List<TypeObject> productTypes() {
		return (List<TypeObject>) typeService.findAll();
	}

	@ModelAttribute("NonLinkedObjectList")
	public List<domain.Object> nlObjectList(@RequestParam(value="typeobject",required=false) String typeobject, @RequestParam(value="cobject",required=true) String cobject) {
		String type;
		String context = objectService.findOne(cobject).getContexte().getCode();
		List<domain.Object> nlObjectList;
		
		if (typeobject == null || typeobject.equals("")){
			nlObjectList = (List<domain.Object>) objectService.objectsNonLiee(context);
			return nlObjectList;
		}
		else{
			type = typeService.findOne(typeobject).getCode(); 
		}
		nlObjectList = (List<domain.Object>) objectService.findTypedNonLinkedObject(context, type);
		return nlObjectList;
	}

	@ModelAttribute("mutualisableObjectList")
	public List<domain.Object> mObjectList(@RequestParam(value="typeobject",required=false) String typeobject, @RequestParam(value="cobject",required=true) String cobject) {
		String type;
		String context = objectService.findOne(cobject).getContexte().getCode();
		List<domain.Object> mObjectList;
		
		if (typeobject == null || typeobject.equals("")){
			mObjectList = (List<domain.Object>) objectService.findMutualisableObjects(context);
			return mObjectList;
		}
		else{
			type = typeService.findOne(typeobject).getCode(); 
		}
		mObjectList = (List<domain.Object>) objectService.findTypedMutualisableObjects(context, type);
		return mObjectList;

	}



	@RequestMapping(value = "/addFils", method = RequestMethod.POST)
	public ModelAndView addFils(@RequestParam(value="cobject",required=true) String cobject, @Valid @ModelAttribute domain.Object selectedFils, BindingResult result) {


		if(result.hasErrors()) {
			System.out.println(result.toString());
			return new ModelAndView("redirect:gestionFils.htm?cobject="+cobject);
		}
		domain.Object o = objectService.findOne(cobject);
		Fils tmpF = new Fils();
		tmpF.setRang(1);
		tmpF.setFils(selectedFils);
		pereFilsService.save(tmpF);
		
		o.getAllFils().add(tmpF);
		objectService.save(o, user);

		return new ModelAndView("redirect:gestionFils.htm?cobject="+cobject + o.getTypeObject().getCode());
	}

	@ModelAttribute("user")
	public User newUser() {
		return user;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(List.class, "typeObject", new CustomCollectionEditor(List.class)
		{
			@Override
			protected Object convertElement(Object element)
			{
				List<TypeObject> retour = new ArrayList<TypeObject>();
				retour.add(typeService.findOne((String) element));
				return retour;
			}
		});
		
//		binder.registerCustomEditor(Collection.class, "NonLinkedObjectList", new CustomCollectionEditor(Collection.class)
//		{
//			@Override
//			protected Object convertElement(Object element)
//			{
//			
//				System.out.println("init ... BENDER !!!");
//				
//				Collection <Fils> retour = new ArrayList<Fils>();
//				Fils f = new Fils();
//				f.setRang(1);
//				f.setFils(objectService.findOne((String)element));
//				retour.add(f);
//				return retour;
//			}
//			
//		});
	}
}