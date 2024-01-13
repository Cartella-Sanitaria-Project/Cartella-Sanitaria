package com.it.unibs.alessandrobellini.cartellasanitaria;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.it.unibs.alessandrobellini.cartellasanitaria.logic.AggiornaDati;
import com.it.unibs.alessandrobellini.cartellasanitaria.logic.AggiungiEsame;
import com.it.unibs.alessandrobellini.cartellasanitaria.logic.FunzionalitaInterface;
import com.it.unibs.alessandrobellini.cartellasanitaria.logic.InizializzaDati;
import com.it.unibs.alessandrobellini.cartellasanitaria.logic.InserimentoMalattia;
import com.it.unibs.alessandrobellini.cartellasanitaria.logic.InserimentoPrestazione;
import com.it.unibs.alessandrobellini.cartellasanitaria.logic.ModificaMalattia;
import com.it.unibs.alessandrobellini.cartellasanitaria.logic.ModificaPrestazione;
import com.it.unibs.alessandrobellini.cartellasanitaria.logic.VisualizzazioneEsameDiagnostico;
import com.it.unibs.alessandrobellini.cartellasanitaria.logic.VisualizzazioneEsitiEsamiPeriodici;
import com.it.unibs.alessandrobellini.cartellasanitaria.logic.VisualizzazioneSinteticaUtente;

/**
 * Menu principale e contestualle di tutte le funzioni disponibili
 * all'utente
 */
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
	        + "[9] visulizzazione degli esiti misurabili degli esami periodici \n"
	        + "[10] visualizzazione degli esami diagnostici";
	        
	
			
	private int nFuncMax = 10;
	
	
	
	
	
	
	/**
	 * 
	 * Richiede un numero all'utente per selezionare la funzionalità,
	 * controlla l'input e esegue una classe di logica
	 * 
	 */
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
	
	/**
	 * 
	 * Gestisce in base all'input l'avvio di una funzionalità
	 * 
	 * @param input - numero della funzionalità (selettore)
	 */
	private void manageInput(int input) {
		switch (input) {
		case 1: {
			FunzionalitaInterface init = new InizializzaDati();
			init.execute();
 
		break; //rompi lo switch ed esci da esso
		}
		case 2: {
			FunzionalitaInterface aggiorna = new AggiornaDati();
			aggiorna.execute();
 
		break; //rompi lo switch ed esci da esso
		}
		case 3: {
			FunzionalitaInterface aggiungi = new AggiungiEsame();
			aggiungi.execute();
 
		break; //rompi lo switch ed esci da esso
		}
		case 4: {
			FunzionalitaInterface inserisci = new InserimentoPrestazione();
			inserisci.execute();
 
		break; //rompi lo switch ed esci da esso
		}
		case 5: {
			FunzionalitaInterface inserisciMalattia = new InserimentoMalattia();
			inserisciMalattia.execute();
 
		break; //rompi lo switch ed esci da esso
		}
		case 6: {
			FunzionalitaInterface inserisciModifica = new ModificaPrestazione();
			inserisciModifica.execute();
 
		break; //rompi lo switch ed esci da esso
		}
		case 7: {
			FunzionalitaInterface modificaMalattia = new ModificaMalattia();
			modificaMalattia.execute();
 
		break; //rompi lo switch ed esci da esso
		}
		case 8: {
			FunzionalitaInterface visualizzaDati = new VisualizzazioneSinteticaUtente();
			visualizzaDati.execute();
 
		break; //rompi lo switch ed esci da esso
		}
		case 9: {
			FunzionalitaInterface visualizzaEsiti = new VisualizzazioneEsitiEsamiPeriodici();
			visualizzaEsiti.execute();
 
		break; //rompi lo switch ed esci da esso
		}
		case 10: {
			FunzionalitaInterface visEsame = new VisualizzazioneEsameDiagnostico();
			visEsame.execute();
		break;
		}
		
		default:
			throw new IllegalArgumentException("Unexpected value: " + input);
		}
		
	}

}
