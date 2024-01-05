package com.it.unibs.alessandrobellini.cartellasanitaria;

import com.it.unibs.alessandrobellini.cartellasanitaria.logic.SalvaCaricaDati;

public class Main {
	
	public static void main(String... args) {
		System.out.println("avvio applicazione");
		SalvaCaricaDati.caricaDati();
		Menu menu = new Menu();
		menu.menuPrincipale();
		System.out.println("termine applicazione");
		
	}

}
