package com.it.unibs.alessandrobellini.cartellasanitaria;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.it.unibs.alessandrobellini.cartellasanitaria.logic.AggiornaDati;
import com.it.unibs.alessandrobellini.cartellasanitaria.logic.InizializzaDati;

public class Menu {
	
	private final static String MESSAGGIO_PRINCIPALE = "INSERIRE IL NUMERO DELLA FUNZIONALITA' \n"
			+ "[0] termina programma \n"
			+ "[1] inserisci dati paziente \n"
			+ "[2] aggiorna dati paziente \n";
	private int nFuncMax = 2;
	
	
	
	
	
	
	
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
		
		default:
			throw new IllegalArgumentException("Unexpected value: " + input);
		}
		
	}

}
