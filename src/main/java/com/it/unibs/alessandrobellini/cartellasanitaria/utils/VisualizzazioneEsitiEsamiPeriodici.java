package com.it.unibs.alessandrobellini.cartellasanitaria.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.it.unibs.alessandrobellini.cartellasanitaria.logic.CercaEsame;
import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Esame;
import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.PrestazioneEsame;
import com.it.unibs.alessandrobellini.cartellasanitaria.session.ApplicationSession;

public class VisualizzazioneEsitiEsamiPeriodici {
	public static void execute() {
		ApplicationSession sessione = ApplicationSession.getIstance();
		CercaEsame esameDaStampare = new CercaEsame();
		Long idEsame = esameDaStampare.ricercaTipologia();
		if (idEsame == null || idEsame == 0L) {
			System.out.println("Ricerca annullata");
			return;
		}
		Map<Long, Esame> esami = sessione.getEsami();
		
		Esame esameSelezionato = esami.get(idEsame);
		if(!esameSelezionato.getTipologia().equals("PERIODICO")) {
			System.out.println("L'esame non è di tipo PERIODICO.\n"
					+ "Ricomincia la ricerca e seleziona un esame di tipo PERIODICO \n");
			return;
		}
		
		boolean onRunning = true,
				daFiltrare = false;
		while (onRunning) {
			System.out.println("E'stato selezionato un esame periodico \n "
					+ "premere 1 se si vuole filtrare per date gli esami \n"
					+ "premere 0 se si vuole visualizzare tutti gli esami indipendentemente dalla data \n");
			Scanner scanner = new Scanner(System.in);
			String input = scanner.nextLine();
			if (!StringUtils.isNumeric(input)) {
				System.out.println("l'input non è un numero, inserire 0 o 1");
				continue;
			}
			int inputNum = NumberUtils.toInt(input, -1);
			if((inputNum < 0) || (inputNum > 1)) {
				System.out.println(" il numero non è compreso tra i due disponibili");
				continue;	//ritorna alla testa del ciclo e ripetilo
			}
			if(inputNum == 0) {
				daFiltrare = false;
				onRunning = false;
			} else if (inputNum == 1){
				daFiltrare = true;
				onRunning = false;
				
			}
		}
		
		Date[] dateDaFiltrare = null;
		if (daFiltrare) {
			dateDaFiltrare = richiediDatePerFiltro();
		}
		List<PrestazioneEsame> prestSelezionati = new ArrayList<>();
		for (Map.Entry<Long, PrestazioneEsame> entry : sessione.getPrestazioni().entrySet()) {
			PrestazioneEsame prestazione = entry.getValue();
			if (prestazione.getIdEsame() != idEsame) {
				continue;
			}
			
			if (dateDaFiltrare != null && dateDaFiltrare.length == 2) {
				Date dataPrestazioneMedica = InsertDateUtils.castDateStandard(prestazione.getDataEsame());
				if (dateDaFiltrare[0].after(dataPrestazioneMedica)) {
					// data di inizio DOPO la data di prestazione medica
					continue;
				}
				if (dateDaFiltrare[1].before(dataPrestazioneMedica)) {
					// data di fine PRIMA della data di prestazione medica
					continue;
				}
			}
			
			
			prestSelezionati.add(prestazione);
			
		}
		
		printListaPrestazioni(prestSelezionati);
		
	}
		
		
	private static Date[] richiediDatePerFiltro() {
		Date[] dateDaFiltrare = new Date[2];
		System.out.println("Inserisci la data di inizio filtro");
        Date dataInizio = InsertDateUtils.richiediDataSoloGiorno();
        System.out.println("Inserisci la data di fine filtro");
        Date dataFine = InsertDateUtils.richiediDataSoloGiorno();
        dateDaFiltrare[0] = dataInizio;
        dateDaFiltrare[1] = dataFine;
        return dateDaFiltrare;
	}
	
	private static void printListaPrestazioni(List<PrestazioneEsame> prestazioni) {
		ApplicationSession sessione = ApplicationSession.getIstance();
		StringBuffer sb = new StringBuffer("Prestazioni Mediche ricercate:\n");
		BigDecimal somma = BigDecimal.ZERO;
		for (PrestazioneEsame prest : prestazioni) {
			Esame esame = sessione.getEsami().get(prest.getIdEsame());
			
			BigDecimal normMax = esame.getValoreNormalitaMax(),
					normMin =esame.getValoreNormalitaMin(),
					esito = new BigDecimal(prest.getEsito());
			boolean superamentoNorm = esito.compareTo(normMax) > 0 || esito.compareTo(normMin) < 0;
			somma = somma.add(esito);
			
			sb.append("Nome esame: ").append(esame.getNome()).append('\n');
			sb.append("Tipologia esame: ").append(esame.getTipologia()).append('\n');
			sb.append("Valore misurato: ").append(prest.getEsito()).append('\n');
			sb.append("Data prestazione medica: ").append(prest.getDataEsame()).append('\n');
			sb.append("Valore normalità massimo: ").append(esame.getValoreNormalitaMax()).append('\n');
			sb.append("Valore normalità minimo: ").append(esame.getValoreNormalitaMin()).append('\n');
			sb.append("Valore di soglia: ").append(esame.getSogliaErroreInserimento()).append('\n');
			sb.append("Superamento valori normalità: ").append(superamentoNorm).append('\n');
			
		}
		BigDecimal bd = new BigDecimal("" + prestazioni.size());
		System.out.println("VALORE: ["+ bd + "] - " +prestazioni.size());
		sb.append('\n').append("\nValore medio delle misurazioni: ")
				.append(somma.divide(bd, 2, RoundingMode.CEILING)).append('\n');
		sb.append("Numero di esami: ").append(prestazioni.size()).append('\n');
		System.out.println(sb.toString());
		
		
	}
		
}
