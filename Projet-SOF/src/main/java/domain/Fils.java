package domain;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Fils")
public class Fils {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	int id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "Code_fils")
	private Object fils;

	@Column(name = "Rang")
	private Integer Rang;

	@NotNull
	public void setRang(Integer Rang) {
		this.Rang = Rang;
	}

	public Integer getRang() {
		return Rang;
	}	

	public Object getFils() {
		return fils;
	}

	public void setFils(Object fils) {
		this.fils = fils;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
