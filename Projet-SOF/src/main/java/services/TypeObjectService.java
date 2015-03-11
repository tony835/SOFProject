package services;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.TypeObjectDao;
import domain.TypeObject;


@Service
@Transactional
public class TypeObjectService {


	@Autowired
	private TypeObjectDao typeObjectDao;
	
	public List<TypeObject> findAll(){
		return typeObjectDao.findAll();
	}
	
	public TypeObject findOne(String code){
		return typeObjectDao.findOne(code);
	}
	public void save(TypeObject o){
		typeObjectDao.save(o) ;
	}

	public void delete(TypeObject o) {
		typeObjectDao.delete(o) ;
		
	}

}