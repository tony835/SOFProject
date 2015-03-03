package fr.amu.sof.model;

import java.io.Serializable;

import javax.persistence.*;


@Embeddable
public class FieldObjectId implements Serializable{
	
     @ManyToOne
     @JoinColumn(name = "id_field")
     private Field field;
     
     
     public Field getField() {
             return field;
     }

     public void setField(Field field) {
             this.field = field;
     }

     @ManyToOne
     @JoinColumn(name = "code_object")
     private Object object;
     
     
     public Object getObject() {
             return object;
     }
     
     public void setObject(Object object) {
             this.object = object;
     }
     
}
