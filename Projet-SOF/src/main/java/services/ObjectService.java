package services;



import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FilsDao;
import repositories.ObjectDao;
import domain.Fils;
import domain.Object;
import domain.User;


@Service
@Transactional
public class ObjectService {


	@Autowired
	private ObjectDao objectDao;

	@Autowired
	private FilsDao filsDao;
	
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

	public void addLinkFils(domain.Object pere, domain.Object fils, Integer rang){
		Fils p = new Fils();
		p.setFils(fils);
		p.setRang(rang);
		filsDao.save(p);
		pere.getAllFils().add(p);
		objectDao.save(pere);
	}

	public void delLienFils(domain.Object pere, domain.Object fils){
		Fils tmp = null;
		for (Fils p : pere.getAllFils()){
			if(p.getFils().getCode().equals(fils.getCode()))
				tmp = p;
		}
		if(tmp != null){
			pere.getAllFils().remove(tmp);
			objectDao.save(pere);
		}
	}
	
	public Collection<Object> objectsNonLiee(String code){
		return objectDao.findNonLinkedObject(code);
	}
	
	public Collection<Object> findMutualisableObjects(String code){
		return objectDao.findOtherMutualisableObject(code);
	}
	
	public List<Fils> getChild(String code) {
		domain.Object obj = findOne(code);
		List<Fils> list = new ArrayList<Fils>();
		System.out.println(obj.getAllFils());
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
}
