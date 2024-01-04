package com.it.unibs.alessandrobellini.cartellasanitaria;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.it.unibs.alessandrobellini.cartellasanitaria.logic.AggiornaDati;
import com.it.unibs.alessandrobellini.cartellasanitaria.logic.AggiungiEsame;
import com.it.unibs.alessandrobellini.cartellasanitaria.logic.InizializzaDati;
import com.it.unibs.alessandrobellini.cartellasanitaria.logic.InserimentoMalattia;
import com.it.unibs.alessandrobellini.cartellasanitaria.logic.InserimentoPrestazione;
import com.it.unibs.alessandrobellini.cartellasanitaria.logic.ModificaMalattia;
import com.it.unibs.alessandrobellini.cartellasanitaria.logic.ModificaPrestazione;
import com.it.unibs.alessandrobellini.cartellasanitaria.logic.VisualizzazioneSinteticaUtente;
import com.it.unibs.alessandrobellini.cartellasanitaria.utils.VisualizzazioneEsitiEsamiPeriodici;

public class Menu {
	
	private final static String MESSAGGIO_PRINCIPALE = "INSERIRE IL NUMERO DELLA FUNZIONALITA' \n"
			+ "[0] termina programma \n"
			+ "[1] inserisci dati paziente \n"
			+ "[2] aggiorna dati paziente \n"
			+ "[3] aggiungi nuovo esame medico \n"
			+ "[4] aggiungi nuova prestazione medica \n"
	        + "[5] aggiungi una nuova malattia \n"
	        + "[6] modifica prestazione medica \n"
	        + "[7] modifica malattia \n"
	        + "[8] visualizza dati paziente \n"
	        + "[9] visulizzazione degli esiti misurabili degli esami periodici \n";
	        
	
			
	private int nFuncMax = 9;
	
	
	
	
	
	
	
	public void menuPrincipale() {
		boolean onRunning = true;
		Scanner scanner = null;
		while (onRunning) {
		
			System.out.println(MESSAGGIO_PRINCIPALE);
			scanner = new Scanner(System.in);
			String input = scanner.nextLine();
			if (!StringUtils.isNumeric(input)) {
				System.out.println("l'input non è un numero, inserire un numero della lista");
				continue;
			}
			int inputNum = NumberUtils.toInt(input, -1);
			if((inputNum < 0) || (inputNum > nFuncMax)) {
				System.out.println(" il numero non è compreso tra quelli della lista");
				continue;	//ritorna alla testa del ciclo e ripetilo
			}
			if(inputNum == 0) {
				onRunning = false;
			} else {
				manageInput(inputNum);
			}
			
		}
		scanner.close();
			
			
		
	}
	
	private void manageInput(int input) {
		switch (input) {
		case 1: {
			InizializzaDati init = new InizializzaDati();
			init.execute();
 
		break; //rompi lo switch ed esci da esso
		}
		case 2: {
			AggiornaDati aggiorna = new AggiornaDati();
			aggiorna.execute();
 
		break; //rompi lo switch ed esci da esso
		}
		case 3: {
			AggiungiEsame aggiungi = new AggiungiEsame();
			aggiungi.execute();
 
		break; //rompi lo switch ed esci da esso
		}
		case 4: {
			InserimentoPrestazione inserisci = new InserimentoPrestazione();
			inserisci.execute();
 
		break; //rompi lo switch ed esci da esso
		}
		case 5: {
			InserimentoMalattia inserisciMalattia = new InserimentoMalattia();
			inserisciMalattia.execute();
 
		break; //rompi lo switch ed esci da esso
		}
		case 6: {
			ModificaPrestazione inserisciModifica = new ModificaPrestazione();
			inserisciModifica.execute();
 
		break; //rompi lo switch ed esci da esso
		}
		case 7: {
			ModificaMalattia modificaMalattia = new ModificaMalattia();
			modificaMalattia.execute();
 
		break; //rompi lo switch ed esci da esso
		}
		case 8: {
			VisualizzazioneSinteticaUtente visualizzaDati = new VisualizzazioneSinteticaUtente();
			visualizzaDati.execute();
 
		break; //rompi lo switch ed esci da esso
		}
		case 9: {
			VisualizzazioneEsitiEsamiPeriodici visualizzaEsiti = new VisualizzazioneEsitiEsamiPeriodici();
			visualizzaEsiti.execute();
 
		break; //rompi lo switch ed esci da esso
		}
		
		default:
			throw new IllegalArgumentException("Unexpected value: " + input);
		}
		
	}

}
