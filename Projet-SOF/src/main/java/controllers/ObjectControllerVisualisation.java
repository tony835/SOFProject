package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.bytecode.ClassFileWriter.FieldWriter;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
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
import services.FieldService;

@Controller
@RequestMapping("/objectVisualisation")
public class ObjectControllerVisualisation extends AbstractController {

	@Autowired
	ObjectService objectService;

	@Autowired
	TypeObjectService typeService;

	@Autowired
	User user;

	@Autowired
	PersonService personService;

	@Autowired
	FieldService fieldService;

	List<String> navigation = new ArrayList<String>();

	@Transactional
	@RequestMapping(value = "/audit/details", method = RequestMethod.GET)
	public ModelAndView mObjectList(
			@RequestParam String code,
			@RequestHeader(value = "referer", required = false) final String referer) {
		ModelAndView result;
		
		
		if (referer == null) {
			navigation = new ArrayList<String>();
		} else if (!referer.contains("objectVisualisation/details"))
			navigation = new ArrayList<String>();

		Boolean audit = true;
		Boolean isContributor;
		if (!objectService.isContributorOfObject(user.getLogin(), code)
				.isEmpty()) {
			isContributor = true;
		} else {
			isContributor = false;
		}

		result = new ModelAndView("object/details");
		Object obj = objectService.findOne(code);
		obj.getAllFils().size(); // Pour initialiser la liste des fils
		result.addObject("object", obj);
		fieldService.getListField(obj);
		Map<String, List<FieldObject>> maps = new HashMap<String, List<FieldObject>>();
		Collection<FieldObject> fIList = obj.getFieldObjects();
		Collection<FieldObject> fIListGeneral = new ArrayList<FieldObject>();
		Collection<Object> objectMemeType = new ArrayList<Object>();
		if (obj.getTypeObject() != null && obj.getContexte() != null) {
			objectMemeType = objectService.objectsSameTypeInContext(obj
					.getTypeObject().getCode(), obj.getContexte().getCode(),
					obj.getCode());

		}
		List<String> fieldInParam = new ArrayList<String>();
		fieldInParam = fieldService.getListFieldByCode(obj.getTypeObject()
				.getCode());

		for (FieldObject f : fIList) {
			if (fieldInParam.contains(f.getField().getId())) {

				if (f.getField().getTabName() == null || f.getField().getTabName().isEmpty()) {
					fIListGeneral.add(f);
				} else if (maps.containsKey(f.getField().getTabName())) {
					maps.get(f.getField().getTabName()).add(f);
				} else {
					List<FieldObject> filObjects = new ArrayList<FieldObject>();
					filObjects.add(f);
					maps.put(f.getField().getTabName(), filObjects);
				}
			}
		}
		if (navigation.contains("objectVisualisation/audit/details.htm?code="
				+ code)) {
			navigation.remove("objectVisualisation/audit/details.htm?code="
					+ code);
		}
		navigation.add("objectVisualisation/audit/details.htm?code=" + code);

		result.addObject("maps", maps);
		result.addObject("Audit", audit);
		result.addObject("Contributor", isContributor);
		result.addObject("fIListGeneral", fIListGeneral);
		result.addObject("objectMemeType", objectMemeType);
		result.addObject("objectMemeTypeSize", objectMemeType.isEmpty());
		return result;

	}

	@Transactional
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView mObjectListVisitor(
			@RequestParam String code,
			@RequestHeader(value = "referer", required = false) final String referer) {
		ModelAndView result;
		List<String> fieldInParam = new ArrayList<String>();

		if (referer == null) {
			navigation = new ArrayList<String>();
		} else if (!referer.contains("objectVisualisation/details"))
			navigation = new ArrayList<String>();

		Boolean audit = false;

		result = new ModelAndView("object/details");
		Object obj = objectService.findOne(code);
		int version = obj.getVersion();
		fieldService.getListField(obj);
		System.out.println(version);
		obj.getAllFils().size(); // Pour initialiser la liste des fils
		result.addObject("object", obj);
		Map<String, List<FieldObject>> maps = new HashMap<String, List<FieldObject>>();
		Collection<FieldObject> fIList = obj.getFieldObjects();
		Collection<FieldObject> fIListGeneral = new ArrayList<FieldObject>();
		Collection<Object> objectMemeType = new ArrayList<Object>();
		if (obj.getTypeObject() != null && obj.getContexte() != null) {
			objectMemeType = objectService.objectsSameTypeInContext(obj
					.getTypeObject().getCode(), obj.getContexte().getCode(),
					obj.getCode());

		}
		fieldInParam = fieldService.getListFieldByCode(obj.getTypeObject()
				.getCode());
		for (FieldObject f : fIList) {
			if (fieldInParam.contains(f.getField().getId())) {

				if (f.getField().getTabName() == null || f.getField().getTabName().isEmpty()) {
					fIListGeneral.add(f);
				} else if (maps.containsKey(f.getField().getTabName())) {
					maps.get(f.getField().getTabName()).add(f);
				} else {
					List<FieldObject> filObjects = new ArrayList<FieldObject>();
					filObjects.add(f);
					maps.put(f.getField().getTabName(), filObjects);
				}
			}
		}
		if (navigation.contains("objectVisualisation/details.htm?code=" + code)) {
			navigation.remove("objectVisualisation/details.htm?code=" + code);
		}
		navigation.add("objectVisualisation/details.htm?code=" + code);
		
		result.addObject("maps", maps);
		result.addObject("Audit", audit);
		result.addObject("fIListGeneral", fIListGeneral);
		result.addObject("objectMemeType", objectMemeType);
		result.addObject("objectMemeTypeSize", objectMemeType.isEmpty());
		return result;

	}

	@ModelAttribute("navigation")
	public List<String> navigations() {
		return navigation;
	}

	@ModelAttribute("user")
	public User newUser() {
		return user;
	}

}
