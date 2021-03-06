package services;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

	public boolean addLinkFils(domain.Object pere, domain.Object fils, Integer rang){
		
		List<domain.Object> lDesc = new ArrayList<domain.Object>();
		lDesc.add(fils);
		getDescendants(lDesc,fils);
		
		if(!lDesc.contains(pere)){
			Fils p = new Fils();
			p.setFils(fils);
			p.setRang(rang);
			filsDao.save(p);
			pere.getAllFils().add(p);
			objectDao.save(pere);
			return true;
		}
		return false;
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
			if(user.isResponsable(pere.getContexte())){
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Collection<Object> objectsNonLiee(String code){
		return objectDao.findNonLinkedObject(code);
	}

	public Collection<Object> findNonFLinkedObject(String contextCode, String fCode){
		return objectDao.findNonFLinkedObject(contextCode, fCode);
	}

	
	public Collection<Object> findTypedNonLinkedObject(String code, String type, String cobject){
		return objectDao.findTypedNonLinkedObject(code, type, cobject);
	}

	public Collection<Object> findMutualisableObjects(String code, String cobject){
		return objectDao.findOtherMutualisableObject(code, cobject);
	}

	public Collection<Object> findTypedMutualisableObjects(String code, String type, String cobject){
		return objectDao.findOtherTypesMutualisableObject(code, type, cobject);
	}

	public List<Fils> getChild(String code) {
		domain.Object obj = findOne(code);
		
		List<Fils> list = new ArrayList<Fils>();
	
		if(obj == null)
			return list;
		
		list.addAll(obj.getAllFils());
		
//		for (Fils fils : obj.getAllFils()){
//			boolean passe = false;
//			if(list.size() == 0){
//				list.add(fils);
//				continue;
//			}
//			for (int i = 0; i < list.size(); i++){
//				if(fils.getRang() <= list.get(i).getRang()){
//					list.add(i, fils);
//					passe = true;
//					break;
//				}
//			}
//			if(!passe){
//				list.add(list.size(), fils);
//			}
			
//		}
		 Collections.sort(list);
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

		String actualSons ="";

		for (Fils f : o.getAllFils()){
			if(f.getFils() != null	&& f.getFils().getTypeObject() != null
					&& f.getFils().getTypeObject().getCode() != null){
				actualSons += f.getFils().getTypeObject().getCode() + "_";
			}
		}

		if(expectedSons != null && actualSons.matches(expectedSons)){
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
	
	public boolean canBeDeleted(domain.Object pere, domain.Object fils){
		if(fils.getAllFils().size() == 0) return true;
		if (fils.getContexte().getCode().equals(pere.getContexte().getCode())) {
			if(objectDao.countNbFathers(fils.getCode()) > 1)
				return true;
		}else
			return true;
		return false;
	}

	public Collection<domain.Object> objectsNonLieeM(Formation formation, String code) {
		List<domain.Object> lomContext = new ArrayList<domain.Object>();
		lomContext.add(formation);
		getDescendants(lomContext, formation);
		return objectDao.objectsNonLieeM(lomContext, code);
	}
}
