//package controllers;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.propertyeditors.CustomCollectionEditor;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.InitBinder;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import domain.FieldObject;
//import domain.Fils;
//import domain.Formation;
//import domain.TypeObject;
//import domain.User;
//import services.ObjectService;
//import services.TypeObjectService;
//
//@Controller
//@RequestMapping("/object")
//public class ObjectController {
//
//	@Autowired
//	ObjectService objectService;
//
//	@Autowired
//	TypeObjectService typeService;
//
//	@Autowired
//	User user;
//
//
//	@RequestMapping(value = "/create.htm", method = RequestMethod.GET)
//	public String create() {
//		return "tmpObjectCreation/createObject";
//	}
//
//	@RequestMapping(value = "/create.htm", method = RequestMethod.POST)
//	public String saveNewObject(@ModelAttribute @Valid domain.Object o, BindingResult result) {
//		if(result.hasErrors()) {
//			return "create.htm?";
//		}
//
//		objectService.save(o, user);
//
//		return "formation/list";
//
//	}
//	
//	@RequestMapping(value = "/edit.htm", method = RequestMethod.GET)
//	public String edit() {
//		return "tmpObjectCreation/editObject";
//	}
//
//	@RequestMapping(value = "/edit.htm", method = RequestMethod.POST)
//	public String saveObject(@ModelAttribute @Valid domain.Object o, BindingResult result) {
//		if(result.hasErrors()) {
//			return "formation/list.htm";
//		}
//		objectService.save(o, user);
//		return "formation/list";
//	}
//	
//	@RequestMapping(value = "/editsons.htm", method = RequestMethod.GET)
//	public String manageSons() {
//		return "tmpObjectCreation/editSons";
//	}
//
//	@RequestMapping(value = "/editsons.htm", method = RequestMethod.POST)
//	public String saveObjectSons(@ModelAttribute @Valid domain.Object o, BindingResult result) {
//		if(result.hasErrors()) {
//			return "formation/list.htm";
//		}
//		//objectService.save(o, user);
//		return "formation/list";
//	}
//
//	@ModelAttribute("myobject")
//	public domain.Object newObject(
//			@RequestParam(value = "code", required = false) String code) {
//				
//		if (code != null) {
//			return objectService.findOne(code);
//		}
//		domain.Object o = new domain.Object();
//		o.setCode("");
//		o.setName("");
//		o.setVersion("");
//		o.setMutualisable(false);
//		o.setContexte(null);
//		o.setTypeObject(new TypeObject());
//		o.setAllFils(new ArrayList<Fils>());
//		o.setFieldObjects(new ArrayList<FieldObject>());
//
//		return o;
//	}
//
//	@ModelAttribute("typesList")
//	public List<TypeObject> productTypes() {
//		return (List<TypeObject>) typeService.findAll();
//	}
//	
//	@ModelAttribute("NonLinkedObjectList")
//	public List<domain.Object> nlObjectList() {
//		return (List<domain.Object>) objectService.objectsNonLiee(null);
//	}
//	
//	@ModelAttribute("mutualisableObjectList")
//	public List<domain.Object> mObjectList() {
//		return (List<domain.Object>) objectService.findMutualisableObjects(null);
//	}
//
//	@ModelAttribute("user")
//	public User newUser() {
//		return user;
//	}
//
//	@InitBinder
//	protected void initBinder(WebDataBinder binder) {
//		binder.registerCustomEditor(List.class, "typeObject", new CustomCollectionEditor(List.class)
//		{
//			@Override
//			protected Object convertElement(Object element)
//			{
//				List<TypeObject> retour = new ArrayList<TypeObject>();
//				retour.add(typeService.findOne((String) element));
//				return retour;
//			}
//		});
//	}
//
//}