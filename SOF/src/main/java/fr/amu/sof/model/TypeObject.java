package fr.amu.sof.model;

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
	@Column(name="Code_type")
	String code ;
	
	@Column(name="Nom_typeObjet")
	String name ;	
	
	@Column(name="ContentModel")
	String modelContenu;
	
	@Column(name="Erreur_descritpion")
	String descError;
	
	@Column(name="Code_Regex")
	String codeRegex ;
	
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

	public String getCodeRegex() {
		return codeRegex;
	}

	public void setCodeRegex(String codeRegex) {
		this.codeRegex = codeRegex;
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

	

}
