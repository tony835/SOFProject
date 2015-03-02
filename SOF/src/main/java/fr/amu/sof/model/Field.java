package fr.amu.sof.model;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


@Entity
@NamedQueries({
    @NamedQuery(name = Field.FIND_ALL, query = "SELECT p FROM Field p"),
})
@Table(name = "Descripteur_champs")
public class Field {

	public static final  String FIND_ALL = "FIND_ALL_Field";
	@Id
	@Column(name="Id")
	String id;
	
	@Column(name="Nom_champ")
	String name;
	
	@Column(name="Description_champ")
	String description;
	
	@Column(name="Type_contenu")
	@Enumerated(EnumType.STRING)
	TypeContenu typeContenu;
		
	@Transient
	boolean required ;
	
	@Transient
	Map<String, String> lstValue;
	
	public enum TypeContenu{STRING,ENUM,STRUCTURE,INT}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public TypeContenu getTypeContenu() {
		return typeContenu;
	}
	public void setTypeContenu(TypeContenu typeContenu) {
		this.typeContenu = typeContenu;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public Map<String, String> getLstValue() {
		return lstValue;
	}
	public void setLstValue(Map<String, String> lstValue) {
		this.lstValue = lstValue;
	}
	

	
}
