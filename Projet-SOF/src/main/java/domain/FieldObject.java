package domain;

import javax.persistence.*;

//@Entity
@Table(name="A_pour_champs")
public class FieldObject {

	@EmbeddedId
	FieldObjectId fo;
	
	@MapsId(value="object")
	@ManyToOne
	@JoinColumn(name="Code_objet",referencedColumnName="Code_objet")
	private Object object ;
	
	@MapsId(value="field")
	@ManyToOne
	@JoinColumn(name="Id_champ",referencedColumnName="Id")
	private Field field ;

	@Column(name = "value")
	private String value;


	public FieldObjectId getFo() {
		return fo;
	}

	public void setFo(FieldObjectId fo) {
		this.fo = fo;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}


}
