package com.it.unibs.alessandrobellini.cartellasanitaria.logic;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Malattia;
import com.it.unibs.alessandrobellini.cartellasanitaria.session.ApplicationSession;
import com.it.unibs.alessandrobellini.cartellasanitaria.utils.InputDati;
import com.it.unibs.alessandrobellini.cartellasanitaria.utils.InsertDateUtils;
import com.it.unibs.alessandrobellini.cartellasanitaria.utils.PazienteUtils;

public class InserimentoMalattia {
	
	public void execute() {
		ApplicationSession sessione = ApplicationSession.getIstance();
		
		System.out.println("inserimento di un nuova malattia \n"
				+ "inserisci 0 per tornare al menù principale oppure un altro carattere "
				+ "per iniziare l'inserimento");
		String primoInput = InputDati.leggiStringaNonVuota("conferma : ");
		if(primoInput != null && primoInput.equals("0")) {
			return;
		}
		
		Malattia malattia = new Malattia();
		List<String> elencoEsami = new ArrayList<>();
		String nome = InputDati.leggiStringaNonVuota(" inserisci nome : \n");
		
		String dataInizio = InputDati.leggiStringaNonVuota(" inserisci data di inizio nel formato yyyy-MM-dd della malattia : \n");
		while (!PazienteUtils.isDataValid(dataInizio)) {
				System.out.println("data inserita non valida");
				dataInizio = InputDati.leggiStringaNonVuota(" inserisci data di inizio nel formato yyyy-MM-dd della malattia : \n");
		}
		Date dataInizioDate = InsertDateUtils.castDateGiorno(dataInizio);
		
		String dataFine = InputDati.leggiStringa(" inserisci la data dell'eventuale fine nel formato yyyy-MM-dd (digitare nessuna o 0 oppure premere invio per non inserire nessuna data di fine malattia  : \n");
		while (!PazienteUtils.isDataValidOnull(dataFine)) {
			System.out.println("data inserita non valida");
			dataFine = InputDati.leggiStringa(" inserisci data di fine nel formato yyyy-MM-dd della malattia : \n");
		}
		String sintomi = InputDati.leggiStringaNonVuota(" inserisci i sintomi : \n");
		String diagnosi = InputDati.leggiStringaNonVuota(" inserisci la diagnosi : \n");
		boolean daAggiungere = true;
		while (daAggiungere) {
			
			String esameDaAggiungere = InputDati.leggiStringaNonVuota(" inserisci il nome dell'esame collegato alla malattia,  digitare 0 o non inserire nulla premendo invio per interrompere l'inserimento: \n");
			if (esameDaAggiungere==null || esameDaAggiungere.equals("0") || esameDaAggiungere.isEmpty()) {
				daAggiungere = false;
			}
			else elencoEsami.add(esameDaAggiungere);
		}
		
		//inserire elenco degli esami necessari
		String terapia = InputDati.leggiStringaNonVuota(" inserisci la terapia : \n");
		
		
		malattia.setIdMalattia(System.currentTimeMillis());
		malattia.setNome(nome);
		dataInizioDate = InsertDateUtils.setMidday(dataInizioDate);
		malattia.setDataInizio(InsertDateUtils.printDateStandard(dataInizioDate));
		if (dataFine != null && !dataFine.isEmpty() && !dataFine.trim().contains("0")
				&& !dataFine.contains("nessuna")) {
			Date dataFineDate = InsertDateUtils.castDateGiorno(dataFine);
			dataFineDate = InsertDateUtils.setMidday(dataFineDate);
			malattia.setDataFine(InsertDateUtils.printDateStandard(dataFineDate));
		}
		malattia.setSintomi(sintomi);
		malattia.setDiagnosi(diagnosi);
		malattia.setTerapia(terapia);
		malattia.setElencoEsami(elencoEsami);
		
		
		sessione.addMalattia(malattia.getIdMalattia(), malattia);
		SalvaCaricaDati.salvaDati();
		
	}

}
