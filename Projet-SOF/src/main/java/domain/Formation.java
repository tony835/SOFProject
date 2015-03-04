package domain;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Formation")
@NamedQueries({ @NamedQuery(name = Formation.FIND_ALL, query = "SELECT p FROM Formation p"), })
public class Formation extends Object {

	public static final String FIND_ALL = "FIND_ALL_Formation";

	@Column(name = "Error_number")
	int numError;

	@Column(name = "Visible")
	boolean visible;

	@OneToMany(mappedBy="contexte")
	private Collection<Object> objectsContexte;

	@ManyToOne
	@JoinColumn(name="Responsable")
	private Person responsable;
    
	@ManyToMany
    @JoinTable(name="A_pour_contributeur",
        joinColumns=
            @JoinColumn(name="Code_objet", referencedColumnName="Code_objet"),
        inverseJoinColumns=
            @JoinColumn(name="Id_personne", referencedColumnName="Login")
        )
	private Collection<Person> contributeurs;

	public int getNumError() {
		return numError;
	}

	public void setNumError(int numError) {
		this.numError = numError;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}


	public Collection<Object> getObjectsContexte() {
		return objectsContexte;
	}

	public void setObjectsContexte(Collection<Object> objectContexte) {
		this.objectsContexte = objectContexte;
	}

	public Person getResponsable() {
		return responsable;
	}

	public void setResponsable(Person person) {
		this.responsable = person;
	}

	public Collection<Person> getContributeurs() {
		return contributeurs;
	}

	public void setContributeurs(Collection<Person> contributeurs) {
		this.contributeurs = contributeurs;
	}


}
