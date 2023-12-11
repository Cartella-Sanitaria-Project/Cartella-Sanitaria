package com.it.unibs.alessandrobellini.cartellasanitaria.utils;

import java.util.UUID;

import org.apache.commons.lang3.math.NumberUtils;

public class PazienteUtils {
	
	public static String genCodiceSanitario() {
		String csId = UUID.randomUUID().toString(); /* generazione univoca di un identificativo, (no duplicati)
		* salviamo ciò che 
		* usiamoil metodo toStirng per trasformarlo in una stringa 
		*/
		csId=csId.replaceAll("-", "");//rimpiazza TUTTI i trattini con carattere vuoto ovvero lki rimuove 
	    //esendo String una classe immutabile e che quindi ha bisgono di un riassegnamento per non creare una nuova stringa
		return csId;
		
	}
	
	public static boolean isDataNascitaValid(String dataDiNascita) {
		//verfichiamo la bontà della data
		if(dataDiNascita == null) {
			System.out.println("la data di nascita è nulla");
			return false;
		}
		if (dataDiNascita.length() != 10) {
			System.out.println("la data di nascita non è nel formato atteso : yyyy-MM-dd");
			return false;	
		}
		if (NumberUtils.toInt(dataDiNascita.substring(0, 4), -1) < 0 ) { //se ciò che viene passato non è un numero si ritorna il valore di default
			System.out.println("l'anno non è un numero");
			return false;
		}
		if (NumberUtils.toInt(dataDiNascita.substring(5, 7), -1) < 0 ) { 
			System.out.println("il mese non è un numero");
			return false;
		}
		if (NumberUtils.toInt(dataDiNascita.substring(8, 10), -1) < 0 ) { 
			System.out.println("il giorno non è un numero");
			return false;
			
		}
		return true;
		
	}
	public static boolean isCFValid (String codiceFiscale) { //deve essere acceduto tramite classe diretta senza inizializzare la classe
		 boolean condizione = true;
		if (codiceFiscale.length() == 16) {
			for (int i=0; i<=5; i++) {
				char c = codiceFiscale.charAt(i);
				if (Character.isLetter(c) != true )
					condizione = false;
			}
			for (int j=6; j<=7; j++) {
				char c = codiceFiscale.charAt(j);
				if (Character.isDigit(c) != true )
					condizione = false;
				
			}
			int k = 8;
			char c = codiceFiscale.charAt(k);
			if (Character.isLetter(c) != true )
				condizione = false;
			for (int j=9; j<=10; j++) {
				char g = codiceFiscale.charAt(j);
				if (Character.isDigit(g) != true )
					condizione = false;
			}
			int n = 11;
			char h = codiceFiscale.charAt(n);
			if (Character.isLetter(h) != true )
				condizione = false;
			for (int j=12; j<=14; j++) {
				char g = codiceFiscale.charAt(j);
				if (Character.isDigit(g) != true )
					condizione = false;
			}
			int m = 15;
			char l = codiceFiscale.charAt(m);
			if (Character.isLetter(l) != true )
				condizione = false;
			return condizione;
			/*if (conta_eccezioni != 0)
				return false;
			else return true;
			//altrimenti utilizzare il confronto dei codici ASCII*/
		}
		else return false;
	
				
		
	}

}
