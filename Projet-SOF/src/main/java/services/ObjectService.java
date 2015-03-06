package services;



import java.util.Collection;

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
		return objectDao.findObjectnNonLiée(code);
	}
}
