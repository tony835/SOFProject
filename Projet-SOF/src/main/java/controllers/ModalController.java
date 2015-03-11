package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ObjectService;
import domain.FieldObject;


@Controller
@RequestMapping("/modal")
public class ModalController {

	@Autowired
	ObjectService managerObject ;
	public ModalController() {
	}
	
	@RequestMapping(value = "/tt.htm", method = RequestMethod.GET)
	public ModelAndView listFields(
	       /* @RequestParam(value = "obj", required=true) */String codeObject) {
	    
		Collection<FieldObject> fields = managerObject.findOne("obj3").getFieldObjects();
		System.out.println(fields.size());
		
	    return new ModelAndView("protoModal/editFieldObject", "fields", fields);
	}

}