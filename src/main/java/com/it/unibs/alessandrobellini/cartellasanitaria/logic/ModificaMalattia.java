package com.it.unibs.alessandrobellini.cartellasanitaria.logic;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Malattia;
import com.it.unibs.alessandrobellini.cartellasanitaria.session.ApplicationSession;
import com.it.unibs.alessandrobellini.cartellasanitaria.utils.InputDati;
import com.it.unibs.alessandrobellini.cartellasanitaria.utils.InsertDateUtils;

public class ModificaMalattia {
	public void execute() {
		ApplicationSession sessione = ApplicationSession.getIstance();
		CercaEsame cercaEsame = new CercaEsame();
		Long idMalattia = cercaEsame.ricercaMalattia();
		if (idMalattia == null) {
			return;
		}
		boolean inModifica = true;
		while (inModifica) {
			Malattia malattia = sessione.getMalattie().get(idMalattia);
			System.out.println();
			System.out.println("selezionare la voce da modificare : "
					+ "\n[0] annulla la modifica e torna al menù principale :"
					+ "\n[1] nome : \n" + malattia.getNome()    
					+ "\n[2] data inizio : \n" + malattia.getDataInizio() 
					+ "\n[3] data fine : \n" + malattia.getDataFine()
					+ "\n[4] sintomi : \n" + malattia.getSintomi()
					+ "\n[5] diagnosi : \n" + malattia.getDiagnosi()
					+ "\n[6] lista esami : \n" + malattia.getElencoEsami()
					+ "\n[7] terapia : \n" + malattia.getTerapia());
			System.out.println();
			
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner (System.in);
			String input = scanner.nextLine();
			
			if (!StringUtils.isNumeric(input)) {
				System.out.println("l'input non è un numero, inserire un numero della lista, riprova! \n");
				continue;
			}
			int inputNum = NumberUtils.toInt(input, -1);
			if((inputNum < 0) || (inputNum > 7)) {
				System.out.println(" il numero non è compreso tra quelli della lista, riprova! \n");
				continue;	//ritorna alla testa del ciclo e ripetilo
			}
				
			if(inputNum == 0) {
				inModifica = false;
			} else {
				Map <Long , Malattia> malattie = sessione.getMalattie();
				malattia = manageInput(inputNum,malattia);
				malattie.put(malattia.getIdMalattia(), malattia);
				sessione.setMalattie(malattie);
				System.out.println(" il dato è stato aggiornato, puoi aggiornare altri dati");
				SalvaCaricaDati.salvaDati();
					}
			}
		
		
		}

	private Malattia manageInput(int inputNum, Malattia malattia) {
		switch (inputNum) {
			case 1: {
				String nome = InputDati.leggiStringaNonVuota("inserisci il nuovo nome : \n");
				malattia.setNome(nome);
				break; //rompi lo switch ed esci da esso
			}
			case 2: {
				System.out.println("Modifica della data di inizio");
				Date dataCompleta = InsertDateUtils.richiediDataSoloGiorno();
				dataCompleta = InsertDateUtils.setMidday(dataCompleta);
				String dataFormattata = InsertDateUtils.printDateStandard(dataCompleta);
				
				malattia.setDataInizio(dataFormattata);		
							
				break; //rompi lo switch ed esci da esso
			}
			case 3: {
				System.out.println("Modifica della data di fine");
				Date dataCompleta = InsertDateUtils.richiediDataSoloGiorno();
				dataCompleta = InsertDateUtils.setMidday(dataCompleta);
				String dataFormattata = InsertDateUtils.printDateStandard(dataCompleta);
				
				malattia.setDataFine(dataFormattata);
				
				break; //rompi lo switch ed esci da esso
			}
			case 4: {
				String sintomi = InputDati.leggiStringaNonVuota("inserisci i sintomi : \n");
				malattia.setSintomi(sintomi);
				
				
				break; //rompi lo switch ed esci da esso
			}
			case 5: {
				String diagnosi = InputDati.leggiStringaNonVuota("inserisci diagnosi : \n");
				malattia.setDiagnosi(diagnosi);
				
				
				break; //rompi lo switch ed esci da esso
			}
			case 6: {
				
				malattia = manageEsami(malattia);
				break; //rompi lo switch ed esci da esso
			}
			case 7: {
				String terapia = InputDati.leggiStringaNonVuota("inserisci la terapia : \n");
				malattia.setTerapia(terapia);
				
				
				break; //rompi lo switch ed esci da esso
			}
		}
		
		return malattia;
		
	}
	
	
	private Malattia manageEsami(Malattia malattia) {
		String primoInput = InputDati.leggiStringaNonVuota("Inserisci 1 per aggiungere un nuovo esame "
				+ "o un altro carattere per aggiornarne uno esistente\n");
		if (primoInput != null && primoInput.equals("1")) {
			String nuovoEsame = InputDati.leggiStringaNonVuota("inserisci il nuovo esame\n");
			malattia.addEsame(nuovoEsame);
			
		} else {
			List<String> esami = malattia.getElencoEsami();
			
			if (esami == null || esami.size() == 0) {
				System.out.println("Non hai ancora associato nessun esame");
				return malattia;
			}
			
			int i = 0;
			StringBuffer sb = new StringBuffer("Lista degli esami:\n");
			
			for (String exm : esami) {
				i++;
				sb.append("[").append(i).append("] ").append(exm).append("\n");
				
			}
			
			sb.append("Seleziona l'esame da modificare, oppure digita 0 per annullare la modifica");
			System.out.println(sb.toString());
			
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			String input = scanner.nextLine();
			
			if (!StringUtils.isNumeric(input)) {
				System.out.println("l'input non è un numero, inserire un numero della lista, torno al menu di aggiornamento \n");
				return malattia;
			}
			int inputNum = NumberUtils.toInt(input, -1);
			if((inputNum < 0) || (inputNum > i)) {
				System.out.println(" il numero non è compreso tra quelli della lista, torno al menu di aggiornamento \n");
				return malattia;
			}
			
			if(inputNum == 0) {
				System.out.println("Modifica dell'esame annullata");
			} else {
				String nuovoEsame = InputDati.leggiStringaNonVuota("inserisci il nuovo nome dell'esame da aggiornare\n");
				esami.set(inputNum-1, nuovoEsame);
				malattia.setElencoEsami(esami);
			}
			
		}
		
		return malattia;
	}

}
