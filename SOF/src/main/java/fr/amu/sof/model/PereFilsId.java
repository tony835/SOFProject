package fr.amu.sof.model;

import java.io.Serializable;

import javax.persistence.*;

@Embeddable
public class PereFilsId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String pere;
	private String fils;

	public String getPere() {
		return pere;
	}

	public void setPere(String pere) {
		this.pere = pere;
	}

	public String getFils() {
		return fils;
	}

	public void setFils(String fils) {
		this.fils = fils;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fils == null) ? 0 : fils.hashCode());
		result = prime * result + ((pere == null) ? 0 : pere.hashCode());
		return result;
	}

	@Override
	public boolean equals(java.lang.Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PereFilsId other = (PereFilsId) obj;
		if (fils == null) {
			if (other.fils != null)
				return false;
		} else if (!fils.equals(other.fils))
			return false;
		if (pere == null) {
			if (other.pere != null)
				return false;
		} else if (!pere.equals(other.pere))
			return false;
		return true;
	}

}
