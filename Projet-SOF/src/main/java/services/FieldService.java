package services;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.FieldDao;
import domain.Field;
import domain.TypeObject;


@Service
@Transactional
public class FieldService {


	@Autowired
	private FieldDao fildDao;
	
	public List<Field> findAll(){
		return fildDao.findAll();
	}
	
	public Field findOne(String id){
		return fildDao.findOne(id);
	}
	public void save(Field o){
		fildDao.save(o) ;
	}

	public void delete(Field f2) {
		
		fildDao.delete(f2) ;
	}
	private List<String> listFieldByCode(Element racine, String code) {
		List<String> ids = new ArrayList<String>();
		List<Element> listTypesObject = racine.getChildren("object_type");
		Iterator<Element> i = listTypesObject.iterator();
		while (i.hasNext()) {
			Element courant = (Element) i.next();
			TypeObject typeObject = new TypeObject();
			if(courant.getChild("code").getText().equals(code)){
				List<Element> listField = courant.getChildren("field");
				Iterator<Element> j = listField.iterator();
				typeObject.setFields(new ArrayList<Field>());
				while (j.hasNext()) {
					Element courant2 = (Element) j.next();
					ids.add(courant2.getChild("Id").getText());
				}
			}
		}
		return ids;
	}
	
	public List<String> getListFieldByCode(String code){
		SAXBuilder sxb = new SAXBuilder();
		Document document = null;
		try {
			document = sxb.build(getClass().getResource("/configApp.xml"));
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}

		Element racine = document.getRootElement();
		return listFieldByCode(racine, code);
	}

}