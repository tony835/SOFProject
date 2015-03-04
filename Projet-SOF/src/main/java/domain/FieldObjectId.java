package domain;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + field;
		result = prime * result + ((object == null) ? 0 : object.hashCode());
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
		FieldObjectId other = (FieldObjectId) obj;
		if (field != other.field)
			return false;
		if (object == null) {
			if (other.object != null)
				return false;
		} else if (!object.equals(other.object))
			return false;
		return true;
	}



}
