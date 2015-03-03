package fr.amu.sof.model;

import java.io.Serializable;

import javax.persistence.*;


@Embeddable
public class PereFilsId implements Serializable{
	
    
	@ManyToOne
     @JoinColumn(name = "id_pere")
     private Object pere;
     public Object getObjectPere() {
             return pere;
     }

     public void setObjectPere(Object pere) {
             this.pere = pere;
     }

     @ManyToOne
     @JoinColumn(name = "id_fils")
     private Object fils;
     public Object getObjectFils() {
             return fils;
     }

     public void setObjectFils(Object fils) {
             this.fils = fils;
     }

}
