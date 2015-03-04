package domain;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component()
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * Statut de l'utilisateur en session.
	 */
	private boolean connected = false;
	/**
	 * Login de l'utilisateur en session.
	 */
	private String login;
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
	 * Permet de vérifier si l'utilisateur est un administrateur.
	 *
	 * @return True si l'utilisateur est un administrateur, false sinon.
	 */
	public boolean isConcepteur() {
		//TODO regarder dans fichier xml
		return false;
	}

	/**
	 * Permet de réinitialiser la session courante.
	 */
	public void flush() {
		connected = false;
		login = "";
		password = "";
	}
}