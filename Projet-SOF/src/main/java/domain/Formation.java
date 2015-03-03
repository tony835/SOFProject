package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="Formation")
@NamedQueries({
    @NamedQuery(name = Formation.FIND_ALL, query = "SELECT p FROM Formation p"),
})
public class Formation extends domain.Object{
	
	public static final String FIND_ALL = "FIND_ALL_Formation";
	
	@Column(name="Error_number")
	int numError;
	
	@Column(name="Visible")
	boolean visible;

	public int getNumError() {
		return numError;
	}

	public void setNumError(int numError) {
		this.numError = numError;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
