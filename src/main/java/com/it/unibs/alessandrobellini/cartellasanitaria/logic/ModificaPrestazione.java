package com.it.unibs.alessandrobellini.cartellasanitaria.logic;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Esame;
import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Malattia;
import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.PrestazioneEsame;
import com.it.unibs.alessandrobellini.cartellasanitaria.session.ApplicationSession;
import com.it.unibs.alessandrobellini.cartellasanitaria.utils.InputDati;
import com.it.unibs.alessandrobellini.cartellasanitaria.utils.InsertDateUtils;

/**
 * Modifica di una prestazione medica precedentemente inserita.<br>
 * Viene richiesta la ricerca delle prestazioni tra quelle in lista di sessione.
 * 
 */
public class ModificaPrestazione implements FunzionalitaInterface {
	
	public void execute() {
		boolean val = true;
		ApplicationSession sessione = ApplicationSession.getIstance();
		System.out.println("modifica della prestazione medica \n"
				+ "inserisci 0 o premere invio per tornare al menù principale "
				+ "scrivere un qualsiasi carattere (diverso da 0) per iniziare la modifica");
		String primoInput = InputDati.leggiStringaNonVuota("conferma : ");
		if(primoInput != null && primoInput.equals("0")) {
			return;
		}
		CercaEsame cercaEsame = new CercaEsame();
		Long id = cercaEsame.ricercaPrestazione();
		if(id == null) {
			System.out.println("Ricerca della prestazione annullata ");
			return;
		}
		while(val) {
				PrestazioneEsame prestazioneObj = sessione.getPrestazioni().get(id);
				long idPrestazione = prestazioneObj.getIdPrestazione();
				Malattia malattia = null;
				if (prestazioneObj.getIdMalattia() != 0L)
					malattia = sessione.getMalattie().get(prestazioneObj.getIdMalattia());
				String nomeMalattia = "";
				if (malattia != null && malattia.getNome() != null)
					nomeMalattia = malattia.getNome();
				System.out.println();
				System.out.println("selezionare la voce da modificare : "
						+ "\n[0] annulla la modifica e torna al menù principale :"
						+ "\n[1] data dell'esame : \n" + prestazioneObj.getDataEsame()    
						+ "\n[2] luogo dell'esame : \n" + prestazioneObj.getLuogoEsame() 
						+ "\n[3] malattia : \n" + nomeMalattia
						+ "\n[4] esito : \n" + prestazioneObj.getEsito());
				System.out.println();
				
				Scanner scanner = new Scanner (System.in);
				String input = scanner.nextLine();
				
				if (!StringUtils.isNumeric(input)) {
					System.out.println("l'input non è un numero, inserire un numero della lista, riprova! \n");
					continue;
				}
				int inputNum = NumberUtils.toInt(input, -1);
				if((inputNum < 0) || (inputNum > 4)) {
					System.out.println(" il numero non è compreso tra quelli della lista, riprova! \n");
					continue;	//ritorna alla testa del ciclo e ripetilo
				}
				
				if(inputNum == 0) {
					val = false;
				} else {
					Map <Long , PrestazioneEsame> prestazioni = sessione.getPrestazioni();
					prestazioneObj = manageInput(inputNum,prestazioneObj);
					prestazioni.put(idPrestazione, prestazioneObj);
					sessione.setPrestazioni(prestazioni);
					System.out.println(" il dato è stato aggiornato, puoi aggiornare altri dati");
					SalvaCaricaDati.salvaDati();
						}
				}
		}
	
		/**
		 * Gestione del selettore e modifica dell'attributo nella struttura PrestazioneEsame
		 * 
		 */
		public PrestazioneEsame manageInput(int inputNum , PrestazioneEsame iPrestazione) {
			switch (inputNum) {
			case 1: {
				Date dataCompleta = InsertDateUtils.richiediData();
				String dataFormattata = InsertDateUtils.printDateStandard(dataCompleta);
				
				iPrestazione.setDataEsame(dataFormattata);
				
				break; //rompi lo switch ed esci da esso
			}
			case 2: {
				String luogoEsame = InputDati.leggiStringaNonVuota("inserire il luogo dell'esame ");
				iPrestazione.setLuogoEsame(luogoEsame);
				break;  
			}
			case 3: {
				Long idNuovaMalattia = new CercaEsame().ricercaMalattia();
				if(idNuovaMalattia != null) {
					iPrestazione.setIdMalattia(idNuovaMalattia);
				}
				 
			break; 
			}
			case 4: {
				String esito = InputDati.leggiStringaNonVuota("inserire il nuovo esito ");
				Esame esameSelezionato = ApplicationSession.getIstance().getEsami().get(iPrestazione.getIdEsame());
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
							 iPrestazione.setEsito(esito);
							 break;
						 }
						 
					 } catch (NumberFormatException e) {
						System.out.println("Il valore dell'esito deve essere numerico");
					 }
				}
			break; 
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + inputNum);
			} return iPrestazione;
		}
	}
					


