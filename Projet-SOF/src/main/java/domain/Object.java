package domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/*
 Code_objet varchar(255) not null,
 Nom_objet varchar(255),
 FMutualisable boolean,
 Version varchar(255) not null,
 */
@Entity
@Table(name = "Objet")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({ @NamedQuery(name = Object.FIND_ALL, query = "SELECT p FROM Object p"), })
public class Object {

	public static final String FIND_ALL = "FIND_ALL_Object";

	@Id
	@Column(name = "Code_objet")
	private String code;

	@Column(name = "Nom_objet")
	private String name;

	@Column(name = "Version")
	private String version;

	@Column(name = "FMutualisable")
	private boolean mutualisable;
//
//	@OneToMany(mappedBy = "pere")
//	private Set<PereFils> pereFils = new HashSet<PereFils>();
//
//	public Set<PereFils> getPereFils() {
//		return this.pereFils;
//	}
//
//	public void setPereFils(Set<PereFils> pereFils) {
//		this.pereFils = pereFils;
//	}

	@OneToMany(mappedBy = "fils")
	private Set<PereFils> pereFils = new HashSet<PereFils>();

	public Set<PereFils> getPereFils() {
		return this.pereFils;
	}

	public void setPereFils(Set<PereFils> pereFils) {
		this.pereFils = pereFils;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public boolean isMutualisable() {
		return mutualisable;
	}

	public void setMutualisable(boolean mutualisable) {
		this.mutualisable = mutualisable;
	}

}
