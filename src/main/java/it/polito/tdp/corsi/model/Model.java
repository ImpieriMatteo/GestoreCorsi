package it.polito.tdp.corsi.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.db.CorsoDAO;

public class Model {
	
	private CorsoDAO corsoDAO;
	
	public Model() {
		corsoDAO = new CorsoDAO();
	}
	
	public List<Corso> getCorsiByPeriodo(Integer pd) {
		return corsoDAO.getCorsiByPeriodo(pd);
	}
	
	public Map<Corso, Integer> getStudentiByPeriodo(Integer pd) {
		return corsoDAO.getStudentiByPeriodo(pd);
	}
	
	public List<Studente> getStudentiByCorso(String codice) {
		return corsoDAO.getStudentiByCorso(new Corso(codice, null, null, null));
	}
	
	public Map<String, Integer> getDivisioneCDS(String codice) {
		
		//SOLUZIONE 1
//		Map<String, Integer> divisione = new HashMap<String, Integer>();
//		List<Studente> studenti = this.getStudentiByCorso(codice);
//		for(Studente s : studenti) {
//			if(!s.getCDS().equals("") && s.getCDS()!=null) {
//				if(divisione.get(s.getCDS())==null)
//					divisione.put(s.getCDS(), 1);
//				else
//					divisione.put(s.getCDS(), divisione.get(s.getCDS())+1);
//			}
//		}
//		return divisione;
		
		return corsoDAO.getDivisioneStudenti(new Corso(codice, null, null, null));
	}

	public boolean esisteCorso(String codice) {
		return corsoDAO.esisteCorso(new Corso(codice, null, null, null));
	}
}
