package com.it.unibs.alessandrobellini.cartellasanitaria.utils;

import java.util.Map;
import java.util.Scanner;

import javax.xml.crypto.Data;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.it.unibs.alessandrobellini.cartellasanitaria.logic.CercaEsame;
import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Esame;
import com.it.unibs.alessandrobellini.cartellasanitaria.session.ApplicationSession;

public class VisualizzazioneEsitiEsamiPeriodici {
	public static void execute() {
		ApplicationSession sessione = ApplicationSession.getIstance();
		CercaEsame esameDaStampare = new CercaEsame();
		Long idEsame = esameDaStampare.ricercaTipologia();
		Map<Long, Esame> esami = sessione.getEsami();
		for (Map.Entry<Long, Esame> entry : esami.entrySet()) {
			Long key = entry.getKey();
			Esame esame = entry.getValue();
			if(!esame.getTipologia().equals("PERIODICO")) {
				System.out.println("Seleziona un esame di tipo PERIODICO e ricomincia la ricerca \n");
				return;
			} else if(esame.getTipologia().equals("PERIODICO")) {
				boolean onRunning = true;
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
						//implementare
					} else if (inputNum == 1){
						//implementare
						
					}
					
				}
						
				}
			}
		}
	private Data filtraDate() {
		return null;
	}
		
	}
