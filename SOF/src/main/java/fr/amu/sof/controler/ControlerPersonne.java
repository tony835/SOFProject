package fr.amu.sof.controler;

import java.util.Collection;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.amu.sof.manager.IService;
import fr.amu.sof.model.Formation;
import fr.amu.sof.model.Person;
import fr.amu.sof.model.TypeObject;

@Controller()
@RequestMapping("/test")
public class ControlerPersonne {

    
    @Resource(name="PersonneManager")
    IService personManager;
    
    @Resource(name="ServiceObject")
    IService objectService;

    @Resource(name="SerciceFormation")
    IService formationManager;
    
    @Resource(name="ServiceTypeObject")
    IService typeObjectManager;

    @Resource(name="ServiceField")
    IService serviceField;
    
    protected final Log logger = LogFactory.getLog(getClass());

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView listProducts() {
        logger.info("List of products");
        Collection<Person> persons = personManager.findAll();
        Collection<Object> objects = objectService.findAll();
        Collection<Formation> formations = formationManager.findAll();
        Collection<TypeObject> typeObjects = typeObjectManager.findAll();
        Collection<TypeObject> fields = serviceField.findAll();
        
        ModelAndView m =  new ModelAndView("personList", "persons", persons);
        m.addObject("objects", objects);
        m.addObject("formations", formations);
        m.addObject("typeObjects", typeObjects);
        m.addObject("fields", fields);
        return m ;
    }
}