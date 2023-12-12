package com.it.unibs.alessandrobellini.cartellasanitaria.logic;

import java.util.HashMap;
import java.util.Map;

import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Esame;
import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.PrestazioneEsame;
import com.it.unibs.alessandrobellini.cartellasanitaria.session.ApplicationSession;
import com.it.unibs.alessandrobellini.cartellasanitaria.utils.InputDati;

public class CercaEsame {
	public Long ricercaTipologia() {
	
		String nEsame = InputDati.leggiStringaNonVuota("inserisci il nome dell'esame da ricercare \n");
		 
		if (nEsame == null || nEsame.isEmpty()) {
			 System.out.println("Input vuoto, termino ricerca");
			 return null;
		}
		 
		ApplicationSession sessione = ApplicationSession.getIstance();
		Map<Long, Esame> esami = sessione.getEsami();
		Map<Long, Esame> risultatiRicerca = new HashMap<>(esami.size());
		long i = 0L;
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
		
		if (risultatiRicerca.size() == 0) {
			System.out.println("Non esistono esami per la ricerca: " + nEsame);
			return null;
		}
		
		
		StringBuffer sb = new StringBuffer("Lista dei risultati di ricerca:\n");
		
		for (Map.Entry<Long, Esame> entry : risultatiRicerca.entrySet()) {
			long key = entry.getKey();
			Esame val = entry.getValue();
			
			sb.append("[").append(key).append("] ")
					.append(val.getNome()).append(" - ")
					.append(val.getTipologia()).append("\n");
		}
		
		
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
			return esameCercato.getIdEsame();
		}
	}
	
	
	
	public Long ricercaPrestazione() {
		
		Long idEsameCercato = ricercaTipologia();
		
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


}
