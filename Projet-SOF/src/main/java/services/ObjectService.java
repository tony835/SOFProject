package services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Formation;
import repositories.ObjectDao;
import repositories.PersonDao;


@Service
@Transactional
public class ObjectService {


	@Autowired
	private ObjectDao objectDao;

	public void save(domain.Object obj) {

		Assert.notNull(obj);

		objectDao.save(obj);
	}

	
}
