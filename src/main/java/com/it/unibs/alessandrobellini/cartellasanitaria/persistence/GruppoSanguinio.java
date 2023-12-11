package com.it.unibs.alessandrobellini.cartellasanitaria.persistence;

import java.io.Serializable;

public class GruppoSanguinio implements Serializable {
	private static final long serialVersionUID = 2366616291727832998L;// quando si scrvie da oggetto java ad un file di 
	/*
	 * trasformi l'oggetto java in dati, questo trasforma l'oggetto in un array di byte ed esso potrebbe 
	 * andare in collisione di byte, per evitare la collisione si usa l'UID che lo serializza in modo 
	 * univoco
	 */
	private String fattoreRh;
	private String gruppo;
	
	public GruppoSanguinio() {
		
	}
	
	public GruppoSanguinio(String fattoreRh, String gruppo) {
		super();//chiama il costruttore della classe padre on questo caso direttamente object
		this.fattoreRh = fattoreRh;
		this.gruppo = gruppo;
	}

	public String getFattoreRh() {
		return fattoreRh;
	}

	public void setFattoreRh(String fattoreRh) {
		this.fattoreRh = fattoreRh;
	}

	public String getGruppo() {
		return gruppo;
	}

	public void setGruppo(String gruppo) {
		this.gruppo = gruppo;
	}

	@Override
	public String toString() {
		return "GruppoSanguinio [fattoreRh=" + fattoreRh + ", gruppo=" + gruppo + "]";
	}
	
	

	
	

}
