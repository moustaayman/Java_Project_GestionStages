package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class utilitaireCandidatStage {
    Connection conn=Connexion.getConnection();
    //afficher une seule offre
    public void afficherUneOffre(int id) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "select * from offre where IdO = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("ID : " + rs.getInt("idOffre"));
                System.out.println("Titre : " + rs.getString("titre"));
                System.out.println("Description : " + rs.getString("description"));
                System.out.println("Date de début : " + rs.getDate("dateDebut"));
                System.out.println("Durée : " + rs.getInt("duree") + " mois");
            } else {
                System.out.println("Aucune offre trouvée avec l'ID " + id);
            }
            rs.close();
            ps.close();
            //conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Afficher toutes les offres
    public ArrayList<Stage> afficherToutesLesOffres() {
        ArrayList<Stage> offres = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "select * from stage");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Stage offre = new Stage(0, null, null, 0);

                offre.setIdStage(rs.getInt("IdS"));
                offre.setTitre(rs.getString("Titre"));
                offre.setSujet(rs.getString("Sujet"));
                offre.setDuree(rs.getInt("Duree"));
                offres.add(offre);
            }
            rs.close();
            ps.close();
            //conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return offres;
    }

    public void postuler(int idCandidat, int idStage) {

        // Ajouter la candidature à la table de candidatures dans la base de données
        try {
            Connection conn=Connexion.getConnection();
            PreparedStatement statement = conn.prepareStatement("INSERT INTO candidature (IdC, IdS) VALUES (?, ?)");
            statement.setInt(1, idCandidat);
            statement.setInt(2, idStage);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
