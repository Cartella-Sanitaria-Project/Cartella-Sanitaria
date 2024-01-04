package com.it.unibs.alessandrobellini.cartellasanitaria.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InsertDateUtils {
	
	
	public static Date richiediData() {
		boolean dataCorretta= false;
		Calendar dataCompleta = Calendar.getInstance();
		while (!dataCorretta) {
				String dataGiorno = InputDati.leggiStringaNonVuota("inserisci il giorno dell'esame in formato yyyy-MM-dd : ");
				dataCorretta = PazienteUtils.isDataValid(dataGiorno);
				if(!dataCorretta) System.out.println("la data inserita non è corretta");
				else {
					DateFormat dFday = new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date date = dFday.parse(dataGiorno);
						dataCompleta.setTime(date);
					} catch (ParseException e) {
						System.out.println("Errore di parsing della data (giorno)");
						return null;
					}
					
				}
		}
		dataCorretta = false;
		while (!dataCorretta) {
			String dataOra = InputDati.leggiStringaNonVuota("inserisci l'orario dell'esame in formato hh:mm : ");
			dataCorretta = PazienteUtils.isHoursValid(dataOra);
			if(!dataCorretta) System.out.println("la data inserita non è corretta");
			else {
				DateFormat dFday = new SimpleDateFormat("HH:mm");
				try {
					Date date = dFday.parse(dataOra);
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					dataCompleta.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
					dataCompleta.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
				} catch (ParseException e) {
					System.out.println("Errore di parsing della data (orario)");
					return null;
				}
			}
		}
		
		return dataCompleta.getTime();
	}
	
	public static Date richiediDataSoloGiorno() {
		boolean dataCorretta = false;
		while (!dataCorretta) {
			String dataGiorno = InputDati.leggiStringaNonVuota("inserisci il giorno in formato yyyy-MM-dd : ");
			dataCorretta = PazienteUtils.isDataValid(dataGiorno);
			if(!dataCorretta) System.out.println("la data inserita non è corretta");
			else {
				DateFormat dFday = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date date = dFday.parse(dataGiorno);
					return date;
				} catch (ParseException e) {
					System.out.println("Errore di parsing della data (giorno)");
					return null;
				}
				
			}
		}
		return null;
	}
	
	public static Date castDateGiorno(String dataInput) {
		if (!PazienteUtils.isDataValid(dataInput)) {
			// data non corretta con il formato atteso
			return null;
		}
		DateFormat dFday = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = dFday.parse(dataInput);
			return date;
		} catch (ParseException e) {
			System.out.println("Errore di parsing della data (giorno)");
			return null;
		}
			
	}
	
	public static Date setMidday(Date data) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.set(Calendar.HOUR_OF_DAY, 12);
		data = cal.getTime();
		return data;
	}
	
	public static String printDateStandard(Date data) {
		DateFormat dF = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ssZ");
		String dataFormattata = dF.format(data);
		return dataFormattata;
	}
	
	public static Date castDateStandard(String dataInput) {
		if (!PazienteUtils.isDataStandardValid(dataInput)) {
			// data non corretta con il formato atteso
			return null;
		}
		DateFormat dFday = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ssZ");
		try {
			Date date = dFday.parse(dataInput);
			return date;
		} catch (ParseException e) {
			System.out.println("Errore di parsing della data (standard)");
			return null;
		}
			
	}
	
}
