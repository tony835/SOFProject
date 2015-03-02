package fr.amu.sof.model;


@Entity
public class PereFils {
	

	 @Id
     PereFilsId pk;
     
     public PereFilsId getPk() {
             return pk;
     }

     public void setPk(PereFilsId pk) {
             this.pk = pk;
     }

     @Column(name="Rang")
     private String Rang;
     
     public void setRang(String Rang) {Rang= Rang;}
     
     public String getRang() {return Rang;}
     
     public Object getObjectPere() {
        return getPk().getObjectPere();
}

public void setObjectPere(Object ObjectPere) {
        getPk().setObjectPere(ObjectPere);
}

public Object getObjectFils() {
        return getPk().getObjectFils();
}

public void setObjectFils(Object ObjectFils) {
        getPk().setObjectFils(ObjectFils);
}
	
}
