package com.it.unibs.alessandrobellini.cartellasanitaria.persistence;

import java.io.Serializable;

public class PrestazioneEsame implements Serializable {

	private static final long serialVersionUID = -3380113282146830856L;
	private long idEsame;
	private long idPrestazione;
	private String dataEsame;
	private String luogoEsame;
	private String malattia;
	private String esito; // per esami diagnostici sarà es. descrizione stringa  , per quelli periodici sarà un numero

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
	public String getMalattia() {
		return malattia;
	}
	public void setMalattia(String malattia) {
		this.malattia = malattia;
	}
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	
	

}
