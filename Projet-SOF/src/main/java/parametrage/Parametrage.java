package parametrage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServlet;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.FieldService;
import services.PersonService;
import services.TypeObjectService;
import domain.Field;
import domain.Person;
import domain.TypeObject;
import domain.Field.TypeContenu;

public class Parametrage {

	@Autowired
	FieldService fieldManager ;
	@Autowired
	PersonService persoManager ;
	@Autowired
	TypeObjectService typeObjectService ;

	private List<Field> getALLFields(Element racine) {
		List<Element> lstTypeObject = racine.getChildren("object_type");
		Iterator<Element> i = lstTypeObject.iterator();
		List<Field> fields = new ArrayList<Field>();
		while (i.hasNext()) {
			Element courant = (Element) i.next();
			List<Element> listField = courant.getChildren("field");
			Iterator<Element> j = listField.iterator();
			while (j.hasNext()) {
				Element courant2 = (Element) j.next();
				Field f = new Field();
				f.setId(courant2.getChild("Id").getText());
				f.setName(courant2.getChild("name").getText());
				f.setDescription(courant2.getChild("description").getText());
				f.setTypeContenu(TypeContenu.fromSring(courant2.getChild(
						"content_type").getText()));
				f.setRequired(courant2.getChild("required").getText()
						.equals("true") ? true : false);
				fields.add(f);
			}

		}
		return fields;
	}

	private List<TypeObject> getAllObjectTypeANDFieldFromXML(Element racine) {
		List<Element> listTypesObject = racine.getChildren("object_type");
		Iterator<Element> i = listTypesObject.iterator();
		List<TypeObject> ret = new ArrayList<TypeObject>();
		while (i.hasNext()) {
			Element courant = (Element) i.next();
			TypeObject typeObject = new TypeObject();

			typeObject.setCode(courant.getChild("code").getText());
			typeObject.setName(courant.getChild("name").getText());
			typeObject.setModelContenu(courant.getChild("content_model")
					.getText());
			typeObject.setDescError(courant.getChild("Erreur_descritpion").getText());
			// ///////// Coderegex ??????????????????????????????
			List<Element> listField = courant.getChildren("field");

			Iterator<Element> j = listField.iterator();
			typeObject.setFields(new ArrayList<Field>());
			while (j.hasNext()) {
				Element courant2 = (Element) j.next();
				Field f = new Field();
				f.setId(courant2.getChild("Id").getText());
				f.setName(courant2.getChild("name").getText());
				f.setDescription(courant2.getChild("description").getText());
				f.setTypeContenu(TypeContenu.fromSring(courant2.getChild(
						"content_type").getText()));
				boolean required = courant2.getChild("required").getText().equals("true") ? true : false ;
				f.setRequired(required);
				int length;
				try{
					length = Integer.parseInt(courant2.getChild("length").getText()) ;
				}catch(NumberFormatException e){
					length = 0;
				}
				f.setLength(length);//
				f.setTabName(courant2.getChild("tab_name").getText());
				typeObject.getFields().add(f);
			}
			ret.add(typeObject);
		}
		return ret;
	}

	private List<Person> getAllDesigner(Element racine) {
		List<Element> listDesigner = racine.getChild("designer").getChildren(
				"login");

		Iterator<Element> i = listDesigner.iterator();
		List<Person> ret = new ArrayList<Person>();

		while (i.hasNext()) {
			Element courant = (Element) i.next();
			Person p = new Person();
			p.setLogin(courant.getValue());
			ret.add(p);

		}
		return ret;
	}
	@PostConstruct
	public void init() {
		System.out.println("Lancemant du parametrage de l'application");
	}
	@PreDestroy
	public void fin() {
		System.out.println("Fin du parametrage de l'application");
	}


	public void start() {
		SAXBuilder sxb = new SAXBuilder();
		Document document = null;
		try {



			document = sxb.build(getClass().getResource("/configApp.xml"));
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}

		Element racine = document.getRootElement();
		List <TypeObject> l= new Parametrage().getAllObjectTypeANDFieldFromXML(racine);
		for(Iterator iterTypeObject = l.iterator() ; iterTypeObject.hasNext(); ){
			TypeObject typeObject = (TypeObject) iterTypeObject.next() ;
			Field [] fields = typeObject.getFields().toArray(new Field[typeObject.getFields().size()]) ;
			typeObject.setFields(null);
			TypeObject typeObjectFromDB = typeObjectService.findOne(typeObject.getCode());
			if (typeObjectFromDB == null ){
				System.out.println("le type d'objet : " + typeObject + " a ete mis à jour ->  insert");
				typeObjectService.save(typeObject) ;
			}
			else if(!typeObjectFromDB.equals(typeObject)){
				System.out.println("le type d'objet : " + typeObject + " a ete mis à jour -> update");
				typeObjectService.save(typeObject) ;

			}
			for(int i =0;i<fields.length;++i){
				fields[i].setTypeObject(typeObject);
				Field f2 = fieldManager.findOne( fields[i].getId()) ;
				if(f2 == null){
					System.out.println("le champs : " + fields[i] + " a ete mis à jour ->  insert");
					fieldManager.save(fields[i]);
				}
				else if(!f2.equals(fields[i])){ //le champs à été mis à jour(seul l'ID est encore )
					System.out.println("le champs : " + fields[i] + " a ete mis à jour -> update");
					fieldManager.save(fields[i]);
				}
			}
		}
		//		List <Person> l2= getAllDesigner(racine);
		//		for(int i=0;i<l2.size();++i){
		//			Person p = persoManager.findOne(l2.get(i).getLogin()) ;
		//			if(p == null){
		//				System.out.println("le concepteur : "+  l2.get(i).getLogin() + " a ete ajoute -> insert");
		//				persoManager.save(l2.get(i));
		//			}
		//		}
		//		

		////////////////////////////////////////
		List <TypeObject> typeObjectsDB = typeObjectService.findAll();
		for(int i =0 ; i< typeObjectsDB.size();++i){
			int j = 0 ;
			for(;j<l.size();++j){
				if(l.get(j).equals(typeObjectsDB.get(i))){
					break ;
				}
			}
			if(j == l.size()){
				System.out.println("!!!!Attention le type d'objet " + typeObjectsDB.get(i) + " est present dans la base et non définit dans le fichier XML");
			}
		}

		////////////////////////
		List <Field> fieldsDB = fieldManager.findAll();
		List l3 = getALLFields(racine);
		for(int i =0 ; i< fieldsDB.size();++i){
			int j = 0 ;
			for(;j<l3.size();++j){
				if(l3.get(j).equals(fieldsDB.get(i))){
					break ;
				}
			}
			if(j == l3.size()){
				System.out.println("!!!!Attention le champs " + fieldsDB.get(i) + " est present dans la base et non définit dans le fichier XML");
			}
		}

	}
}