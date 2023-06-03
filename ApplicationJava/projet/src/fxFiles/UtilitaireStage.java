package fxFiles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.Connexion;



public class UtilitaireStage {
	
	public static int nombreProposition(int IdR) {
    	Connection conn = null ;
    	Statement st = null;
    	int nbreProp=1 ;
    	try {
        	conn = Connexion.getConnection();
        	st = conn.createStatement() ;
    		ResultSet rs = st.executeQuery("SELECT * from stage where IdR="+IdR ) ;
    		
    		while(rs.next())
    			nbreProp = nbreProp + 1 ;
    		return nbreProp ;    		
    	} catch (SQLException e) {
            e.printStackTrace();
            return nbreProp ;
            }
        }
    
    public static void envoyerStageEnBDD(stage stage) {
    	PreparedStatement stmt = null ;
	   	Connection conn = null ; 
        try {
            // Établir la connexion à la base de données
        	
        	conn = Connexion.getConnection() ;
        	
            // Préparer la requête SQL pour insérer les données du stage
            String sql = "INSERT INTO stage (Titre, Sujet, Duree, IdR) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, stage.getTitre());
            stmt.setString(2, stage.getSujet());
            stmt.setInt(3, stage.getDuree());
            stmt.setInt(4, stage.getIdR());	            

            // Exécuter la requête SQL
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
           
        }
    }

