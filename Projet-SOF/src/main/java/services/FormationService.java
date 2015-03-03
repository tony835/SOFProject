package services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.FormationDao;
import repositories.PersonDao;


@Service
@Transactional
public class FormationService {


	@Autowired
	private FormationDao formationDao;


}
