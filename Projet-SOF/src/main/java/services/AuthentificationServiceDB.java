package services;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import repositories.PersonDao;
import domain.Person;
import domain.User;

@Service
public class AuthentificationServiceDB {

	/**
	 * L'utilisateur en session.
	 *
	 * @see User
	 */
	@Autowired()
	User user;
	
	@Autowired
	private PersonDao personneRepository;

	@Autowired
	ServletContext context;
	
	public boolean login(User u) {
		String login = u.getLogin();
		String password = u.getPassword();
		Person p = personneRepository.findOne(login);

		if (p == null || !p.getPassword().equals(password)) {
			u.setPassword("");
			return false;
		}
		user.setConnected(true);
		user.setLogin(login);
		user.verifyIfIsConceptor(context);
		return true;
	}

	public void flush() {
		user.flush();
	}

	/**
	 * Permet d'avoir accès à l'utilisateur en session.
	 *
	 * @return @see User L'utilisateur en session.
	 */
	@ModelAttribute("user")
	public User newUser() {
		return user;
	}

}
