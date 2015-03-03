package fr.amu.sof.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="Objet")
@Inheritance(strategy=InheritanceType.JOINED)
@NamedQueries({
    @NamedQuery(name = Object.FIND_ALL, query = "SELECT p FROM Object p"),
})
public class Object {
	
	public static final String FIND_ALL = "FIND_ALL_Object";

	@Id
	@Column(name="Code_objet")
	private String code;
	
	@Column(name="Nom_objet")
	private String name;
	
	@Column(name="Version")
	private String version;
	
	@Column(name="FMutualisable")
	private boolean mutualisable;
	
	@OneToMany(mappedBy="object")
	@JoinColumn(name="CODE_FORMATION")
	private Collection<Formation> formations;
	
	@ManyToOne
	private TypeObject typeObject;
	
	
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
	public Collection<Formation> getFormations() {
		return formations;
	}
	public void setFormations(Collection<Formation> formations) {
		this.formations = formations;
	}
	public TypeObject getTypeObject() {
		return typeObject;
	}
	public void setTypeObject(TypeObject typeObject) {
		this.typeObject = typeObject;
	}

}
