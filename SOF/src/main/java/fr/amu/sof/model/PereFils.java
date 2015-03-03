package fr.amu.sof.model;

import javax.persistence.*;


@Entity
public class PereFils {
	

	 @Id
     PereFilsId pk;
     
     @Column(name="Rang")
     private String Rang;
     
     public PereFilsId getPk() {
             return pk;
     }

     public void setPk(PereFilsId pk) {
             this.pk = pk;
     }
     
     public void setRang(String Rang) {this.Rang= Rang;}
     
     public String getRang() {return Rang;}
     
     public Object getObjectPere() {
        return getPk().getObjectPere();
}

public void setObjectPere(Object objectPere) {
        getPk().setObjectPere(objectPere);
}

public Object getObjectFils() {
        return getPk().getObjectFils();
}

public void setObjectFils(Object objectFils) {
        getPk().setObjectFils(objectFils);
}
	
}
