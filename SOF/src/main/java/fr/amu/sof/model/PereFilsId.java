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

}
