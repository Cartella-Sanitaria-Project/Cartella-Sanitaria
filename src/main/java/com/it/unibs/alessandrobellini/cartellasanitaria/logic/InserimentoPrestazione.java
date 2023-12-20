package com.it.unibs.alessandrobellini.cartellasanitaria.logic;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;

import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Esame;
import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Paziente;
import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.PrestazioneEsame;
import com.it.unibs.alessandrobellini.cartellasanitaria.session.ApplicationSession;
import com.it.unibs.alessandrobellini.cartellasanitaria.utils.InputDati;
import com.it.unibs.alessandrobellini.cartellasanitaria.utils.PazienteUtils;

public class InserimentoPrestazione {
	/*
	 * creare una mappa hash per posizionare le prestazioni, ogni prestazione avrà un suo id 
	 * 
	 */
	
	public void execute() {
		
			
		
		    ApplicationSession sessione = ApplicationSession.getIstance();
		    //sessione.addPrestazione(null, null);
		    System.out.println("inserimento di una nuova prestazione medica\n"
					+ "inserisci 0 per tornare al menù principale oppure un altro carattere "
					+ "per iniziare l'inserimento");
			String primoInput = InputDati.leggiStringaNonVuota("conferma : ");
			if(primoInput != null && primoInput.equals("0"))
				return;
			
			CercaEsame idEsameCercato = new CercaEsame();
			long id = idEsameCercato.ricercaTipologia();
			Esame esameSelezionato = sessione.getEsami().get(id);
			String tipoEsame = esameSelezionato.getTipologia();
			PrestazioneEsame prestazione = new PrestazioneEsame();
			prestazione.setIdEsame(id);
			boolean dataCorretta= false;
			Calendar dataCompleta = Calendar.getInstance();
			while (!dataCorretta) {
					String dataGiorno = InputDati.leggiStringaNonVuota("inserisci il giorno dell'esame in formato yyyy-MM-dd : ");
					dataCorretta = PazienteUtils.isDataValid(dataGiorno);
					if(!dataCorretta) System.out.println("la data inserita non è corretta");
					else {
						DateFormat dFday = new SimpleDateFormat("yyyy-MM-dd");
						try {
							Date date = dFday.parse(dataGiorno);
							dataCompleta.setTime(date);
						} catch (ParseException e) {
							System.out.println("Errore di parsing della data (giorno)");
							return;
						}
						
					}
			}
			dataCorretta = false;
			while (!dataCorretta) {
				String dataOra = InputDati.leggiStringaNonVuota("inserisci l'orario dell'esame in formato hh:mm : ");
				dataCorretta = PazienteUtils.isHoursValid(dataOra);
				if(!dataCorretta) System.out.println("la data inserita non è corretta");
				else {
					DateFormat dFday = new SimpleDateFormat("HH:mm");
					try {
						Date date = dFday.parse(dataOra);
						Calendar cal = Calendar.getInstance();
						cal.setTime(date);
						dataCompleta.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
						dataCompleta.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
					} catch (ParseException e) {
						System.out.println("Errore di parsing della data (orario)");
						return;
					}
				}
			}
			DateFormat dF = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ssZ");
			String dataFormattata = dF.format(dataCompleta.getTime());
			prestazione.setDataEsame(dataFormattata);
			
			String luogo = InputDati.leggiStringaNonVuota("Inserisci il luogo della prestazione");
			prestazione.setLuogoEsame(luogo);
			
			boolean rispostaCorretta = false;
			Long idMalattia = null;
			while (!rispostaCorretta) {
				String risposta = InputDati.leggiStringaNonVuota("La prestazione è legata a una malattia? Y/N");
				if (risposta.equalsIgnoreCase("Y")) {
					idMalattia = new CercaEsame().ricercaMalattia();
					rispostaCorretta = true;
				} else if (risposta.equalsIgnoreCase("N")) {
					System.out.println("Ricerca della malattia saltata");
					rispostaCorretta = true;
				} else {
					System.out.println("Input non corretto, inserisci Y o N");
				}
			}
			
			if (idMalattia != null) {
				prestazione.setIdMalattia(idMalattia);
			}
			
			 if (tipoEsame.toUpperCase().equals("DIAGNOSTICO")) {
				 String esito = InputDati.leggiStringa("Inserisci l'esito della prestazione (descrizione stringa ), puoi lasciare questo campo anche vuoto");
				 if (esito != null && !esito.isEmpty()){
					 prestazione.setEsito(esito);
				 }
				
				 
			 }
			 if (tipoEsame.toUpperCase().equals("PERIODICO")) {
				 
				 do {
					 String esito = InputDati.leggiStringa("Inserisci l'esito della prestazione (Valore numerico), puoi lasciare questo campo anche vuoto");
					 if (esito != null && !esito.isEmpty()){
						 try { 
							 BigDecimal esitoNumerico = NumberUtils.createBigDecimal(esito);
							 BigDecimal normMin = esameSelezionato.getValoreNormalitaMin(),
									 normMax = esameSelezionato.getValoreNormalitaMax(),
									 soglia = esameSelezionato.getSogliaErroreInserimento();
							 
							 BigDecimal valoreErroreMinimo = normMin.subtract(soglia);
							 BigDecimal valoreErroreMassimo = normMax.add(soglia);
							 
							 if (esitoNumerico.compareTo(valoreErroreMinimo) < 0) {
								 System.out.println("Valore troppo basso rispetto alla soglia di errore");
							 } else if (esitoNumerico.compareTo(valoreErroreMassimo) > 0) {
								 System.out.println("Valore troppo alto rispetto alla soglia di errore");
							 } else {
								 prestazione.setEsito(esito);
								 break;
							 }
							 
						 } catch (NumberFormatException e) {
							System.out.println("Il valore dell'esito deve essere numerico");
						 }
					 } else {
						 break;
					 }
				 } while (true);
				 
			 }
			 long idPrestazione = System.currentTimeMillis();
			 prestazione.setIdPrestazione(idPrestazione);
			
			 sessione.addPrestazione(idPrestazione, prestazione);
		
		}
	}

			
			
	
	


