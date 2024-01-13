package com.it.unibs.alessandrobellini.cartellasanitaria;

import com.it.unibs.alessandrobellini.cartellasanitaria.logic.SalvaCaricaDati;

public class Main {
	
	/**
	 * Main method, avvio dell'applicazione
	 * 
	 * @param args - Argomenti da passare in esecuzione, nessuno richiesto
	 */
	public static void main(String... args) {
		//inizializza sessione
		System.out.println("avvio applicazione");
		//carica i dati da un file locale
		SalvaCaricaDati.caricaDati();
		Menu menu = new Menu();
		//avvia la prima richiesta all'utente
		menu.menuPrincipale();
		System.out.println("termine applicazione");
		
	}

}
