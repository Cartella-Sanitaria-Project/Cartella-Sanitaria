package com.it.unibs.alessandrobellini.cartellasanitaria.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Esame;
import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.GruppoSanguinio;
import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Malattia;
import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Paziente;
import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.PrestazioneEsame;
import com.it.unibs.alessandrobellini.cartellasanitaria.session.ApplicationSession;

/**
 * Questa classe agisce su file locale JSON, carica o scrive/sovrascrive il file
 */
public class SalvaCaricaDati {
	
	/**
	 * Salva i dati di sessione su file JSON.<br>
	 * In caso il file esista già lo sovrascrive.
	 */
	public static void salvaDati() {
		ApplicationSession sessione = ApplicationSession.getIstance();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		//motore che trasforma i dati nel json effettivo
		//libreria di goolge che consente di creare da oggetti java delle stringhe json
		JsonObject jsonObject = new JsonObject();
		
		jsonObject = serializePaziente(sessione, jsonObject);
		jsonObject = serializeEsami(sessione, jsonObject);
		jsonObject = serializePrestazioni (sessione, jsonObject);
		jsonObject = serializeMalattie (sessione,jsonObject);
		
		//prestazioni
		//malattie
		//ogni volta al jsonObject viene asseganta una nuova struttura  poichè quella precedente viene già passata
		
		try {
			String jsonString = gson.toJson(jsonObject); //scrivo su un file json
			File jsonFile = new File("saves.json");//creiamo o ci colleghiamo al file
			FileWriter fw = new FileWriter(jsonFile, false);//apriamo un nuovo writer su file in mod. sovrascrittura (false)
			fw.write(jsonString);//scriviamo su file
			fw.close();//chiudiamo il writer
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}

	/**
	 * 
	 * Questo metodo carica i dati da file locale su filesystem.
	 * Il file deve essere in formato JSON con una struttura specifica
	 * 
	 */
	public static void caricaDati() {
		FileReader fr = null;
		JsonObject obj = null;
		
		try {
			fr = new FileReader("saves.json");
			obj = JsonParser.parseReader(fr).getAsJsonObject();
			/*
			 * predo il filereader già creato, leggiamo il file e lo parsiamo (trasfornare un contenuto da un altro) in un oggetto java (JsonObject), l'oggetto iniziale è il contenuto del file è una stringa molto lunga
			 */
		} catch (FileNotFoundException e) {
			System.out.println("Nessun salvataggio, nessun dato caricato");
			return;
		} finally {
			if (fr != null) {
				try {
					fr.close();//chiudi lo stream
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		/*
		 * prendiamo il json object e dobbiamo deserializzare l'oggetto in un oggetto della sessione singleton
		 */
		
		deserializePaziente(obj);
		deserializeEsami(obj);
		deserializePrestazioni(obj);
		deserializeMalattie(obj);
		
		
		
	}
	
	
	
	
	
	
	
	private static JsonObject serializePaziente(ApplicationSession sessione, JsonObject jsonObject) {
		JsonObject jsonPaziente = new JsonObject();
		JsonObject jsonGruppoSangue = new JsonObject();
		Paziente paziente = sessione.getPaziente();
		JsonArray jsonPazTelefoni;
		if (paziente != null && paziente.getTelefono() != null)
			jsonPazTelefoni = new JsonArray(paziente.getTelefono().size());
		else
			jsonPazTelefoni = new JsonArray(0);
		
		jsonPaziente.addProperty("codiceFiscale", paziente.getCodiceFiscale());
		jsonPaziente.addProperty("codiceSanitario", paziente.getCodiceSanitario());
		jsonPaziente.addProperty("cognome", paziente.getCognome());
		jsonPaziente.addProperty("dataNascita", paziente.getDataNascita());
		jsonPaziente.addProperty("email", paziente.getEmail());
		jsonPaziente.addProperty("genere", paziente.getGenere());
		jsonPaziente.addProperty("indirizzo", paziente.getIndirizzo());
		jsonPaziente.addProperty("luogoNascita", paziente.getLuogoNascita());
		jsonPaziente.addProperty("nome", paziente.getNome());
		jsonGruppoSangue.addProperty("fattoreRH", paziente.getGruppoSanguinio().getFattoreRh());
		jsonGruppoSangue.addProperty("gruppo", paziente.getGruppoSanguinio().getGruppo());
		jsonPaziente.add("gruppoSanguinio", jsonGruppoSangue);
		
		if (paziente.getTelefono() != null) {
			for (String tel : paziente.getTelefono()) {
				jsonPazTelefoni.add(tel);
			}
		}
		
		jsonPaziente.add("telefoni", jsonPazTelefoni);
		
		
		jsonObject.add("paziente", jsonPaziente);
		return jsonObject;
	}   
	
	
	private static JsonObject serializeEsami(ApplicationSession sessione, JsonObject jsonObject) {
		Map<Long, Esame> esami = sessione.getEsami();
		JsonArray jsonEsami = new JsonArray(esami.size());
		for (Map.Entry<Long, Esame> entry : esami.entrySet()) {
			Esame esame = entry.getValue();
			JsonObject jsonEsame = new JsonObject();
			jsonEsame.addProperty("nome", esame.getNome());
			jsonEsame.addProperty("preparazione", esame.getPreparazione());
			jsonEsame.addProperty("tipologia", esame.getTipologia());
			jsonEsame.addProperty("trattamentiPostEsame", esame.getTrattamentiPostEsame());
			jsonEsame.addProperty("idEsame", esame.getIdEsame());
			jsonEsame.addProperty("valoreNormalitaMax", esame.getValoreNormalitaMax());
			jsonEsame.addProperty("valoreNormalitaMin", esame.getValoreNormalitaMin());
			jsonEsame.addProperty("sogliaErroreInserimento", esame.getSogliaErroreInserimento());
			jsonEsami.add(jsonEsame);
			
		}
		jsonObject.add("esami", jsonEsami);
		return jsonObject;
		
		
		
		
		
	}
	private static JsonObject serializePrestazioni(ApplicationSession sessione, JsonObject jsonObject) {
		Map<Long, PrestazioneEsame > prestazioni = sessione.getPrestazioni();
		JsonArray jsonPrestazioni = new JsonArray(prestazioni.size());
		for (Map.Entry<Long, PrestazioneEsame> entry: prestazioni.entrySet()) {
			PrestazioneEsame prestazione = entry.getValue();
			JsonObject jsonPrestazione = new JsonObject();
			jsonPrestazione.addProperty("idEsame", prestazione.getIdEsame());
			jsonPrestazione.addProperty("idPrestazione", prestazione.getIdPrestazione());
			jsonPrestazione.addProperty("dataEsame", prestazione.getDataEsame());
			jsonPrestazione.addProperty("luogoEsame", prestazione.getLuogoEsame());
			jsonPrestazione.addProperty("idMalattia", prestazione.getIdMalattia());
			jsonPrestazione.addProperty("esito", prestazione.getEsito());
			jsonPrestazioni.add(jsonPrestazione);
			
		}
		jsonObject.add("prestazioni", jsonPrestazioni);
		return jsonObject;
		
		
	}
	private static JsonObject serializeMalattie (ApplicationSession sessione, JsonObject jsonObject) {
		Map<Long, Malattia > malattie = sessione.getMalattie();
		JsonArray jsonMalattie = new JsonArray(malattie.size());
		for (Map.Entry<Long, Malattia> entry: malattie.entrySet()) {
			Malattia malattia = entry.getValue();
			JsonObject jsonMalattia = new JsonObject();
			JsonArray jsonElencoEsami = new JsonArray();
			jsonMalattia.addProperty("idMalattia", malattia.getIdMalattia());
			jsonMalattia.addProperty("nome", malattia.getNome());
			jsonMalattia.addProperty("dataInizio", malattia.getDataInizio());
			jsonMalattia.addProperty("dataFine", malattia.getDataFine());
			jsonMalattia.addProperty("sintomi", malattia.getSintomi());
			jsonMalattia.addProperty("diagnosi", malattia.getDiagnosi());
			jsonMalattia.addProperty("terapia", malattia.getTerapia());
			
			
			if (malattia.getElencoEsami() != null) {
				for (String ex : malattia.getElencoEsami()) {
					jsonElencoEsami.add(ex);
				}
			
				jsonMalattia.add("elencoEsami", jsonElencoEsami);
			}
			
			jsonMalattie.add(jsonMalattia);
			
		}
		
		jsonObject.add("malattie", jsonMalattie);
		
		return jsonObject;
	
		}





	private static void deserializePaziente(JsonObject jsonObject) {
		ApplicationSession sessione = ApplicationSession.getIstance();
		if (!jsonObject.has("paziente"))
			return;
		
		JsonObject jsonPaziente = jsonObject.get("paziente").getAsJsonObject();
		
		Paziente paziente = new Paziente();
		
		if (jsonPaziente.has("codiceFiscale"))
			paziente.setCodiceFiscale(jsonPaziente.get("codiceFiscale").getAsString());
		if (jsonPaziente.has("codiceSanitario"))
			paziente.setCodiceSanitario(jsonPaziente.get("codiceSanitario").getAsString());
		if (jsonPaziente.has("cognome"))
			paziente.setCognome(jsonPaziente.get("cognome").getAsString());
		if (jsonPaziente.has("dataNascita"))
			paziente.setDataNascita(jsonPaziente.get("dataNascita").getAsString());
		if (jsonPaziente.has("email"))
			paziente.setEmail(jsonPaziente.get("email").getAsString());
		if (jsonPaziente.has("genere"))
			paziente.setGenere(jsonPaziente.get("genere").getAsString());
		if (jsonPaziente.has("indirizzo"))
			paziente.setIndirizzo(jsonPaziente.get("indirizzo").getAsString());
		if (jsonPaziente.has("luogoNascita"))
			paziente.setLuogoNascita(jsonPaziente.get("luogoNascita").getAsString());
		if (jsonPaziente.has("nome"))
			paziente.setNome(jsonPaziente.get("nome").getAsString());
		
		if (jsonPaziente.has("gruppoSanguinio")) {
			JsonObject jsonGruppoSangue = jsonPaziente.get("gruppoSanguinio").getAsJsonObject();
			GruppoSanguinio gs = new GruppoSanguinio();
			gs.setFattoreRh(jsonGruppoSangue.get("fattoreRH").getAsString());
			gs.setGruppo(jsonGruppoSangue.get("gruppo").getAsString());
			
			paziente.setGruppoSanguinio(gs);
		}
			
		
		if (jsonPaziente.has("telefoni")) {
			JsonArray jsonTelefoni = jsonPaziente.get("telefoni").getAsJsonArray();
			
			List<String> telefoni = new ArrayList<String>(jsonTelefoni.size());
			for (JsonElement tel : jsonTelefoni) {
				if (tel == null)
					continue;
				telefoni.add(tel.getAsString());
			}
			paziente.setTelefono(telefoni);
		}
		
		
		sessione.setPaziente(paziente);
	} 
	
	private static void deserializeEsami(JsonObject jsonObject) {
		ApplicationSession sessione = ApplicationSession.getIstance();
		
		if (!jsonObject.has("esami"))
			return;
		
		JsonArray jsonEsami = jsonObject.get("esami").getAsJsonArray();
		Map<Long , Esame> esami = new HashMap<>(jsonEsami.size());
		for (JsonElement jsonElement : jsonEsami) {
			JsonObject jsonEsame = jsonElement.getAsJsonObject();
			Esame esame = new Esame();
			
			if (jsonEsame.has("nome"))
				esame.setNome(jsonEsame.get("nome").getAsString());
			
			if (jsonEsame.has("preparazione"))
				esame.setPreparazione(jsonEsame.get("preparazione").getAsString());
			if (jsonEsame.has("tipologia"))
				esame.setTipologia(jsonEsame.get("tipologia").getAsString());
			if (jsonEsame.has("trattamentiPostEsame"))
				esame.setTrattamentiPostEsame(jsonEsame.get("trattamentiPostEsame").getAsString());
			if (jsonEsame.has("idEsame"))
				esame.setIdEsame(jsonEsame.get("idEsame").getAsLong());
			if (jsonEsame.has("valoreNormalitaMax"))
				esame.setValoreNormalitaMax(jsonEsame.get("valoreNormalitaMax").getAsBigDecimal());
			if (jsonEsame.has("valoreNormalitaMin"))
				esame.setValoreNormalitaMin(jsonEsame.get("valoreNormalitaMin").getAsBigDecimal());
			if (jsonEsame.has("sogliaErroreInserimento"))
				esame.setSogliaErroreInserimento(jsonEsame.get("sogliaErroreInserimento").getAsBigDecimal());
			esami.put(esame.getIdEsame(),esame);
			
			
		}
		sessione.setEsami(esami);
		
	}
	private static void deserializePrestazioni(JsonObject jsonObject) {
		ApplicationSession sessione = ApplicationSession.getIstance();
		if (!jsonObject.has("prestazioni"))
			return;
		JsonArray jsonPrestazioni = jsonObject.get("prestazioni").getAsJsonArray();
		Map<Long , PrestazioneEsame> prestazioni = new HashMap<>(jsonPrestazioni.size());
		for (JsonElement jsonElement : jsonPrestazioni) {
			JsonObject jsonPrestazione = jsonElement.getAsJsonObject();
			PrestazioneEsame prest = new PrestazioneEsame();
			if (jsonPrestazione.has("idEsame"))
				prest.setIdEsame(jsonPrestazione.get("idEsame").getAsLong());
			if (jsonPrestazione.has("idPrestazione"))
				prest.setIdPrestazione(jsonPrestazione.get("idPrestazione").getAsLong());
			if (jsonPrestazione.has("dataEsame"))
				prest.setDataEsame(jsonPrestazione.get("dataEsame").getAsString());
			if (jsonPrestazione.has("luogoEsame"))
				prest.setLuogoEsame(jsonPrestazione.get("luogoEsame").getAsString());
			if (jsonPrestazione.has("idMalattia"))
				prest.setIdMalattia(jsonPrestazione.get("idMalattia").getAsLong());
			if (jsonPrestazione.has("esito"))
				prest.setEsito(jsonPrestazione.get("esito").getAsString());
			
			prestazioni.put(prest.getIdPrestazione(), prest);
			
			
			
		}
		sessione.setPrestazioni(prestazioni);
		
	}
	
	private static void deserializeMalattie(JsonObject jsonObject) {
		ApplicationSession sessione = ApplicationSession.getIstance();
		if (!jsonObject.has("malattie"))
			return;
		JsonArray jsonMalattie = jsonObject.get("malattie").getAsJsonArray();
		Map<Long , Malattia> malattie = new HashMap<>(jsonMalattie.size());
		for (JsonElement jsonElement : jsonMalattie) {
			JsonObject jsonMalattia = jsonElement.getAsJsonObject();
			Malattia malattia = new Malattia();
			if (jsonMalattia.has("idMalattia"))
				malattia.setIdMalattia(jsonMalattia.get("idMalattia").getAsLong());
			if (jsonMalattia.has("nome"))
				malattia.setNome(jsonMalattia.get("nome").getAsString());
			if (jsonMalattia.has("dataInizio"))
				malattia.setDataInizio(jsonMalattia.get("dataInizio").getAsString());
			if (jsonMalattia.has("dataFine"))
				malattia.setDataFine(jsonMalattia.get("dataFine").getAsString());
			if (jsonMalattia.has("sintomi"))
				malattia.setSintomi(jsonMalattia.get("sintomi").getAsString());
			if (jsonMalattia.has("diagnosi"))
				malattia.setDiagnosi(jsonMalattia.get("diagnosi").getAsString());
			if (jsonMalattia.has("terapia"))
				malattia.setTerapia(jsonMalattia.get("terapia").getAsString());
				
			malattie.put(malattia.getIdMalattia(), malattia);
			
			
		}
		sessione.setMalattie(malattie);
	}
	
}
