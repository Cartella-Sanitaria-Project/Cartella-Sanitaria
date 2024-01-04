package com.it.unibs.alessandrobellini.cartellasanitaria.logic;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Esame;
import com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Paziente;
import com.it.unibs.alessandrobellini.cartellasanitaria.session.ApplicationSession;

public class SalvaCaricaDati {
	public static void salvaDati() {
		ApplicationSession sessione = ApplicationSession.getIstance();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		//motore che trasforma i dati nel json effettivo
		//libreria di ggolge che consente di creare da oggetti java delle stringhe json
		JsonObject jsonObject = new JsonObject();
		
		jsonObject = serializePaziente(sessione, jsonObject);
		jsonObject = serializeEsami(sessione, jsonObject);
		
		//prestazioni
		//malattie
		
		
		
		
		
	}

	
	
	
	
	
	
	
	
	
	private static JsonObject serializePaziente(ApplicationSession sessione, JsonObject jsonObject) {
		JsonObject jsonPaziente = new JsonObject();
		JsonObject jsonGruppoSangue = new JsonObject();
		Paziente paziente = sessione.getPaziente();
		JsonArray jsonPazTelefoni = new JsonArray(paziente.getTelefono().size());
		
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
		
		for (String tel : paziente.getTelefono()) {
			jsonPazTelefoni.add(tel);
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

}
