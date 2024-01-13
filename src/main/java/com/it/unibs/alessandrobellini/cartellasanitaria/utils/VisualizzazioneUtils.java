package com.it.unibs.alessandrobellini.cartellasanitaria.utils;

import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import com.it.unibs.alessandrobellini.cartellasanitaria.logic.CercaEsame;
import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Esame;
import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Malattia;
import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Paziente;
import com.it.unibs.alessandrobellini.cartellasanitaria.session.ApplicationSession;

/**
 * Classe di utilità per la gestione della visualizzazione di vare informazioni
 */
public class VisualizzazioneUtils {
	
	private final static String MESSAGGIO_PRINCIPALE = "INSERIRE IL NUMERO DELLA FUNZIONALITA' \n"
			+ "[0] termina al menù principale \n"
			+ "[1] Visualizzazione completa dei dati anagarfici \n"
			+ "[2] visualizzazione completa di un singolo esame, selezionato tra quelli presenti \n"
			+ "[3] visualizzazione completa di una singola malattia, selezionata tra quelle presenti \n";
	
	
	private final static int nFuncMax = 3;
	
	/**
	 * Menu principale per approfondire la visualizzazione dei dati.<br>
	 * Tramite questa funzione verrà richiesto all'utente di approfondire una sezione di dati
	 */
	public static void menuApprofondimento() {
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
		}
		//scanner.close();
		
		private static void manageInput(int input) {
			switch (input) {
			case 1: {
				datiAnagrafici();
				
			break; //rompi lo switch ed esci da esso
			}
			case 2: {
				CercaEsame esameDaStampare = new CercaEsame();
				singoloEsame(esameDaStampare.ricercaTipologia());
	 
			break; //rompi lo switch ed esci da esso
			}
			case 3: {
				CercaEsame malattiaDaStampare = new CercaEsame();
				singolaMalattia(malattiaDaStampare.ricercaMalattia());
				
	 
			break; //rompi lo switch ed esci da esso
			}
			
			default:
				throw new IllegalArgumentException("Unexpected value: " + input);
			}
			
			
			
 
			
		}
		
		/**
		 * Visualizzazione dei dati anagrafici legati al paziente
		 */
		public static void datiAnagrafici() {
			ApplicationSession sessione = ApplicationSession.getIstance();
			Paziente paziente = sessione.getPaziente();
			System.out.println(paziente.toString());
			}
		
		/**
		 * Visualizzazione dei dati legati ad un singolo esame passato
		 * in input
		 * @param idEsame - ID dell'esame da prendere in sessione stampare
		 */
		private static void singoloEsame(Long idEsame) {
			if (idEsame == null || idEsame == 0L) {
				System.out.println("Nessun esame trovato");
				return;
			}
			
			ApplicationSession sessione = ApplicationSession.getIstance();
			Map<Long, Esame> esami = sessione.getEsami();
			for (Map.Entry<Long, Esame> entry : esami.entrySet()) {
				Long key = entry.getKey();
				Esame esame = entry.getValue();
				if(idEsame.equals(key)) {
							System.out.println("Il nome dell'esame è : " + esame.getNome() + "\n"
							+ "L'id dell'esame è : " + esame.getIdEsame() + "\n"
							+ "L'esame è di tipo : " + esame.getTipologia() + "\n"
							+ "La preparazione necessaria è : " + esame.getPreparazione() + "\n"
							+ "Dopo l'esame si consiglia di: " + esame.getTrattamentiPostEsame() + "\n");
					if(esame.getTipologia().equals("PERIODICO")) {
							System.out.println("Il valore di normalità massima è : " + esame.getValoreNormalitaMax() + "\n"
							+ "Il valore di normalità minima è : " + esame.getValoreNormalitaMin() + "\n"
							+ "La soglia di errore ammessa è : " + esame.getSogliaErroreInserimento() + "\n");
					}
				}
			}
		}
		
		/**
		 * Visualizzazione dei dati legati ad una singola malattia passata
		 * in input
		 * @param idMalattia - ID della malattia da prendere in sessione stampare
		 */
		private static void singolaMalattia(Long idMalattia) {
			if (idMalattia == null || idMalattia == 0L) {
				System.out.println("Nessuna malattia trovata");
				return;
			}
			
			ApplicationSession sessione = ApplicationSession.getIstance();
			Map<Long, Malattia> malattie = sessione.getMalattie();
			for (Map.Entry<Long, Malattia> entry : malattie.entrySet()) {
				Long key = entry.getKey();
				Malattia malattia = entry.getValue();
				if (idMalattia.equals(key)) {
					System.out.println("Il nome della malattia è : " + malattia.getNome() + "\n"
							+ "L'id della malattia è : " + malattia.getIdMalattia() + "\n"
							+ "La data di inzio della malattia è : " + malattia.getDataInizio() + "\n"
							+ "La data di fine della malattia è : " + malattia.getDataFine() + "\n"
							+ "I sintomi della malattia sono : " + malattia.getSintomi() + "\n"
							+ "La diagnosi è : " + malattia.getDiagnosi() + "\n"
							+ "La terapia da eseguire : " + malattia.getTerapia() + "\n"
							+ "Gli esami richiesti sono : " + malattia.getElencoEsami() + "\n");
					}
				}
			}		
}

