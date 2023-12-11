package com.it.unibs.alessandrobellini.cartellasanitaria.logic;

import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.GruppoSanguinio;
import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Paziente;
import com.it.unibs.alessandrobellini.cartellasanitaria.session.ApplicationSession;
import com.it.unibs.alessandrobellini.cartellasanitaria.utils.InputDati;
import com.it.unibs.alessandrobellini.cartellasanitaria.utils.PazienteUtils;

public class InizializzaDati {
	public void execute() {
		ApplicationSession sessione = ApplicationSession.getIstance();
		
		Paziente paziente = new Paziente();
		String  nome = InputDati.leggiStringaNonVuota("Inserisci il nome \n");
		String  cognome = InputDati.leggiStringaNonVuota("Inserisci il cognome \n");
		String  indirizzo = InputDati.leggiStringaNonVuota("Inserisci l'indirizzo \n");
		String  telefono = InputDati.leggiStringaNonVuota("Inserisci il numero di telefono \n");
		String  eMail = InputDati.leggiStringaNonVuota("Inserisci l'e-mail \n");
		String  dataNascita = InputDati.leggiStringaNonVuota("Inserisci la data nel formato yyyy-MM-dd \n");
		String  luogoDiNascita = InputDati.leggiStringaNonVuota("Inserisci il luogo di nascita \n");
		String  genere = InputDati.leggiStringaNonVuota("Inserisci il genere \n");
		String fattoreRh = InputDati.leggiStringaNonVuota("Inserisci il fattore Rh ( positivo o negativo ) \n"); 
		String  gruppo = InputDati.leggiStringaNonVuota("Inserisci il gruppo sanguinio ( 0 A B AB) \n");
		String  codiceFiscale = InputDati.leggiStringaNonVuota("Inserisci il codice fiscale \n");
		String codiceSanitario = PazienteUtils.genCodiceSanitario();
		
		paziente.setNome(nome);
		paziente.setCognome(cognome);
		paziente.setIndirizzo(indirizzo);
		paziente.addTelefono(telefono);
		paziente.setEmail(eMail);
		while(!PazienteUtils.isDataNascitaValid(dataNascita)) {
			dataNascita = InputDati.leggiStringaNonVuota("Inserisci la data nel formato yyyy-MM-dd \n");
		}
		paziente.setDataNascita(dataNascita);//verifica
		paziente.setLuogoNascita(luogoDiNascita);
		paziente.setGenere(genere);
		GruppoSanguinio gruppoSangue = new GruppoSanguinio(fattoreRh , gruppo);
		paziente.setGruppoSanguinio(gruppoSangue);  
		while(!PazienteUtils.isCFValid(codiceFiscale)) {
			System.out.println("codice fiscale inserito non valido");
			codiceFiscale = InputDati.leggiStringaNonVuota("Inserisci il codice fiscale\n");
		}
		paziente.setCodiceFiscale(codiceFiscale);
		paziente.setCodiceSanitario(codiceSanitario);
		//abbiamo creato il nostor paziente ora lo salviamo in sessione
		sessione.setPaziente(paziente);
		
	}
	

}
