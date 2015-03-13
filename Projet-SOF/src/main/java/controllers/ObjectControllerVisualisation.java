package controllers;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.FieldObject;
import domain.Fils;
import domain.Formation;
import domain.Object;
import domain.TypeObject;
import domain.User;
import services.ObjectService;
import services.TypeObjectService;
import services.PersonService;

@Controller
@RequestMapping("/objectVisualisation")
public class ObjectControllerVisualisation  extends AbstractController{

	@Autowired
	ObjectService objectService;

	@Autowired
	TypeObjectService typeService;

	@Autowired
	User user;
	
	@Autowired
	PersonService personService;


	
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView mObjectList(@RequestParam String code) {
		ModelAndView result;
//		System.out.println(personService.isContributorOfObject("FormNouvelle2"));
	    result = new ModelAndView("object/details");
	    Object obj= objectService.findOne(code);
	    result.addObject("object",obj);
	    Map<String, List<FieldObject>> maps= new HashMap<String, List<FieldObject>>();
	    Collection<FieldObject> fIList= obj.getFieldObjects();
	    Collection<FieldObject> fIListGeneral= new ArrayList<FieldObject>();
	    Collection<Object> objectMemeType =  new ArrayList<Object>();
	    if(obj.getTypeObject()!=null && obj.getContexte() !=null){
		    objectMemeType= objectService.objectsSameTypeInContext(obj.getTypeObject().getCode(), obj.getContexte().getCode(),obj.getCode());
		    System.out.println("holaaaaa c la"+ objectMemeType);

	    }
	  
	    for(FieldObject f:fIList ){
	    	if(f.getField().getTabName()==null){
	    		fIListGeneral.add(f);
	    	}else if(maps.containsKey(f.getField().getTabName()))
	    		maps.get(f.getField().getTabName()).add(f);
	    	else {
	    		List<FieldObject> filObjects= new ArrayList<FieldObject>();
	    		filObjects.add(f);
	    		maps.put(f.getField().getTabName(), filObjects);	
	    	}	    	
	    }
	    System.out.println(fIListGeneral);
	    System.out.println(maps);

	    result.addObject("maps",maps);
	    
	    result.addObject("fIListGeneral",fIListGeneral);
	    result.addObject("objectMemeType",objectMemeType);
	    result.addObject("objectMemeTypeSize",objectMemeType.isEmpty());



		return result;
	}

	@ModelAttribute("user")
	public User newUser() {
		return user;
	}

	

}