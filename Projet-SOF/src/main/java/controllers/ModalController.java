package controllers;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import services.FieldObjectService;
import services.ObjectService;
import services.TypeObjectService;
import domain.Field;
import domain.FieldObject;
import domain.FieldObjectId;
import domain.TypeObject;


@Controller
@RequestMapping("/modal")
public class ModalController {

	@Autowired
	ObjectService managerObject ;
	@Autowired
	FieldObjectService managerFieldObject ;
	@Autowired
	TypeObjectService managerTypeObject ;

	public ModalController() {
	}
	
	@Transactional
	@RequestMapping(value = "/tt.htm", method = RequestMethod.GET)
	public ModelAndView listFields(
	        @RequestParam(value = "obj", required=true) String codeObject) {
	    
		Collection<FieldObject> fields = managerObject.findOne(codeObject).getFieldObjects();
		System.out.println(fields.size());
		
	    return new ModelAndView("protoModal/editFieldObject", "fields", fields);
	} 
	@RequestMapping(value = "/ajax.htm", method = RequestMethod.GET)
	public  @ResponseBody String ajax(@RequestParam(value = "codeObjet") String codeObjet,
									  @RequestParam(value = "idField")String idField,
									  @RequestParam(value = "value")String value) {
		
		FieldObject f = managerFieldObject.findOne(new FieldObjectId(idField,codeObjet));
		if(f == null) return null ;
		
		value=HtmlUtils.htmlEscape(value);
		
		Field.TypeContenu type = f.getField().getTypeContenu() ;
		if (type == Field.TypeContenu.INT){
			try{
				Integer.parseInt(value);
			}catch(NumberFormatException e){
				return f.getValue();
			}
		}
		// Verifier également la taille
		f.setValue(value);
		managerFieldObject.save(f);
		return f.getValue() ;
	}

	
}