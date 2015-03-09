package services;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.FieldDao;
import domain.Field;


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


}