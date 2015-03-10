package domain;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="TypesObjets")
@NamedQueries({
    @NamedQuery(name = TypeObject.FIND_ALL, query = "SELECT p FROM TypeObject p"),
})

public class TypeObject {
	
	public static final String FIND_ALL = "FIND_ALL_TypeObject";

	@Id
	@Column(name="Code_type",nullable=false)
	String code ;
	
	@Column(name="Nom_typeObjet")
	String name ;	
	
	@Column(name="ContentModel")
	String modelContenu;
	
	@Column(name="Erreur_description")
	String descError;
	
	@OneToMany(mappedBy="typeObject")
	private Collection<Object> objects;
	
	@OneToMany(mappedBy="typeObject")
	private Collection<Field> fields;

	
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

	public String getModelContenu() {
		return modelContenu;
	}

	public void setModelContenu(String modelContenu) {
		this.modelContenu = modelContenu;
	}

	public String getDescError() {
		return descError;
	}

	public void setDescError(String descError) {
		this.descError = descError;
	}

	public Collection<Object> getObjects() {
		return objects;
	}

	public void setObjects(Collection<Object> objects) {
		this.objects = objects;
	}

	public Collection<Field> getFields() {
		return fields;
	}

	public void setFields(Collection<Field> fields) {
		this.fields = fields;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((descError == null) ? 0 : descError.hashCode());
		result = prime * result
				+ ((modelContenu == null) ? 0 : modelContenu.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(java.lang.Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TypeObject other = (TypeObject) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (descError == null) {
			if (other.descError != null)
				return false;
		} else if (!descError.equals(other.descError))
			return false;
		if (modelContenu == null) {
			if (other.modelContenu != null)
				return false;
		} else if (!modelContenu.equals(other.modelContenu))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "TypeObject [code=" + code + ", name=" + name
				+ ", modelContenu=" + modelContenu + ", descError=" + descError
				+ "]";
	}


}