
package services;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.PersonDao;
import domain.Person;

@Service
public class PersonService {
	@Autowired
	private PersonDao personneRepository;

	/**
	 * Permet de savoir si la personne connecté est un concepteur ou pas
	 * 
	 * @return true si la personne est un concepteur, false sinon
	 * @throws IOException
	 */
	public boolean isConceptor() throws IOException {
		Person currentP = getConnectedPerson();
		try {
			return isConceptor(currentP.getLogin());
		} catch (JDOMException e) { // Si une erreur de JDOM.
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Permet de savoir si la personne connecté est un concepteur ou pas
	 * 
	 * @param login
	 *            Le login de la personne
	 * @return true si la personne est un concepteur, false sinon
	 * @throws JDOMException
	 *             JDOMExept
	 * @throws IOException
	 *             Except
	 */
	public boolean isConceptor(String login) throws JDOMException, IOException {
		SAXBuilder sxb = new SAXBuilder();
		Document document = sxb.build(new File("src/main/resources/Donnees.xml"));

		// On initialise un nouvel élément racine avec l'élément racine du document.
		Element racine = document.getRootElement();
		List<Element> listEtudiants = racine.getChildren("Concepteur");
		Iterator<Element> i = listEtudiants.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();
			if (courant.getChild("login").getText().equals(login))
				return true;
		}
		return false;
	}

	/**
	 * Récupération de toutes les personnes présentes dans la base de données.
	 * @return la liste des personne présente dans la base des données.
	 */
	public List<Person> findAll(){
		return personneRepository.findAll();
	}
	
	/**
	 * Récupération d'une personne présente dans la base de données sélectionné par son id.
	 * @param id - l'id de la personne à sélectionner.
	 * @return la liste des personne présente dans la base des données.
	 */
	public Person findOne(String login){
		return personneRepository.findOne(login);
	}
	
	/**
	 * Récupération de la personne en cour dans la session.
	 * 
	 * @return la personne dans la session.
	 */
	private Person getConnectedPerson() {
		Person p = new Person();
		p.setFirstName("Lemaire");
		p.setMail("lol@lol.fr");
		p.setLogin("l1000000");
		return p;
	}

	public void save(Person person) {
		personneRepository.save(person);
		
	}
}