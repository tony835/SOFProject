package domain;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;



@Entity
@Table(name = "Objet")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({ @NamedQuery(name = Object.FIND_ALL, query = "SELECT p FROM Object p"), })
public class Object {

	public static final String FIND_ALL = "FIND_ALL_Object";

	@Id
	@Column(name = "Code_objet",nullable=false)
	private String code;

	@Size(min=1)
	@Column(name = "Nom_objet")
	private String name;
	
	@Column(name = "Version")
	private String version;


	@Column(name = "FMutualisable")
	private Boolean mutualisable;

	@ManyToOne
	@JoinColumn(name = "contexte", referencedColumnName = "Code_objet")
	private Formation contexte;

	@ManyToOne
	@JoinColumn(name = "Code_type", referencedColumnName = "Code_type")
	private TypeObject typeObject;

	@OneToMany(mappedBy = "object",fetch = FetchType.EAGER)
	private Collection<FieldObject> fieldObjects;
	 
	//@OneToMany(fetch = FetchType.EAGER)
	@OneToMany(fetch = FetchType.EAGER)
	private Collection<Fils> allFils;


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

	public boolean isMutualisable() {
		return mutualisable;
	}

	public void setMutualisable(boolean mutualisable) {
		this.mutualisable = mutualisable;
	}

	public Formation getContexte() {
		return contexte;
	}

	public void setContexte(Formation contexte) {
		this.contexte = contexte;
	}

	public TypeObject getTypeObject() {
		return typeObject;
	}

	public void setTypeObject(TypeObject typeObject) {
		this.typeObject = typeObject;
	}

	public Collection<Fils> getAllFils() {
		return allFils;
	}

	public void setAllFils(Collection<Fils> allFils) {
		this.allFils = allFils;
	}

	public Collection<FieldObject> getFieldObjects() {
		return fieldObjects;
	}

	public void setFieldObjects(Collection<FieldObject> fieldObjects) {
		this.fieldObjects = fieldObjects;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
}
