package com.it.unibs.alessandrobellini.cartellasanitaria.logic;

import java.util.Map;

import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Esame;
import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Malattia;
import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.PrestazioneEsame;
import com.it.unibs.alessandrobellini.cartellasanitaria.session.ApplicationSession;
import com.it.unibs.alessandrobellini.cartellasanitaria.utils.VisualizzazioneUtils;

/**
 * Classe principale di visualizzazione delle informazioni del paziente.
 * In questa classe vengono stampate le info principali del paziente e sarà richiesto
 * un selettore per visualizzare il dettaglio di una informazione.
 */
public class VisualizzazioneSinteticaUtente implements FunzionalitaInterface {
	
	public void  execute() {
		ApplicationSession sessione = ApplicationSession.getIstance();
		sessione.getPaziente();
		sessione.getMalattie();
		
		StringBuffer st = new StringBuffer();
		st.append(" Nome : " + sessione.getPaziente().getNome() + "\n");
		st.append("Cognome : " + sessione.getPaziente().getCognome() + "\n");
		st.append('\n');
		st.append("[Lista esami]\n");
		
		Map <Long , PrestazioneEsame> prestazioni = sessione.getPrestazioni();
		Map <Long , Esame> esami = sessione.getEsami();
		for (Map.Entry<Long, PrestazioneEsame> entry : prestazioni.entrySet()) {
			PrestazioneEsame prest = entry.getValue();
			Long idEsame = prest.getIdEsame();
			Esame esame = esami.get(idEsame);
			st.append("Nome esame: ").append(esame.getNome()).append('\n');
			st.append("Data prestazione: ").append(prest.getDataEsame()).append('\n');
			
			if(esame.getTipologia().equals("PERIODICO")) {
				st.append("L' esito dell'esame è : ").append(prest.getEsito()).append('\n');
			}
			st.append('\n');
		}
		
		Map <Long , Malattia > malattie = sessione.getMalattie();
		st.append('\n');
		st.append("[Lista malattie]\n");
		for (Map.Entry<Long ,Malattia> malattia : malattie.entrySet()) {
			Malattia value = malattia.getValue();
			st.append(" Nome della malattia  : " + value.getNome() + "\n");
			st.append("Data di inizio : " + value.getDataInizio().substring(0, 10) + "\n");
			if (value.getDataFine() != null && value.getDataFine().length() > 10) {
				st.append("Data di fine : " + value.getDataFine().substring(0, 10) + "\n");
			}
		}
		
		
	System.out.println(st);
	
	VisualizzazioneUtils.menuApprofondimento();
 
  }
}
