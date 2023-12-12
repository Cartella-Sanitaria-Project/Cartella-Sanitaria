package com.it.unibs.alessandrobellini.cartellasanitaria;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class Menu {
	
	private final static String MESSAGGIO_PRINCIPALE = "INSERIRE IL NUMERO DELLA FUNZIONALITA' \n"
			+ "[0] termina programma";
	private int nFuncMax = 0;
	
	
	
	
	
	
	
	public void menuPrincipale() {
		boolean onRunning = true;
		while (onRunning) {
		
			System.out.println(MESSAGGIO_PRINCIPALE);
			Scanner scanner  = new Scanner (System.in);
			String input = scanner.nextLine();
			scanner.close();
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
			
			
		
	}
	
	private void manageInput(int input) {
		switch (input) {
		case 1: {
 
		break; //rompi lo switch ed esci da esso
		}
		
		default:
			throw new IllegalArgumentException("Unexpected value: " + input);
		}
		
	}

}
