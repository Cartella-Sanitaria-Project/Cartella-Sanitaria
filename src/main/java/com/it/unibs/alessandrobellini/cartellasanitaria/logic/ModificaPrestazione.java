package com.it.unibs.alessandrobellini.cartellasanitaria.logic;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Malattia;
import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.PrestazioneEsame;
import com.it.unibs.alessandrobellini.cartellasanitaria.session.ApplicationSession;
import com.it.unibs.alessandrobellini.cartellasanitaria.utils.InputDati;
import com.it.unibs.alessandrobellini.cartellasanitaria.utils.PazienteUtils;

public class ModificaPrestazione {
	public void execute() {
		boolean val = true;
		while(val) {
				ApplicationSession sessione = ApplicationSession.getIstance();
				System.out.println("modifica della prestazione medica \n"
						+ "inserisci 0 o premere invio per tornare al menù principale "
						+ "scrivere un qualsiasi carattere (diverso da 0) per iniziare la modifica");
				String primoInput = InputDati.leggiStringaNonVuota("conferma : ");
				if(primoInput != null && primoInput.equals("0")) {
					return;
				}
				//private int N_FUNC_MAX = 4;
				CercaEsame idPrestazione = new CercaEsame();
				long id = idPrestazione.ricercaPrestazione();
				PrestazioneEsame iPrestazione = sessione.getPrestazioni().get(id);
				long tipoPrest = iPrestazione.getIdPrestazione();
				System.out.println("selezionare la voce da modificare : \n "
						+ "[1] data dell'esame : \n" + iPrestazione.getDataEsame()    
						+ "[2] luogo dell'esame : \n" + iPrestazione.getLuogoEsame() 
						+ "[3] malattia : \n" + iPrestazione.getIdMalattia()
						+ "[4] esito : \n" + iPrestazione.getEsito());
				
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
					iPrestazione = manageInput(inputNum,iPrestazione);
					prestazioni.put(tipoPrest, iPrestazione);
					sessione.setPrestazioni(prestazioni);
					System.out.println(" il dato è stato aggiornato, puoi aggiornare altri dati");
						}
				}
		}
		/*
		 * 
		 */
		public PrestazioneEsame manageInput(int inputNum , PrestazioneEsame iPrestazione) {
			switch (inputNum) {
			case 1: {
				String dataEsame = InputDati.leggiStringaNonVuota("inserire la nuova data ");
				while (!PazienteUtils.isDataValid(dataEsame)) {
					dataEsame = InputDati.leggiStringaNonVuota("reinserire la data ");
					iPrestazione.setDataEsame(dataEsame);
					}
					break; //rompi lo switch ed esci da esso
			}
			case 2: {
				String luogoEsame = InputDati.leggiStringaNonVuota("inserire il luogo dell'esame ");
				iPrestazione.setLuogoEsame(luogoEsame);
				break;  
			}
			case 3: {
				Malattia malattiaNuova = new Malattia();
				InserimentoMalattia modificaDati = new InserimentoMalattia();
				modificaDati.execute();
				//iPrestazione.setMalattia(modificaDati.);
				
				
				//Malattia malattia = InputDati.leggiStringaNonVuota("inserire la nuova ");
				 
			break; 
			}
			case 4: {
				String esito = InputDati.leggiStringaNonVuota("inserire il nuovo esito ");
				iPrestazione.setEsito(esito); 
			break; 
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + inputNum);
			} return iPrestazione;
		}
	}
					


