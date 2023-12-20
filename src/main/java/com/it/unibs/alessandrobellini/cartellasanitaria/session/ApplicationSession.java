package com.it.unibs.alessandrobellini.cartellasanitaria.session;

import java.util.HashMap;
import java.util.Map;

import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Esame;
import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Malattia;
import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Paziente;
import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.PrestazioneEsame;

/*
 * singleton : una sola istanza per tutta l'applicazione
 * 
 */
public class ApplicationSession {
	
	private static ApplicationSession istance;
	private Paziente paziente;
	private Map<Long, Esame> esami;
	private Map <Long ,PrestazioneEsame> prestazioni;
	private Map <Long , Malattia> malattie;
	
	public Map<Long, Malattia> getMalattie() {
		return malattie;
	}
	public void setMalattie(Map<Long, Malattia> malattie) {
		this.malattie = malattie;
	}
	//costruttore privato 
	//singola istanza static 
	//metodi di accesso all'istanza pubblici e static
	private ApplicationSession() {
		//nessuna altra classe può creare sessioni, solo questa
		paziente = new Paziente();
		esami = new HashMap<>();// tramite l'hash accede direttamente al dato senza ciclare tutti gli altri, lo ragguiugne per indirizzo
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
	public Map<Long , Esame> getEsami() {
		return esami;
	}
	public void setEsami(Map<Long , Esame> esami) {
		this.esami = esami;
	}
	public void addEsame(Long key, Esame esame) {
		esami.put(key, esame);
	}
	public Map<Long, PrestazioneEsame> getPrestazioni() {
		return prestazioni;
	}
	public void setPrestazioni(Map<Long, PrestazioneEsame> prestazioni) {
		this.prestazioni = prestazioni;
	}
	public void addPrestazione (Long key, PrestazioneEsame prestazione) {
		prestazioni.put(key, prestazione);
		
		
	}
	
	//i metodi static possono essere chiamati anche senza un'istanza creata
	//quelli non static rihciedono un'istanza
	
	
	

}
