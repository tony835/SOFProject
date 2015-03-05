package services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Formation;
import domain.Fils;
import repositories.ObjectDao;
import repositories.PersonDao;


@Service
@Transactional
public class ObjectService {


	@Autowired
	private ObjectDao objectDao;

	public domain.Object create() {
		return new domain.Object();
	}

	public void save(domain.Object obj) {
		Assert.notNull(obj);
		objectDao.save(obj);
	}
	
	public domain.Object findOne(String code){
		return objectDao.findOne(code);
	}
	
	public void addFils(domain.Object pere, domain.Object fils, Integer rang){
		Fils p = new Fils();
		p.setFils(fils);
		p.setRang(rang);
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
	
}
