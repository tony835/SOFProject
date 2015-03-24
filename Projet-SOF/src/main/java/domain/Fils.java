package domain;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Fils")
public class Fils implements Comparable<Fils>{
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	int id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "Code_fils")
	private domain.Object fils;

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

	@Override
	public int compareTo(Fils o) {
		// TODO Auto-generated method stub
		Integer rang = ((Fils) o).getRang() ;
        if(this.Rang != rang)
		return this.Rang - rang;
        else 
        	return this.getFils().getCode().compareTo(o.getFils().getCode());
	}

}
