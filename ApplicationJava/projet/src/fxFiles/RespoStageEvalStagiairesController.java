package fxFiles;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import application.Connexion;
import application.Erreur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class RespoStageEvalStagiairesController {

    int IdR;

    void setIdR(int id) {
        IdR = id;
        initialize();
    }

    @FXML
    private TextField idStagiaire;

    @FXML
    private TextField noteTravailStagiaire;

    @FXML
    private TextField noteComportementStagiaire;

    @FXML
    private TextField noteComportementStagiaire1;

    @FXML
    private TableView<EvalModel> tableEvaluation;

    @FXML
    private TableColumn<EvalModel, Integer> IdStagiaire;
    @FXML
    private TableColumn<EvalModel, String> Nom;
    @FXML
    private TableColumn<EvalModel, String> Prenom;
    @FXML
    private TableColumn<EvalModel, String> NumBadge;
    @FXML
    private TableColumn<EvalModel, Double> NoteTravail;
    @FXML
    private TableColumn<EvalModel, Double> NoteComportement;
    @FXML
    private TableColumn<EvalModel, Double> NoteRapportEcrit;

    @FXML
    void exit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow() ;
        stage.close(); 
    }

    @FXML
    void initialize() {
        if (IdR != 0) {
            tableEvaluation.setEditable(true);

            IdStagiaire.setCellValueFactory(new PropertyValueFactory<>("IdStagiaire"));

            Nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));

            Prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));

            NumBadge.setCellValueFactory(new PropertyValueFactory<>("NumBadge"));

            NoteTravail.setCellValueFactory(new PropertyValueFactory<>("NoteTravail"));

            NoteComportement.setCellValueFactory(new PropertyValueFactory<>("NoteComportement"));

            NoteRapportEcrit.setCellValueFactory(new PropertyValueFactory<>("NoteRapportEcrit"));

            try {
                Connection conn = Connexion.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT IdStagiaire, Nom, Prenom, NumBadge, NoteTravail, NoteComportement, NoteRapport FROM stagiaire, candidat WHERE IdResponsable = " + IdR + " AND stagiaire.IdCandidat = candidat.IdCandidat");
                while (rs.next()) {
                    int IdStagiaire = rs.getInt(1);
                    String Nom = rs.getString(2);
                    String Prenom = rs.getString(3);
                    String NumBadge = rs.getString(4);
                    double NoteTravail = rs.getDouble(5);
                    double NoteComportement = rs.getDouble(6);
                    double NoteRapportEcrit = rs.getDouble(7);
                    EvalModel stagiaire = new EvalModel(IdStagiaire, Nom, Prenom, NumBadge, NoteTravail, NoteComportement, NoteRapportEcrit);
                    tableEvaluation.getItems().add(stagiaire);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    

    @FXML
    void ajouterStageResponsable() {
        if(idStagiaire.getText().isEmpty() || noteTravailStagiaire.getText().isEmpty() || noteComportementStagiaire.getText().isEmpty() || noteComportementStagiaire1.getText().isEmpty()) {
        	Erreur.msgEmptyfields();
        	return ;
        }
        if(!isNumeric(idStagiaire.getText())) {
        	Erreur.msgNotNumeric("IdStagiaire");
        	return ;
        }

        if(!isNumeric(noteTravailStagiaire.getText())) {
        	Erreur.msgNotNumeric("Note Travail ");
        	
        	return ;
        }
        
        if(!isNumeric(noteComportementStagiaire.getText())) {
        	Erreur.msgNotNumeric("Note Comportement");
        	return ;
        }
        if(!isNumeric(noteComportementStagiaire1.getText())) {
        	Erreur.msgNotNumeric("Note Rapport Ecrit");
        	return ;
        }
    	int IdStagiaire = Integer.parseInt(idStagiaire.getText());
        double NoteT = Double.parseDouble(noteTravailStagiaire.getText());
        double NoteC = Double.parseDouble(noteComportementStagiaire.getText());
        double NoteRE = Double.parseDouble(noteComportementStagiaire1.getText());
        if((!NoteCorrect(NoteT))||(!NoteCorrect(NoteC))||(!NoteCorrect(NoteRE))) {
        	Erreur.msgNumberOutOfRange();    	
        	return ;
        }
        
        try {
            Connection conn = Connexion.getConnection();
            String req = "UPDATE stagiaire SET NoteTravail = ?, NoteComportement = ?, NoteRapport = ? WHERE IdStagiaire = ?";
            PreparedStatement ps = conn.prepareStatement(req);
            ps.setDouble(1, NoteT);
            ps.setDouble(2, NoteC);
            ps.setDouble(3, NoteRE);
            ps.setInt(4, IdStagiaire);
            ps.executeUpdate();
            tableEvaluation.getItems().removeAll(tableEvaluation.getItems());
            initialize();
            clearInputFields() ;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
	private void clearInputFields() {
		idStagiaire.setText("");
		noteTravailStagiaire.setText("");
		noteComportementStagiaire.setText("");
		noteComportementStagiaire1.setText("");
	}
    private boolean NoteCorrect(double n) {
    	if((n<0)||(n>20)) {
    		return false;   		
    	}else {
    		return true ;
    	}
    }
}
