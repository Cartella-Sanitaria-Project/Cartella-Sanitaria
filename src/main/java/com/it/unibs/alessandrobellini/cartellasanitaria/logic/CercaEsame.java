package com.it.unibs.alessandrobellini.cartellasanitaria.logic;

import java.util.HashMap;
import java.util.Map;

import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Esame;
import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Malattia;
import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.PrestazioneEsame;
import com.it.unibs.alessandrobellini.cartellasanitaria.session.ApplicationSession;
import com.it.unibs.alessandrobellini.cartellasanitaria.utils.InputDati;

public class CercaEsame {
	public Long ricercaTipologia() {
	    /*
	     * faccio inserire all'utente l'esame da ricercare
	     * verifico che ciò che ha inserito non generi eccezioni e che riferisca quanto non è stato scritto
	     */
		String nEsame = InputDati.leggiStringaNonVuota("inserisci il nome dell'esame da ricercare \n");
		 
		if (nEsame == null || nEsame.isEmpty()) {
			 System.out.println("Input vuoto, termino ricerca");
			 return null;
			 //se non viene inserito alcun dato la ricerca termina 
		}
		/*
		 * entro nella sessione già esistente, in seguito 
		 * prendo la lista già esistente di esami e ne creo una nuova per stivare gli esami che verranno ricercati
		 * 
		 */
		ApplicationSession sessione = ApplicationSession.getIstance();
		Map<Long, Esame> esami = sessione.getEsami();
		Map<Long, Esame> risultatiRicerca = new HashMap<>(esami.size());
		long i = 0L;//impongo che il dato sia long poichè c'è il pericolo che venga castato ad un int
		/*
		 * inizio a ciclare la lista di esami cercando quanto l'utente ha digitato con l'if 
		 * alla riga 47, cerca se il nome dell'esame corrisponde ad uno esistente ma anche solo gli esami che hanno in comune 
		 * alcuni caratteri inseriti dall'utente.
		 * ongi volta che la condizione dell'if viene soddisfatta inserisco nella Map esami ricercati
		 * tutti gli esami che hanno soddisfato l'if
		 */
		for (Map.Entry<Long, Esame> entry : esami.entrySet()) {
			Long key = entry.getKey();
			Esame esame = entry.getValue();
			
			if (key == null || esame == null || esame.getNome() == null)
				continue;
			
			if (esame.getNome().toLowerCase().equals(nEsame.toLowerCase()) ||
					esame.getNome().toLowerCase().contains(nEsame.toLowerCase())){
				i++;
				risultatiRicerca.put(i, esame);
			}
		}
		/*
		 * se nessun esame è compatibile cno quanto ricercato dall'utente non verrà posizionato n
		 * nessun nuovo elemento in risultatiRicerca
		 */
		if (risultatiRicerca.size() == 0) {
			System.out.println("Non esistono esami per la ricerca: " + nEsame);
			return null;
		}
		
		/*
		 * creo una string buffer con la lista dei risultati di ricerca al suo interno
		 * se essa ha portato ad una ricerca positiva di alcuni esami
		 */
		
		
		StringBuffer sb = new StringBuffer("Lista dei risultati di ricerca:\n");
		for (Map.Entry<Long, Esame> entry : risultatiRicerca.entrySet()) {
			long key = entry.getKey();
			Esame val = entry.getValue();
			
			sb.append("[").append(key).append("] ")
					.append(val.getNome()).append(" - ")
					.append(val.getTipologia()).append("\n");
		}
		
		/*
		 * faccio inserire all'utente l'indice numerico dell'esame di suo interesse
		 */
		sb.append("Seleziona l'esame dai risultati di ricerca, oppure digita 0 per annullare");
		int nRicerca = InputDati.leggiIntero(sb.toString());
		
		if((nRicerca < 0) || (nRicerca > i)) {
			System.out.println(" il numero non è compreso tra quelli della lista, termino ricerca \n");
			return null;
		}
		
		if(nRicerca == 0) {
			System.out.println("Ricerca annullata");
			return null;
		} else {
			Esame esameCercato = risultatiRicerca.get((long) nRicerca);
			return esameCercato.getIdEsame();//il tutto ritorna un Long che corrisponde all'id dell'esame trovato
		}
	}
	
	
	
