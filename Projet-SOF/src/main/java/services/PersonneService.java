package services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.PersonneRepository;


@Service
@Transactional
public class PersonneService {


	@Autowired
	private PersonneRepository personneRepository;


}
