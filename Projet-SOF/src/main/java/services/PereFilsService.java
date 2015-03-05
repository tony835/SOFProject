package services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ObjectDao;
import repositories.PereFilsDao;
import domain.Fils;


@Service
@Transactional
public class PereFilsService {


	@Autowired
	private PereFilsDao pereFilsDao;

	public void save(Fils pereFile) {

		Assert.notNull(pereFile);

		pereFilsDao.save(pereFile);
	}

	
}
