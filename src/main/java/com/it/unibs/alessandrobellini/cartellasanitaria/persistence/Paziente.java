package com.it.unibs.alessandrobellini.cartellasanitaria.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Paziente implements Serializable {
	
	private static final long serialVersionUID = 7976957993890447449L;
	
	private String nome;
	private String cognome;
	private String indirizzo;
	private List <String> telefono; //l 'interfaccia list non può vivere da sola e per essere inzializzata deve avere una classe di implementazione come le ArrayList
	private String email;
	private String dataNascita; // = LocalDate.of(2023, 12, );
	private String luogoNascita;
	private String genere;
	private String codiceFiscale;
	private String codiceSanitario;
	private GruppoSanguinio gruppoSanguinio;
	
	public Paziente() {
		gruppoSanguinio = new GruppoSanguinio();
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public List<String> getTelefono() {
		return telefono;
	}

	public void setTelefono(List<String> telefono) {
		this.telefono = telefono;
	}
	
	public void addTelefono(String telefono) {
		if(this.telefono == null)
			this.telefono = new ArrayList<>();// lista di array dinamica 
		this.telefono.add(telefono);//aggiungi in coda 
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getLuogoNascita() {
		return luogoNascita;
	}

	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}

	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCodiceSanitario() {
		return codiceSanitario;
	}

	public void setCodiceSanitario(String codiceSanitario) {
		this.codiceSanitario = codiceSanitario;
	}

	public GruppoSanguinio getGruppoSanguinio() {
		return gruppoSanguinio;
	}

	public void setGruppoSanguinio(GruppoSanguinio gruppoSanguinio) {
		this.gruppoSanguinio = gruppoSanguinio;
	}

	public Paziente(String nome, String cognome, String indirizzo, List<String> telefono, String email,
			String dataNascita, String luogoNascita, String genere, String codiceFiscale, String codiceSanitario,
			GruppoSanguinio gruppoSanguinio) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.indirizzo = indirizzo;
		this.telefono = telefono;
		this.email = email;
		this.dataNascita = dataNascita;
		this.luogoNascita = luogoNascita;
		this.genere = genere;
		this.codiceFiscale = codiceFiscale;
		this.codiceSanitario = codiceSanitario;
		this.gruppoSanguinio = gruppoSanguinio;
	}

	@Override
	public String toString() {
		return "Paziente: \n"
				+ "nome=" + nome + "\n"
				+ "cognome=" + cognome + "\n"
				+ "indirizzo=" + indirizzo + "\n"
				+ "telefono=" + telefono
				+ "\nemail=" + email 
				+ "\ndataNascita=" + dataNascita + "\n"
				+ "luogoNascita=" + luogoNascita + "\n"
				+ "genere=" + genere + "\n"
				+ "codiceFiscale=" + codiceFiscale + "\n"
				+ "codiceSanitario=" + codiceSanitario
				+ "\ngruppoSanguinio=" + gruppoSanguinio;
	}
	
	
	

}
