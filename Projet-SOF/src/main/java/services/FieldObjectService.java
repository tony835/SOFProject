package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Field;
import domain.FieldObject;
import domain.FieldObjectId;
import repositories.FieldObjectDao;

@Service
@Transactional
public class FieldObjectService {

	@Autowired
	public FieldObjectDao dao ;
	
	
	public FieldObject findOne(FieldObjectId id){
		return dao.findOne(id);
	}
	public void save(FieldObject o){
		dao.save(o) ;
	}

	public void delete(FieldObject f2) {
		
		dao.delete(f2) ;
	}
}
