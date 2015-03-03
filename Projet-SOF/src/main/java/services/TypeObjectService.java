package services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.TypeObjectDao;


@Service
@Transactional
public class TypeObjectService {


	@Autowired
	private TypeObjectDao typeObjectDao;


}
