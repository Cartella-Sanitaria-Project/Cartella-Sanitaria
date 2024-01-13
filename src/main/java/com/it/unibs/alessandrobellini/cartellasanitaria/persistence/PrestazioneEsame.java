package com.it.unibs.alessandrobellini.cartellasanitaria.persistence;

import java.io.Serializable;

/**
 * Classe contenente i dati specifici di una prestazione medica eseguita dal paziente.
 * I dati contenuti sono collegati ad un esame medico tramite il salvataggio di un 
 * ID (idEsame).<br>
 * Inoltre c'è il collegamento anche alla struttura Malattia in caso la prestazione
 * sia collegata ad una malattia del paziente (idMalattia).
 * Per la classe di Esame o di Malattia:
 * @see com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Esame
 * @see com.it.unibs.alessandrobellini.cartellasanitaria.persistence.Malattia
 */
public class PrestazioneEsame implements Serializable {

	private static final long serialVersionUID = -3380113282146830856L;
	private long idEsame;
	private long idPrestazione;
	private String dataEsame;
	private String luogoEsame;
	private long idMalattia;
	private String esito; // per esami diagnostici sarà es. descrizione stringa  , per quelli periodici sarà un numero
	
	public PrestazioneEsame() {
		idEsame = 0L;
		idPrestazione = 0L;
		idMalattia = 0L;
	}
	
	public long getIdEsame() {
		return idEsame;
	}
	public void setIdEsame(long idEsame) {
		this.idEsame = idEsame;
	}
	public long getIdPrestazione() {
		return idPrestazione;
	}
	public void setIdPrestazione(long idPrestazione) {
		this.idPrestazione = idPrestazione;
	}
	public String getDataEsame() {
		return dataEsame;
	}
	public void setDataEsame(String dataEsame) {
		this.dataEsame = dataEsame;
	}
	public String getLuogoEsame() {
		return luogoEsame;
	}
	public void setLuogoEsame(String luogoEsame) {
		this.luogoEsame = luogoEsame;
	}
	public long getIdMalattia() {
		return idMalattia;
	}
	public void setIdMalattia(long idMalattia) {
		this.idMalattia = idMalattia;
	}
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	
	

}
