package com.it.unibs.alessandrobellini.cartellasanitaria.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Esame;
import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.PrestazioneEsame;
import com.it.unibs.alessandrobellini.cartellasanitaria.session.ApplicationSession;

/**
 * Classe di visualizzazione dell'esame dalla lista della sessione.<br>
 * Verrà effettuata la ricerca dell'esame da parte dell'utente, dovrà essere selezionato solo
 * un esame di tipo DIAGNOSTICO.<br>
 * Verranno stampate le informazioni legate all'esame diagnostico selezionato e le prestazioni
 * effettuate su di esso.
 */
public class VisualizzazioneEsameDiagnostico implements FunzionalitaInterface {
	
	public void  execute() {
		ApplicationSession sessione = ApplicationSession.getIstance();
		
		CercaEsame esameDaStampare = new CercaEsame();
		Long idEsame = esameDaStampare.ricercaTipologia();
		if (idEsame == null || idEsame == 0L) {
			System.out.println("Ricerca annullata");
			return;
		}
		Map<Long, Esame> esami = sessione.getEsami();
		
		Esame esameSelezionato = esami.get(idEsame);
		if(!esameSelezionato.getTipologia().equals("DIAGNOSTICO")) {
			System.out.println("L'esame non è di tipo DIAGNOSTICO.\n"
					+ "Ricomincia la ricerca e seleziona un esame di tipo DIAGNOSTICO \n");
			return;
		}
		
		
		List<PrestazioneEsame> prestSelezionati = new ArrayList<>();
		for (Map.Entry<Long, PrestazioneEsame> entry : sessione.getPrestazioni().entrySet()) {
			PrestazioneEsame prestazione = entry.getValue();
			if (prestazione.getIdEsame() != idEsame) {
				continue;
			}
			
			prestSelezionati.add(prestazione);
			
		}
		
		if (prestSelezionati != null && prestSelezionati.size() > 0)
			printListaPrestazioni(prestSelezionati);
		else
			System.out.println("Nessuna prestazione da visualizzare");
		
	}
	
	
	/**
	 * Classe che stampa le info delle prestazioni
	 * @param prestazioni - Lista delle prestazioni da stampare
	 */
	private static void printListaPrestazioni(List<PrestazioneEsame> prestazioni) {
		ApplicationSession sessione = ApplicationSession.getIstance();
		StringBuffer sb = new StringBuffer("Prestazioni Mediche ricercate:\n");
		for (PrestazioneEsame prest : prestazioni) {
			Esame esame = sessione.getEsami().get(prest.getIdEsame());
			
			sb.append("Nome esame: ").append(esame.getNome()).append('\n');
			sb.append("Tipologia esame: ").append(esame.getTipologia()).append('\n');
			sb.append("Esito esame: ").append(prest.getEsito()).append('\n');
			sb.append("Data prestazione medica: ").append(prest.getDataEsame()).append('\n');
			
		}
		sb.append("Numero di esami: ").append(prestazioni.size()).append('\n');
		System.out.println(sb.toString());
		
		
	}
	
	
}
