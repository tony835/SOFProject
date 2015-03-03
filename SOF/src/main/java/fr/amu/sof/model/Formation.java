package fr.amu.sof.model;


import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="Formation")
@NamedQueries({
    @NamedQuery(name = Formation.FIND_ALL, query = "SELECT p FROM Formation p"),
})
public class Formation extends Object{
	
	public static final String FIND_ALL = "FIND_ALL_Formation";
	
	@Column(name="Error_number")
	int numError;
	
	@Column(name="Visible")
	boolean visible;
	
	@ManyToOne
	private Object object;
	
	@ManyToOne
	private Person person;
	
	@ManyToMany
	@JoinTable(name="Contribution", joinColumns=
	@JoinColumn(name="CODE_FORMATION"), inverseJoinColumns= @JoinColumn(name="LOGIN"))
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

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Collection<Person> getContributeurs() {
		return contributeurs;
	}

	public void setContributeurs(Collection<Person> contributeurs) {
		this.contributeurs = contributeurs;
	}

	public Formation(int numError, boolean visible, Object object,
			Person person, Collection<Person> contributeurs) {
		super();
		this.numError = numError;
		this.visible = visible;
		this.object = object;
		this.person = person;
		this.contributeurs = contributeurs;
	}
	
}
