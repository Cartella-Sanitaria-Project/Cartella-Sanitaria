package com.it.unibs.alessandrobellini.cartellasanitaria.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Malattia implements Serializable {
	
	
	private static final long serialVersionUID = -3905773892352586037L;
	private long idMalattia;
	private String nome;
	private String dataInizio;
	private String dataFine;
	private String sintomi;
	private String diagnosi;
	private List <String> elencoEsami;
	private String terapia;
	
	public Malattia() {
		super();
		
	}
	
	public Malattia(long idMalattia, String nome, String dataInizio, String dataFine, String sintomi, String diagnosi,
			List<String> elencoEsami, String terapia) {
		super();
		this.idMalattia = idMalattia;
		this.nome = nome;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.sintomi = sintomi;
		this.diagnosi = diagnosi;
		this.elencoEsami = elencoEsami;
		this.terapia = terapia;
	}
	public long getIdMalattia() {
		return idMalattia;
	}
	public void setIdMalattia(long idMalattia) {
		this.idMalattia = idMalattia;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(String dataInizio) {
		this.dataInizio = dataInizio;
	}
	public String getDataFine() {
		return dataFine;
	}
	public void setDataFine(String dataFine) {
		this.dataFine = dataFine;
	}
	public String getSintomi() {
		return sintomi;
	}
	public void setSintomi(String sintomi) {
		this.sintomi = sintomi;
	}
	public String getDiagnosi() {
		return diagnosi;
	}
	public void setDiagnosi(String diagnosi) {
		this.diagnosi = diagnosi;
	}
	public List<String> getElencoEsami() {
		return elencoEsami;
	}
	public void setElencoEsami(List<String> elencoEsami) {
		this.elencoEsami = elencoEsami;
	}
	public void addEsami(String elencoEsami) {
		if(this.elencoEsami == null)
			this.elencoEsami = new ArrayList<>();// lista di array dinamica 
		this.elencoEsami.add(elencoEsami);//aggiungi in coda 
	}
	public String getTerapia() {
		return terapia;
	}
	public void setTerapia(String terapia) {
		this.terapia = terapia;
	}
	
	@Override
	public String toString() {
		return "Paziente [ID malattia = " + idMalattia +  " nome = " + nome + ", data inizio =" + dataInizio + ", data fine =" + dataFine + ", sintomi =" + sintomi
				+ ", diagnosi =" + diagnosi + ", elenco degli esami =" + elencoEsami + ", terapia =" + terapia + "]";
	}
	
	
	
	
	

}