	public Long ricercaPrestazione() {
		
		Long idEsameCercato = ricercaTipologia(); 
		/*
		 * non può esserci una prestazione se non esiste un esame
		 * a seguit verranno stamapte tutte le prestazioni con lo stesso id di esame 
		 * 
		 */
		
		if (idEsameCercato == null) {
			return null;
		}
		
		ApplicationSession sessione = ApplicationSession.getIstance();
		Map <Long , PrestazioneEsame> prestazioni = sessione.getPrestazioni();
		Map <Long, PrestazioneEsame> risultatiRicerca = new HashMap<>(prestazioni.size());
		long i=0L;
		for (Map.Entry<Long, PrestazioneEsame> entry : prestazioni.entrySet()) {
			Long key = entry.getKey();
			PrestazioneEsame prest = entry.getValue();
			
			if (key == null || prest == null)
				continue;
			
			if(prest.getIdEsame() == idEsameCercato) {
				i++;
				risultatiRicerca.put(i,prest);
				
			}
			
		}
		
		StringBuffer sb = new StringBuffer("Lista dei risultati di ricerca:\n");
		
		for (Map.Entry<Long, PrestazioneEsame> entry : risultatiRicerca.entrySet()) {
			long key = entry.getKey();
			PrestazioneEsame val = entry.getValue();
			
			sb.append("[").append(key).append("] ")
					.append(val.getDataEsame()).append(" - ")
					.append(val.getLuogoEsame()).append("\n");
		}
		/*
		 * seleziona l'i-esimo indice per indicare quale scegliere dalla lista
		 */
		
		sb.append("Seleziona la prestazione di esame dai risultati di ricerca, oppure digita 0 per annullare");
		int nRicerca = InputDati.leggiIntero(sb.toString());
		
		if((nRicerca < 0) || (nRicerca > i)) {
			System.out.println(" il numero non è compreso tra quelli della lista, termino ricerca \n");
			return null;
		}
		
		if(nRicerca == 0) {
			System.out.println("Ricerca annullata");
			return null;
		} else {
			PrestazioneEsame peCercato = risultatiRicerca.get((long) nRicerca);
			return peCercato.getIdPrestazione();
		}
		
	}
	
	public Long ricercaMalattia() {
	    /*
	     * faccio inserire all'utente l'esame da ricercare
	     * verifico che ciò che ha inserito non generi eccezioni e che riferisca quanto non è stato scritto
	     */
		String nMalattia = InputDati.leggiStringaNonVuota("inserisci il nome della malattia da ricercare \n");
		 
		if (nMalattia == null || nMalattia.isEmpty()) {
			 System.out.println("Input vuoto, termino ricerca");
			 return null;
			 //se non viene inserito alcun dato la ricerca termina 
		}
		/*
		 * entro nella sessione già esistente, in seguito 
		 * prendo la lista già esistente di esami e ne creo una nuova per stivare gli esami che verranno ricercati
		 * 
		 */
		ApplicationSession sessione = ApplicationSession.getIstance();
		Map<Long, Malattia> malattie = sessione.getMalattie();
		Map<Long, Malattia> risultatiRicerca = new HashMap<>(malattie.size());
		long i = 0L;//impongo che il dato sia long poichè c'è il pericolo che venga castato ad un int
		/*
		 * inizio a ciclare la lista di esami cercando quanto l'utente ha digitato con l'if 
		 * alla riga 47, cerca se il nome dell'esame corrisponde ad uno esistente ma anche solo gli esami che hanno in comune 
		 * alcuni caratteri inseriti dall'utente.
		 * ongi volta che la condizione dell'if viene soddisfatta inserisco nella Map esami ricercati
		 * tutti gli esami che hanno soddisfato l'if
		 */
		for (Map.Entry<Long, Malattia> entry : malattie.entrySet()) {
			Long key = entry.getKey();
			Malattia malattia = entry.getValue();
			
			if (key == null || malattia == null || malattia.getNome() == null)
				continue;
			
			if (nMalattia.toLowerCase().contains(malattia.getNome().toLowerCase()) ||
					nMalattia.toLowerCase().equals(malattia.getNome().toLowerCase())){
				i++;
				risultatiRicerca.put(i, malattia);
			}
		}
		/*
		 * se nessun esame è compatibile cno quanto ricercato dall'utente non verrà posizionato n
		 * nessun nuovo elemento in risultatiRicerca
		 */
		if (risultatiRicerca.size() == 0) {
			System.out.println("Non esistono malattie per la ricerca: " + nMalattia);
			return null;
		}
		
		/*
		 * creo una string buffer con la lista dei risultati di ricerca al suo interno
		 * se essa ha portato ad una ricerca positiva di alcuni esami
		 */
		
		
		StringBuffer sb = new StringBuffer("Lista dei risultati di ricerca:\n");
		for (Map.Entry<Long, Malattia> entry : risultatiRicerca.entrySet()) {
			long key = entry.getKey();
			Malattia val = entry.getValue();
			
			sb.append("[").append(key).append("] ")
					.append(val.getNome()).append(" - ")
					.append(val.getDataInizio()).append("\n");
		}
		/*
		 * faccio inserire all'utente l'indice numerico della malattia di mio interesse
		 */
		sb.append("Seleziona la malattia dai risultati di ricerca, oppure digita 0 per annullare");
		int nRicerca = InputDati.leggiIntero(sb.toString());
		
		if((nRicerca < 0) || (nRicerca > i)) {
			System.out.println(" il numero non è compreso tra quelli della lista, termino ricerca \n");
			return null;
		}
		
		if(nRicerca == 0) {
			System.out.println("Ricerca annullata");
			return null;
		} else {
			Malattia malattiaCercata = risultatiRicerca.get((long) nRicerca);
			return malattiaCercata.getIdMalattia();//il tutto ritorna un Long che corrisponde all'id della malattia trovata
		}
	}
	



}
