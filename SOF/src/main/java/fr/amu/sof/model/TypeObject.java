package fr.amu.sof.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * CREATE TABLE IF NOT EXISTS TypesObjets (
	Code_type varchar(255) not null,
	Nom_typeObjet varchar(255) not null,
	ContentModel varchar(255) not null,
	Erreur_descritpion varchar(255) not null,
	primary key (Code_type)
	
	Code_Regex
);
 *
 */
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

	@Column(name="Code_Regex")
	String codeRegex ;

}
