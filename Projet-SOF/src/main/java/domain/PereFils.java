package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



@Entity
@IdClass(PereFilsId.class)
public class PereFils {

    @ManyToOne
    @JoinColumn(name = "id_pere")
    @Id
    private Object pere;
    public Object getObjectPere() {
            return pere;
    }

    public void setObjectPere(Object pere) {
            this.pere = pere;
    }

    @ManyToOne
    @JoinColumn(name = "id_fils")
    @Id
    private Object fils;
    public Object getObjectFils() {
            return fils;
    }

    public void setObjectFils(Object fils) {
            this.fils = fils;
    }


	@Column(name = "Rang")
	private String rang;

	public void setRang(String Rang) {
		this.rang = Rang;
	}

	public String getRang() {
		return rang;
	}




}
