package com.it.unibs.alessandrobellini.cartellasanitaria.logic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Esame;
import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.PrestazioneEsame;
import com.it.unibs.alessandrobellini.cartellasanitaria.session.ApplicationSession;

public class VisualizzazioneEsameDiagnostico {
	
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
		BigDecimal bd = new BigDecimal("" + prestazioni.size());
		sb.append("Numero di esami: ").append(prestazioni.size()).append('\n');
		System.out.println(sb.toString());
		
		
	}
	
	
}
