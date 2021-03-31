package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Studente;

public class CorsoDAO {

	public List<Corso> getCorsiByPeriodo(Integer periodo) {
		
		String sql = "SELECT * "
				+ "FROM corso "
				+ "WHERE pd = ?";
		List<Corso> result = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, periodo);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				result.add(c);
			}
			
			rs.close();
			st.close();
			conn.close();
			
		} catch(SQLException e) {
			throw new RuntimeException();
		}
		
		return result;
	}
	
	public Map<Corso, Integer> getStudentiByPeriodo(Integer periodo){

		String sql = "SELECT c.codins, c.nome, c.crediti, c.pd, COUNT(*) as tot "
				+ "FROM corso c, iscrizione i "
				+ "WHERE c.codins = i.codins AND c.pd = ? "
				+ "GROUP BY c.codins, c.nome, c.crediti, c.pd";
		Map<Corso, Integer> result = new HashMap<Corso, Integer>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, periodo);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				Integer n = rs.getInt("tot");
				result.put(c, n);
			}
			
			rs.close();
			st.close();
			conn.close();
			
		} catch(SQLException e) {
			throw new RuntimeException();
		}
		
		return result;
	}
	
	public List<Studente> getStudentiByCorso(Corso corso) {
		
		final String sql = "SELECT s.matricola, s.cognome, s.nome, s.CDS "
				+ "FROM iscrizione i, studente s "
				+ "WHERE i.matricola = s.matricola AND codins=?";
		List<Studente> result = new LinkedList<Studente>();
	
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Studente s = new Studente(rs.getInt("matricola"), rs.getString("nome"), rs.getString("cognome"), rs.getString("CDS"));
				result.add(s);
			}
			
			rs.close();
			st.close();
			conn.close();
			
		}catch(SQLException e) {
			throw new RuntimeException();
		}
		return result;
	}
	
	public Map<String, Integer> getDivisioneStudenti(Corso corso) {
		
		final String sql = "SELECT s.CDS, COUNT(*) AS tot "
				+ "FROM studente s, iscrizione i "
				+ "WHERE s.matricola = i.matricola AND i.codins = '01OVYPG' AND s.cds <> '' "
				+ "GROUP BY s.CDS";
		
		Map<String, Integer> result = new HashMap<String, Integer>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				result.put(rs.getString("CDS"), rs.getInt("tot"));
			}
			
			rs.close();
			st.close();
			conn.close();
			
		}catch(SQLException e) {
			throw new RuntimeException();
		}
		return result;
	}

	public boolean esisteCorso(Corso corso) {
		
		final String sql = "SELECT * FROM corso WHERE codins = ?";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				rs.close();
				st.close();
				conn.close();
				
				return true;
			}
			else {
				rs.close();
				st.close();
				conn.close();
				
				return false;
			}
			
		}catch(SQLException e) {
			throw new RuntimeException();
		}
	}
}
