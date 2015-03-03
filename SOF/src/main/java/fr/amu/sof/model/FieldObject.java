package fr.amu.sof.model;


import javax.persistence.*;

@Entity
public class FieldObject {
	

	 @Id
	 FieldObjectId fo;
     
     @Column(name="value")
     private String value;
     
     public FieldObjectId getFo() {
             return fo;
     }

     public void setFo(FieldObjectId fo) {
             this.fo = fo;
     }
     
     public void setValue(String value) {this.value= value;}
     
     public String getValue() {return value;}
     
     
     public Object getObject() {
        return getFo().getObject();
}
     
public void setObject(Object object) {
        getFo().setObject(object);;
}





public Field getField() {
        return getFo().getField();
}

public void setField(Field field) {
        getFo().setField(field);;
}


	
}
