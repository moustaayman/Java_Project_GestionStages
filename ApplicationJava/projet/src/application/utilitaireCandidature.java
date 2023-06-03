package application;

import java.sql.*;
import java.util.ArrayList;

public class utilitaireCandidature {
    Connection conn=Connexion.getConnection();

    public ArrayList<Candidature> afficherLesCandidatures(int idC) {
        ArrayList<Candidature> candidatures = new ArrayList<>();
        try {

            PreparedStatement ps = conn.prepareStatement(
                    "SELECT candidature.IdS,Titre,priorite,etat FROM candidature, stage WHERE IdC=? AND candidature.IdS=stage.IdS");
            ps.setInt(1, idC);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Candidature offre = new Candidature(0, null, 0, null);

                offre.setIdStage(rs.getInt("IdS"));
                offre.setTitre(rs.getString("Titre"));
                offre.setPrioriteStage(rs.getInt("priorite"));
                offre.setEtat(rs.getString("etat"));
                candidatures.add(offre);
            }
            rs.close();
            ps.close();
            //conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidatures;
    }
    public boolean supprimerUneCandidature(int IdCandidat, int IdS) {
        try {
            Connection conn = Connexion.getConnection();
            String sql = "DELETE FROM candidature WHERE IdC = ? AND IdS = ?";
//            String sql =  "DELETE FROM candidature WHERE IdC=" +IdCandidat +   " AND IdS=(SELECT IdS FROM stage WHERE Titre= " + String.format("'%s'", titre)  + ")";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, IdCandidat);
            statement.setInt(2, IdS);
            int rowsDeleted = statement.executeUpdate();
            statement.close();
            //conn.close();

            return rowsDeleted > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
  public boolean supprimerUneCandidatureDeStage(int IdS) {
    try {
      Connection conn = Connexion.getConnection();
      String sql = "DELETE FROM stage WHERE IdS = ?";
//            String sql =  "DELETE FROM candidature WHERE IdC=" +IdCandidat +   " AND IdS=(SELECT IdS FROM stage WHERE Titre= " + String.format("'%s'", titre)  + ")";
      PreparedStatement statement = conn.prepareStatement(sql);
      statement.setInt(1, IdS);
      int rowsDeleted = statement.executeUpdate();
      statement.close();
      //conn.close();

      return rowsDeleted > 0;
    } catch (SQLException ex) {
      ex.printStackTrace();
      return false;
    }
  }
  public boolean updateCandidaturePriority(int candidatureId, int priority) {
    String updateQuery = "UPDATE candidature SET priorite = ? WHERE idS = ?";
    try {
      Connection conn = Connexion.getConnection();
      PreparedStatement statement = conn.prepareStatement(updateQuery);
      statement.setInt(1, priority);
      statement.setInt(2, candidatureId);
      int rowsAffected = statement.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }
  public void addCandidatToStagiaire(int idCandidat) {
    try {
      Connection conn = Connexion.getConnection();
      String query = "INSERT INTO stagiaire(IdCandidat,IdResponsable) VALUES (?,?)";
      String query1 = "SELECT IdR FROM candidature, stage where candidature.IdS = stage.IdS and candidature.IdC = "+idCandidat;
      
      
      int idR;
      
      PreparedStatement statement = conn.prepareStatement(query1);
      ResultSet rs = statement.executeQuery();
      rs.next();
      idR = rs.getInt(1);
      
      statement = conn.prepareStatement(query);
      
      statement.setInt(1, idCandidat);
      statement.setInt(2, idR);
      
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  public int nombrePostulations(int idCandidat) {
    try {
      Connection conn = Connexion.getConnection();
      String query = "SELECT COUNT(*) as nbc FROM candidature WHERE candidature.IdC = ?";
      PreparedStatement statement = conn.prepareStatement(query);

      statement.setInt(1, idCandidat);
      ResultSet rs = statement.executeQuery();
      if (rs.next()) {
          int nbre = rs.getInt(1);
          return nbre ;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    };
    return -1;
  }
  public boolean isCandidatureExist(int IdCandidat, int IdS) {
	    try {
	      Connection conn = Connexion.getConnection();
	      String sql = "SELECT COUNT(*) FROM candidature WHERE candidature.idS = ? AND idC = ?";
	      PreparedStatement statement = conn.prepareStatement(sql);
	      statement.setInt(1, IdCandidat);
	      statement.setInt(2, IdS);
	      ResultSet resultSet = statement.executeQuery();
	      int nbreCandidature =0;
	      if(resultSet.next()) {
	        nbreCandidature = resultSet.getInt(1);
	      }
	      statement.close();
	      //conn.close();
	      if(nbreCandidature == 0) {
	        return true;
	      }
	    } catch (SQLException ex) {
	      ex.printStackTrace();
	      return false;
	    }
	    return false;
	  }

}
