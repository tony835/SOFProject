package services;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jdom2.JDOMException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FilsDao;
import repositories.ObjectDao;
import domain.Fils;
import domain.Formation;
import domain.Object;
import domain.TypeObject;
import domain.User;


@Service
@Transactional
public class ObjectService {


	@Autowired
	private ObjectDao objectDao;

	@Autowired
	private FormationService formationService;

	@Autowired
	private FilsDao filsDao;

	@Autowired
	private User user;

	public domain.Object create() {
		return new domain.Object();
	}

	public boolean save(domain.Object obj, User user) {
		//		if(!user.getLogin().equals(obj.getContexte().getResponsable()))
		//			return false;
		System.out.println("sauvegarde ...");
		Assert.notNull(obj);
		objectDao.save(obj);
		return true;
	}

	public void save(domain.Object obj) {
		Assert.notNull(obj);
		objectDao.save(obj);
	}

	public domain.Object findOne(String code){

		return objectDao.findOne(code);
	}

	public void addLinkFils(domain.Object pere, domain.Object fils, Integer rang){
		Fils p = new Fils();
		p.setFils(fils);
		p.setRang(rang);
		filsDao.save(p);
		pere.getAllFils().add(p);
		objectDao.save(pere);
	}

	public boolean isContributor(String code){
		try{
			return formationService.isContributor(code);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public void delLienFils(domain.Object pere, domain.Object fils){
		try {
			if(user.isConceptor() || isContributor(pere.getContexte().getCode())){
				Fils tmp = null;

				for (Fils p : pere.getAllFils()){
					if(p.getFils().getCode().equals(fils.getCode()))
						tmp = p;
				}
				if(tmp != null){
					pere.getAllFils().remove(tmp);			
					objectDao.save(pere);
					filsDao.delete(tmp);
				}
			}
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
	}

	public Collection<Object> objectsNonLiee(String code){
		return objectDao.findNonLinkedObject(code);
	}

	public Collection<Object> findTypedNonLinkedObject(String code, String type){
		return objectDao.findTypedNonLinkedObject(code, type);
	}

	public Collection<Object> findMutualisableObjects(String code){
		return objectDao.findOtherMutualisableObject(code);
	}

	public Collection<Object> findTypedMutualisableObjects(String code, String type){
		return objectDao.findOtherTypesMutualisableObject(code, type);
	}

	public List<Fils> getChild(String code) {
		domain.Object obj = findOne(code);
		List<Fils> list = new ArrayList<Fils>();
		if(obj == null)
			return list;
		for (Fils fils : obj.getAllFils()){
			boolean passe = false;
			if(list.size() == 0){
				list.add(fils);
				continue;
			}
			for (int i = 0; i < list.size(); i++){
				System.out.println(fils.getRang() +"<=?"+list.get(i));
				if(fils.getRang() <= list.get(i).getRang()){
					list.add(i, fils);
					passe = true;
					break;
				}
			}
			if(!passe){
				list.add(list.size(), fils);
			}
		}
		return list;
	}

	public List<Fils> Object2Fils(List<domain.Object> nlObjectList) {
		List <Fils> nlFilsList = new ArrayList<Fils>();
		for (int i = 0; i < nlObjectList.size(); i++){
			Fils tmpF = new Fils();
			tmpF.setRang(1);
			tmpF.setFils(nlObjectList.get(i));
			nlFilsList.add(tmpF);
		}
		return nlFilsList;		
	}
	public Collection<Object> objectsSameTypeInContext(String codeTypeObject, String codecontext,String code ){
		return objectDao.findOtheObjectSameTypeInContext(codeTypeObject, codecontext,code);
	}

	public String checkContentModel(domain.Object o) {
		TypeObject to = o.getTypeObject();
		if(to == null)
			return "";
		String expectedSons = to.getModelContenu();
		
		// si pas de modèle de contenu, on considère qu'il n'y a pas d'erreurs
		// c'est ce que Massat voulait
		if(expectedSons == null || expectedSons.equals("")) {
			return "";
		}
		
		
		String actualSons ="";

		for (Fils f : o.getAllFils()){
			if(f.getFils() != null	&& f.getFils().getTypeObject() != null
					&& f.getFils().getTypeObject().getCode() != null){
				actualSons += f.getFils().getTypeObject().getCode() + "_";
			}
		}

		if(actualSons.matches(expectedSons)){
			return "";
		}

		String descError = to.getDescError();
		if (descError == null || descError.equals("")){
			return "erreur non repertorie";
		}
		return descError;
	}

	public void getDescendants(List<Object> loContext, domain.Object o) {
		for (Fils f : o.getAllFils()){
			domain.Object tmpO = f.getFils();
			loContext.add(f.getFils());
			getDescendants(loContext, tmpO);
		}
	}
	
	public Collection<String> isContributorOfObject(String login, String object)
	{
		return objectDao.isContributorOfObject(login, object);
	}
}
