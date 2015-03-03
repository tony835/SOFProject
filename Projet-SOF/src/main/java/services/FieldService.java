package services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.FieldDao;
import repositories.PersonDao;


@Service
@Transactional
public class FieldService {


	@Autowired
	private FieldDao fildDao;


}
