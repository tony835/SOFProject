package domain;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="Personne")
@NamedQueries({
    @NamedQuery(name = Person.FIND_ALL, query = "SELECT p FROM Person p"),
})
public class Person {

	public static final String FIND_ALL = "FIND_ALL_Person";

	@Id
	@Column(name="Login")
	private String login ;
	
	@Column(name="Password")
	private String password ;
	
	@Column(name="Nom")
	private String name ;
	
	@Column(name="Prenom")
	private String firstName;
	
	@Column(name="Mail")
	private String mail ;
	
	@OneToMany(mappedBy="responsable")
	private Collection<Formation> responsableDesFormations;
	
	@ManyToMany(mappedBy="contributeurs")
	private Collection<Formation> contributeurDesFormations;
		
	
	public Person() {
	}
	
	public Person(String login, String password, String name, String firstName,
			String mail) {
		super();
		this.login = login;
		this.password = password;
		this.name = name;
		this.firstName = firstName;
		this.mail = mail;
	}

	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}

	public Collection<Formation> getResponsableDesFormations() {
		return responsableDesFormations;
	}

	public void setResponsableDesFormations(
			Collection<Formation> responsableDesFormations) {
		this.responsableDesFormations = responsableDesFormations;
	}

	public Collection<Formation> getContributeurDesFormations() {
		return contributeurDesFormations;
	}

	public void setContributeurDesFormations(
			Collection<Formation> contributeurDesFormations) {
		this.contributeurDesFormations = contributeurDesFormations;
	}


}