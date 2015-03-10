package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import services.ObjectService;


@Controller
@RequestMapping("/modal")
public class ModalController {

	@Autowired
	ObjectService managerObject ;
	public ModalController() {
	}
	
	@RequestMapping(value = "/test.htm")
	public String afficheFields() {
		
		domain.Object o = managerObject.findOne("ObjExCode") ;
		System.out.println("object ->>>>>>>>>>>>>>>< " +o);
		System.out.println(o.getTypeObject().getFields().size());
		return "protoModal/modal";
	}

}
