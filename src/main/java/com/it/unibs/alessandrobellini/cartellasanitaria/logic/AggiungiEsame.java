package com.it.unibs.alessandrobellini.cartellasanitaria.logic;

import java.math.BigDecimal;

import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Esame;
import com.it.unibs.alessandrobellini.cartellasanitaria.session.ApplicationSession;
import com.it.unibs.alessandrobellini.cartellasanitaria.utils.InputDati;

/**
 * Classe che aggiunge un esame medico (Struttura con informazioni mediche - non prestazione del paziente)
 */
public class AggiungiEsame implements FunzionalitaInterface {
	public void execute () {
		System.out.println("inserimento di un nuovo esame medico\n"
				+ "inserisci 0 per tornare al menù principale oppure un altro carattere "
				+ "per iniziare l'inserimento");
		String primoInput = InputDati.leggiStringaNonVuota("conferma : ");
		if(primoInput != null && primoInput.equals("0")) {
			return;
		}
		
		Esame esame = new Esame ();
		String nome = InputDati.leggiStringaNonVuota(" inserisci nome : \n");
		String tipologia = InputDati.leggiStringaNonVuota(" inserisci tipologia (PERIODICO o DIAGNOSTICO) : \n");
		while (!isTipologiaValid(tipologia)) {
			tipologia = InputDati.leggiStringaNonVuota("tipologia non valida\n"
					+ "  inserisci tipologia (PERIODICO o DIAGNOSTICO) : \n");
		}
		tipologia = tipologia.toUpperCase();
		String preparazione = InputDati.leggiStringaNonVuota(" inserisci descrizione di preparazione medica  : \n");
		if(tipologia.equals("PERIODICO")) {
			BigDecimal valoreNormalitaMin = new BigDecimal(Integer.toString(InputDati.leggiIntero(" inserisci valore di normalità minimo  : \n")));
			BigDecimal valoreNormalitaMax = new BigDecimal(Integer.toString(InputDati.leggiIntero(" inserisci valore di normalità massimo  : \n")));
			BigDecimal sogliaErroreInserimento = new BigDecimal(Integer.toString(InputDati.leggiIntero(" inserisci valore di soglia di errore  : \n")));
			
			if (sogliaErroreInserimento == null || sogliaErroreInserimento
					.equals(BigDecimal.ZERO)) {
				sogliaErroreInserimento = valoreNormalitaMax.multiply(new BigDecimal("5"));
			}
			
			esame.setValoreNormalitaMin(valoreNormalitaMin);
			esame.setValoreNormalitaMax(valoreNormalitaMax);
			esame.setSogliaErroreInserimento(sogliaErroreInserimento);
			
		}
		String trattamentiPostEsame = InputDati.leggiStringa(" inserisci descrizione trattamenti post esame : \n");
		esame.setIdEsame(System.currentTimeMillis());
		esame.setNome(nome);
		esame.setTipologia(tipologia);
		esame.setPreparazione(preparazione);
		esame.setTrattamentiPostEsame(trattamentiPostEsame);
		
		ApplicationSession sessione = ApplicationSession.getIstance(); // prendiamo l'istanza della sessione 
		sessione.addEsame(esame.getIdEsame(), esame);
		SalvaCaricaDati.salvaDati();
		
		
		
	}
	
	
	/**
	 * Esame può essere di una tipologia (DIAGNOSTICO o PERIODICO).<br>
	 * La funzione controlla se l'input è di una tipologia valida
	 * @param tipologia - da controllare
	 * @return booleano indicante se la tipologia inserita è corretta
	 */
	private boolean isTipologiaValid(String tipologia) {
		if(tipologia == null || tipologia.isEmpty()) {
			return false;
		}
		tipologia = tipologia.toUpperCase();
		if (!tipologia.equals("DIAGNOSTICO") && !tipologia.equals("PERIODICO")) {
			return false;
		}
		return true;
	}

}
