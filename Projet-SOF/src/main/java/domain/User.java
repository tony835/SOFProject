package domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component()
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean conceptor;
	/**
	 * Statut de l'utilisateur en session.
	 */
	private boolean connected = false;
	/**
	 * Login de l'utilisateur en session.
	 */
	private String login;
	private String name;
	private String firstName;
	/**
	 * Mot de passe de l'utilisateur en session.
	 */
	private String password;


	/**
	 * Retourne le login de l'utilisateur en session.
	 *
	 * @return Login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Fixe le login de l'utilisateur en session.
	 *
	 * @param login
	 *            Login de l'utilisateur.
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Permet de vérifier le statut de l'utilisateur en session.
	 *
	 * @return True si l'utilisateur est connecté, false sinon.
	 */
	public boolean isConnected() {
		return connected;
	}

	/**
	 * Fixe le statut de l'utilisateur en session.
	 *
	 * @param connected
	 *            Valeur du statut.
	 */
	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	/**
	 * Retourne le mot de passe de l'utilisateur en session.
	 *
	 * @return Mot de passe
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Fixe le mot de passe de l'utilisateur en session.
	 *
	 * @param password
	 *            Mot de passe de l'utilisateur.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Permet de réinitialiser la session courante.
	 */
	public void flush() {
		connected = false;
		login = "";
		name = "";
		firstName = "";
		password = "";
		conceptor = false;
	}

	public void setConceptor(boolean conceptor) {
		this.conceptor = conceptor;
	}

	public boolean isConceptor() throws JDOMException, IOException {
		return conceptor;
	}

	public void verifyIfIsConceptor(ServletContext context) {
		SAXBuilder sxb = new SAXBuilder();

		Document document = null;
		try {
			document = sxb.build(getClass().getResource("/configApp.xml"));

		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}

		// On initialise un nouvel élément racine avec l'élément racine du document.
		Element racine = document.getRootElement();
		Element designers = racine.getChild("designer");
		List<Element> listDesigners = designers.getChildren("login");

		
		for (Element designer : listDesigners){
			if (designer.getText().equals(login)){
				conceptor = true;
				break;
			}
		}
	}
	
	public boolean isContributor(Formation formation){
		for(Person p : formation.getContributeurs()){
			if(p.getLogin().equals(login)) return true;
		}
		return false;
	}
	
	public boolean isResponsable(Formation formation){
		return formation.getResponsable().getLogin().equals(login);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getFullName(){
		String fullName = "";
		fullName += firstName + " " + name;
		return fullName;
	}
}