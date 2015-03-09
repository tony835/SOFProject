package domain;

import java.util.Collection;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	@Column(name="Id",nullable=false)
	String id;
	
	@Column(name="Nom_champ")
	String name;
	
	@Column(name="Description_champ")
	String description;
	
	@Column(name="Type_contenu")
	@Enumerated(EnumType.STRING)
	TypeContenu typeContenu;
	
	@Column(name="Required")
	boolean required ;

	@ManyToOne
	@JoinColumn(name="Code_type")
	private TypeObject typeObject;
	
	@OneToMany(mappedBy="field")
	private Collection<FieldObject> fieldObjects ;
	
	
	@Transient
	Map<String, String> lstValue;
	
	public enum TypeContenu{
		STRING,
		ENUM,
		STRUCTURE,
		INT;
		
		public static TypeContenu fromSring(String type){
			if(type.equals("enum"))
				return ENUM;
			else if (type.equals("structure")){
				return STRUCTURE ;
			}
			else if (type.equals("int")){
				return INT;
			}
			else{
				return STRING ;
			}
		}
	}
	
	@Column(name="length")
	private int length;
	
	@Column(name="tabName")
	private String tabName;
	
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
	public TypeObject getTypeObject() {
		return typeObject;
	}
	public void setTypeObject(TypeObject typeObject) {
		this.typeObject = typeObject;
	}
	
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getTabName() {
		return tabName;
	}
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lstValue == null) ? 0 : lstValue.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (required ? 1231 : 1237);
		result = prime * result
				+ ((typeContenu == null) ? 0 : typeContenu.hashCode());
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
		Field other = (Field) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lstValue == null) {
			if (other.lstValue != null)
				return false;
		} else if (!lstValue.equals(other.lstValue))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (required != other.required)
			return false;
		if (typeContenu != other.typeContenu)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Field [id=" + id + ", name=" + name + ", description="
				+ description + ", typeContenu=" + typeContenu + ", required="
				+ required + "]";
	}

	
}