package com.it.unibs.alessandrobellini.cartellasanitaria.logic;

import java.util.Map;

import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Esame;
import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Malattia;
import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.PrestazioneEsame;
import com.it.unibs.alessandrobellini.cartellasanitaria.session.ApplicationSession;

public class VisualizzazioneSinteticaUtente {
	public void  execute() {
		ApplicationSession sessione = ApplicationSession.getIstance();
		sessione.getPaziente();
		sessione.getMalattie();
		
		StringBuffer st = new StringBuffer();
		st.append(" Nome : " + sessione.getPaziente().getNome() + "\n");
		st.append("Cognome : " + sessione.getPaziente().getCognome() + "\n");
		st.append('\n');
		st.append("[Lista esami]\n");
		//
		Map <Long , PrestazioneEsame> prestazioni = sessione.getPrestazioni();
		Map <Long , Esame> esami = sessione.getEsami();
		for (Map.Entry<Long, PrestazioneEsame> entry : prestazioni.entrySet()) {
			Long key = entry.getKey();
			PrestazioneEsame prest = entry.getValue();
			prest.getIdEsame();
			prest.getDataEsame();
			for (Map.Entry<Long, Esame> esame : esami.entrySet()) {
				Long key1 = esame.getKey();
				Esame exam = esame.getValue();
				if(prest.getIdEsame() == key1) {
					st.append(" Nome dell'esame  : " + exam.getNome() + "\n");
					st.append("Data : " + prest.getDataEsame() + "\n");
					}
				if(exam.getTipologia().equals("PERIODICO")) {
					st.append("L' esito dell'esame è : ").append(prest.getEsito());
					st.append('\n').append('\n');
					}
				}
			}
		Map <Long , Malattia > malattie = sessione.getMalattie();
		st.append('\n');
		st.append("[Lista malattie]\n");
		for (Map.Entry<Long ,Malattia> malattia : malattie.entrySet()) {
			Long key = malattia.getKey();
			Malattia value = malattia.getValue();
			st.append(" Nome della malattia  : " + value.getNome() + "\n");
			st.append("Data di inzio : " + value.getDataInizio().substring(0, 11) + "\n");
			
		}
		
		
 System.out.println(st);
}
}
