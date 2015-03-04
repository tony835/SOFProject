package fr.amu.sof.model;

import java.io.Serializable;

import javax.persistence.*;

@Embeddable
public class FieldObjectId implements Serializable {

	private static final long serialVersionUID = 1L;

	private int field;

	private String object;

	
	public int getField() {
		return field;
	}

	public void setField(int field) {
		this.field = field;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}



}
