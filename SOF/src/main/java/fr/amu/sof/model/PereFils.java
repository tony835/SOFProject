package fr.amu.sof.model;

import javax.persistence.*;

@Entity
@Table(name="Pere_fils")
public class PereFils {

	@EmbeddedId
	PereFilsId pk;

	@MapsId(value = "pere")
	@ManyToOne
	@JoinColumn(name = "Code_pere")
	private Object pere;

	@MapsId(value = "fils")
	@ManyToOne
	@JoinColumn(name = "Code_fils")
	private Object fils;

	@Column(name = "Rang")
	private String Rang;

	public PereFilsId getPk() {
		return pk;
	}

	public void setPk(PereFilsId pk) {
		this.pk = pk;
	}

	public void setRang(String Rang) {
		this.Rang = Rang;
	}

	public String getRang() {
		return Rang;
	}

}
