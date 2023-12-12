package com.it.unibs.alessandrobellini.cartellasanitaria.logic;

import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.GruppoSanguinio;
import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Paziente;
import com.it.unibs.alessandrobellini.cartellasanitaria.session.ApplicationSession;
import com.it.unibs.alessandrobellini.cartellasanitaria.utils.InputDati;

public class AggiornaDati {
	
	private final static String MESSAGGIO_AGGIORNAMENTO = "Digitare 0 per non aggiornare i dati precedentemente inseriti\n OPPURE \ndigitare l'indice numerico associato ad ogni singolo dato da aggiornare\n";
	private final static int N_FUNC_MAX = 11;
	
	public void execute() {
		ApplicationSession sessione = ApplicationSession.getIstance();
		
		Paziente paziente = sessione.getPaziente();
		
		
		boolean onRunning = true;
		System.out.println(MESSAGGIO_AGGIORNAMENTO);
		Scanner scanner = null;
		while(onRunning) {
			String messaggioInit = "[1] Nome: " + paziente.getNome() + "\n"
					+ "[2] Cognome: " + paziente.getCognome() + "\n"
					+ "[3] Indirizzo: " + paziente.getIndirizzo() + "\n"
				    + "[4] Telefono: " + paziente.getTelefono() + "\n"
				    + "[5] Email: " + paziente.getEmail() + "\n"
				    + "[6] Data di nascita: " + paziente.getDataNascita() + "\n"
				    + "[7] Luogo di nascita: " + paziente.getLuogoNascita() + "\n"
			        + "[8] Genere: " + paziente.getGenere() + "\n"
			        + "[9] Fattore Rh: " + paziente.getGruppoSanguinio().getFattoreRh() + "\n"
			        + "[10] Gruppo sanguinio: " + paziente.getGruppoSanguinio().getGruppo() + "\n"
			        + "[11] Codice fiscale: " + paziente.getCodiceFiscale() + "\n"
			        + "[0] uscita, torna al menù principale \n";
			System.out.println(messaggioInit);
			scanner = new Scanner (System.in);
			String input = scanner.nextLine();
			
			if (!StringUtils.isNumeric(input)) {
				System.out.println("l'input non è un numero, inserire un numero della lista, riprova! \n");
				continue;
			}
			int inputNum = NumberUtils.toInt(input, -1);
			if((inputNum < 0) || (inputNum > N_FUNC_MAX)) {
				System.out.println(" il numero non è compreso tra quelli della lista, riprova! \n");
				continue;	//ritorna alla testa del ciclo e ripetilo
			}
			
			if(inputNum == 0) {
				onRunning = false;
			} else {
				paziente = manageInput(inputNum, paziente);
				sessione.setPaziente(paziente);
				System.out.println(" il dato è stato aggiornato, puoi aggiornare altri dati");
			}
		
		}
	
		
	}
	
	private Paziente manageInput(int input, Paziente paziente) {
		switch (input) {
		case 1: {
			String nome = InputDati.leggiStringaNonVuota("Inserisci di nuovo nome \n");
		    paziente.setNome(nome);
 
		break; //rompi lo switch ed esci da esso
		}
		case 2: {
			String  cognome = InputDati.leggiStringaNonVuota("Inserisci di nuovo cognome \n");
		    paziente.setCognome(cognome);
			 
		break;  
		}
		case 3: {
			String  indirizzo = InputDati.leggiStringaNonVuota("Inserisci di nuovo l'indirizzo \n");
			paziente.setIndirizzo(indirizzo);
			 
		break; 
		}
		case 4: {
			paziente = manageTelefono(paziente);
			 
		break; 
		}
		case 5: {
			String  eMail = InputDati.leggiStringaNonVuota("Inserisci di nuovo l'e-mail \n");
			paziente.setEmail(eMail);
			 
		break; 
		}
		case 6: {
			String  dataNascita = InputDati.leggiStringaNonVuota("Inserisci di nuovo la data nel formato yyyy-MM-dd \n");
			paziente.setDataNascita(dataNascita);
			 
		break; 
		}
		case 7: {
			String  luogoDiNascita = InputDati.leggiStringaNonVuota("Inserisci di nuovo il luogo di nascita \n");
			paziente.setLuogoNascita(luogoDiNascita);
			 
		break; 
		}
		case 8: {
			String  genere = InputDati.leggiStringaNonVuota("Inserisci di nuovo il genere \n");
			paziente.setGenere(genere);
			 
		break; 
		}
		case 9: {
			String fattoreRh = InputDati.leggiStringaNonVuota("Inserisci di nuovo il fattore Rh ( positivo o negativo ) \n"); 
			GruppoSanguinio gs = paziente.getGruppoSanguinio();
			gs.setFattoreRh(fattoreRh);
			paziente.setGruppoSanguinio(gs);
			 
		break; 
		}
		case 10: {
			String  gruppo = InputDati.leggiStringaNonVuota("Inserisci di nuovo il gruppo sanguinio ( 0 A B AB) \n");
			GruppoSanguinio gs = paziente.getGruppoSanguinio();
			gs.setGruppo(gruppo);
			paziente.setGruppoSanguinio(gs);
			 
		break; 
		}
		case 11: {
			String  codiceFiscale = InputDati.leggiStringaNonVuota("Inserisci di nuovo il codice fiscale \n");
			paziente.setCodiceFiscale(codiceFiscale);
			 
		break; 
		}

		
		default:
			throw new IllegalArgumentException("Unexpected value: " + input);
		}
		return paziente;
	}
	
	
	
	private Paziente manageTelefono(Paziente paziente) {
		String primoInput = InputDati.leggiStringaNonVuota("Inserisci 1 per aggiungere un nuovo numero "
				+ "o un altro carattere per aggiornarne uno esistente\n");
		if (primoInput != null && primoInput.equals("1")) {
			String nuovoNumero = InputDati.leggiStringaNonVuota("inserisci il nuovo numero\n");
			paziente.addTelefono(nuovoNumero);
			
		} else {
			List<String> telefoni = paziente.getTelefono();
			
			if (telefoni == null || telefoni.size() == 0) {
				System.out.println("Non hai ancora associato nessun numero di telefono");
				return paziente;
			}
			
			int i = 0;
			StringBuffer sb = new StringBuffer("Lista dei telefoni:\n");
			
			for (String tel : telefoni) {
				i++;
				sb.append("[").append(i).append("] ").append(tel).append("\n");
				
			}
			
			sb.append("Seleziona il numero di telefono da modificare, oppure digita 0 per annullare la modifica");
			System.out.println(sb.toString());
			
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			String input = scanner.nextLine();
			
			if (!StringUtils.isNumeric(input)) {
				System.out.println("l'input non è un numero, inserire un numero della lista, torno al menu di aggiornamento \n");
				return paziente;
			}
			int inputNum = NumberUtils.toInt(input, -1);
			if((inputNum < 0) || (inputNum > i)) {
				System.out.println(" il numero non è compreso tra quelli della lista, torno al menu di aggiornamento \n");
				return paziente;
			}
			
			if(inputNum == 0) {
				System.out.println("Modifica del numero di telefono annullata");
			} else {
				String nuovoNumero = InputDati.leggiStringaNonVuota("inserisci il nuovo numero da aggiornare\n");
				telefoni.set(i-1, nuovoNumero);
				paziente.setTelefono(telefoni);
			}
			
		}
		
		return paziente;
	}
	

}
