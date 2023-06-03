package fxFiles;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import application.Connexion;
import application.Erreur;
import javafx.collections.ObservableList;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RespoStagePropStagesController {

	public int IdR ;	
	void setIdR(int id) {
		IdR = id ;			
		initialize() ;
	}
	
    @FXML
    private CheckBox checkBoxListe1;

    @FXML
    private TextField duréeStage;

    @FXML
    private TextField sujetStage;  
    
    @FXML
    private TextField titreStage;

    @FXML
    private TableView<PropositionModel> tableOffres;
    @FXML
    TableColumn<PropositionModel, Integer> IdS ;
    @FXML
    TableColumn<PropositionModel, String> Titre;
    @FXML
    TableColumn<PropositionModel, String> Sujet;
    @FXML
    TableColumn<PropositionModel, Integer> Duree;
    @FXML
    TableColumn<PropositionModel, Boolean> checkBoxListe ;



    @SuppressWarnings("unchecked")
    public void initialize() {
    	if(IdR != 0) {
    		tableOffres.setEditable(true);

            //Create the table columns
            IdS.setCellValueFactory(new PropertyValueFactory<>("IdStage"));

            Titre.setCellValueFactory(new PropertyValueFactory<>("Titre"));

            Sujet.setCellValueFactory(new PropertyValueFactory<>("Sujet"));

            Duree.setCellValueFactory(new PropertyValueFactory<>("Duree"));

            checkBoxListe.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
            checkBoxListe.setCellFactory(CheckBoxTableCell.forTableColumn(checkBoxListe));


            try {
                Connection conn = Connexion.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM stage WHERE IdR = "+IdR);
                int j=0 ;
                while (rs.next()) {
                    int IdStage = rs.getInt(1);
                    String Titre = rs.getString(2);
                    String Sujet = rs.getString(3);
                    int Durree = rs.getInt(4);
                    PropositionModel stage = new PropositionModel(IdStage, Titre, Sujet, Durree);
                    tableOffres.getItems().addAll(stage);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }        	
    	}
    	
    }
    
    @FXML
    void ajouterStageResponsable(ActionEvent event) {
        int nbreProp = UtilitaireStage.nombreProposition(IdR);
        if (nbreProp <= 5) {
        	if(titreStage.getText().isEmpty() || sujetStage.getText().isEmpty()) {
        		Erreur.msgEmptyfields();
        		return ;
        	}
        	if(!isNumeric(duréeStage.getText())) {
        		Erreur.msgNotNumeric("Durrée");
        		return ;
        	}
        		
	        String titre = titreStage.getText();
	        String sujet = sujetStage.getText();
	        int duree = Integer.parseInt(duréeStage.getText());
	        // Créer un objet Stage avec les valeurs fournies
	        stage s =new stage(titre, sujet, duree, IdR);
	        UtilitaireStage.envoyerStageEnBDD(s) ;
	        
    		tableOffres.getItems().removeAll(tableOffres.getItems());
    		initialize() ;
    		clearInputFields() ;
	        }else {
	        	Alert alert = new Alert(AlertType.ERROR);
	            alert.setTitle("Error");
	            alert.setHeaderText("Nombre Proposition maximale !");
	            alert.setContentText( "Vous avez déjà proposé 5 stages");
	            alert.showAndWait();	        
	        }               
        } 
        
    @FXML
    void exit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow() ;
        stage.close();    
    }
    @FXML
    void retour(ActionEvent event) {
    	Parent root;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxFiles/RespoStageMenu.fxml"));
			root = loader.load() ;
			Scene scene = new Scene(root);
			Stage stage = new Stage() ;
			stage.initStyle(StageStyle.UNDECORATED);    			
			stage.setScene(scene);
			stage.show();
			exit(event) ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    @FXML
    void supprimerOffre(ActionEvent event)  {
            int result = 0;
            
            try {
            	ObservableList<PropositionModel> selectedItems = tableOffres.getItems();
                Connection conn = Connexion.getConnection() ;
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM stage WHERE IdS = ?") ;
                for (PropositionModel ligne : selectedItems) {
                	if(ligne.isSelectedStage()) {
                	stmt.setInt(1, ligne.getIdStage());
                    result += stmt.executeUpdate();
                    }
                }	if(result >0) {
                		tableOffres.getItems().removeAll(tableOffres.getItems());
                		initialize() ;
                	}
                
                
            } catch (SQLException e) {
                System.out.println("An error occurred while deleting the rows: " + e.getMessage());
            }           
     }
    private boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
	
    private void clearInputFields() {
		titreStage.setText("");
		duréeStage.setText("");
		sujetStage.setText("");
	}
}
