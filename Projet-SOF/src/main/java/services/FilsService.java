package services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ObjectDao;
import repositories.FilsDao;
import domain.Fils;


@Service
@Transactional
public class FilsService {


	@Autowired
	private FilsDao FilsDao;

	public void save(Fils fils) {

		Assert.notNull(fils);

		FilsDao.save(fils);
	}

	
}
