package com.it.unibs.alessandrobellini.cartellasanitaria.session;

import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Paziente;

/*
 * singleton : una sola istanza per tutta l'applicazione
 * 
 */
public class ApplicationSession {
	
	private static ApplicationSession istance;
	private Paziente paziente;
	
	
	
	//costruttore privato 
	//singola istanza static 
	//metodi di accesso all'istanza pubblici e static
	private ApplicationSession() {
		//nessuna altra classe può creare sessioni, solo questa
	}
	public static ApplicationSession getIstance() {
		if(istance == null)
			istance = new ApplicationSession();
		return istance; 
	}
	public Paziente getPaziente() {
		return paziente;
	}
	public void setPaziente(Paziente paziente) {
		this.paziente = paziente;
	}
	
	//i metodi static possono essere chiamati anche senza un'istanza creata
	//quelli non static rihciedono un'istanza
	
	
	

}
