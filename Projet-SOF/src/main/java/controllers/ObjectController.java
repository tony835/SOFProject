package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import domain.FieldObject;
import domain.Fils;
import domain.TypeObject;
import domain.Object;
import domain.User;
import services.ObjectService;
import services.TypeObjectService;

@Controller
@RequestMapping("/object")
public class ObjectController {

	@Autowired
	ObjectService objectService;

	@Autowired
	TypeObjectService typeService;

	@Autowired
	User user;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create() {
		return "tmpObjectCreation/createObject";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String saveNewObject(@ModelAttribute @Valid Object o, BindingResult result) {
		if(result.hasErrors()) {
			return "formation/list.htm";
		}
		
		objectService.save(o, user);
		
		return "formation/list.htm";

	}

	@ModelAttribute("object")
	public Object newPerson(
			@RequestParam(value = "code", required = false) String code) {
		if (code != null) {
			return objectService.findOne(code);
		}
		Object o = new Object();
		o.setCode("");
		o.setName("");
		o.setVersion("");
		o.setMutualisable(false);
		o.setContexte(null);
		o.setTypeObject(null);
		o.setAllFils(new ArrayList<Fils>());
		o.setFieldObjects(new ArrayList<FieldObject>());

		return o;
	}

	@ModelAttribute("typesList")
	public List<TypeObject> productTypes() {
		return (List<TypeObject>) typeService.findAll();
	}

	@ModelAttribute("user")
	public User newUser() {
		return user;
	}

}