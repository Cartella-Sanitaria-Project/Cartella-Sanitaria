package com.it.unibs.alessandrobellini.cartellasanitaria.persistence;

import java.io.Serializable;
import java.math.BigDecimal;

public class Esame implements Serializable {
	
	
	private static final long serialVersionUID = 2269344357355399808L;
	
	private long idEsame;
	private String nome;
	private String tipologia;
	private String preparazione;
	private BigDecimal valoreNormalitaMin;
	private BigDecimal valoreNormalitaMax;
	private BigDecimal sogliaErroreInserimento;
	private String trattamentiPostEsame;
	public long  getIdEsame() {
		return idEsame;
	}
	public void setIdEsame(long idEsame) {
		this.idEsame = idEsame;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	public String getPreparazione() {
		return preparazione;
	}
	public void setPreparazione(String preparazione) {
		this.preparazione = preparazione;
	}
	public BigDecimal getValoreNormalitaMin() {
		return valoreNormalitaMin;
	}
	public void setValoreNormalitaMin(BigDecimal valoreNormalitaMin) {
		this.valoreNormalitaMin = valoreNormalitaMin;
	}
	public BigDecimal getValoreNormalitaMax() {
		return valoreNormalitaMax;
	}
	public void setValoreNormalitaMax(BigDecimal valoreNormalitaMax) {
		this.valoreNormalitaMax = valoreNormalitaMax;
	}
	public BigDecimal getSogliaErroreInserimento() {
		return sogliaErroreInserimento;
	}
	public void setSogliaErroreInserimento(BigDecimal sogliaErroreInserimento) {
		this.sogliaErroreInserimento = sogliaErroreInserimento;
	}
	public String getTrattamentiPostEsame() {
		return trattamentiPostEsame;
	}
	public void setTrattamentiPostEsame(String trattamentiPostEsame) {
		this.trattamentiPostEsame = trattamentiPostEsame;
	}
	
	
	
	
	

}
