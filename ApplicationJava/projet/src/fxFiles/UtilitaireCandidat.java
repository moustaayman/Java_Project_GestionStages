package fxFiles;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.Connexion;

public class UtilitaireCandidat {
	
public static ResultSet getCandidatures() throws ClassNotFoundException {
		
    	Connection conn = null ;
    	Statement st = null ;
    	try {
    		conn = Connexion.getConnection() ;
    		st = conn.createStatement() ;
    		String req =("SELECT candidat.IdCandidat,Nom,Prenom,Courriel,IdS,Titre,Sujet,Duree,Etat,Priorite FROM candidat,candidature,stage WHERE candidature.IdCandidat=candidat.IdCandidat and candidature.IdStage=stage.IdS")  ;
    		ResultSet rs = st.executeQuery(req) ;
    		return rs ;
    	}catch(SQLException e){
    		e.printStackTrace();
    		return null;
    	}	
    }
}
