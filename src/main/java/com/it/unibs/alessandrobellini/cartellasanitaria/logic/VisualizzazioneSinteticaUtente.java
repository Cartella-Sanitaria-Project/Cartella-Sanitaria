package com.it.unibs.alessandrobellini.cartellasanitaria.logic;

import com.it.unibs.alessandrobellini.cartellasanitaria.session.ApplicationSession;

public class VisualizzazioneSinteticaUtente {
	public void  execute() {
		ApplicationSession sessione = ApplicationSession.getIstance();
		sessione.getPaziente();
		sessione.getPrestazioni();
		sessione.getMalattie();
		
		StringBuffer st = new StringBuffer();
		st.append(" Nome : " + sessione.getPaziente().getNome() + "\n");
		st.append("Cognome : " + sessione.getPaziente().getCognome() + "\n");
		//st.append();
		
	}

}
