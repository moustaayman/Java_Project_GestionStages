package fxFiles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import application.Connexion;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.BooleanProperty;


public class RespoStageValidCandiController {

	int IdR ;	
	void setIdR(int id) {
		IdR = id ;	
		initialize() ;
	}
	
    @FXML
    private CheckBox CheckBoxListe2;
    @FXML
    private CheckBox CheckBoxListe3;

    @FXML
    private TableView<ValidModel> tableCandidatures;

    @FXML
    TableColumn<ValidModel, Integer> IdC ;
    @FXML
    TableColumn<ValidModel, String> Nom;
    @FXML
    TableColumn<ValidModel, String> Prenom ;
    @FXML
    TableColumn<ValidModel, Integer> IdS;
    @FXML
    TableColumn<ValidModel, String> Titre ;
    @FXML
    TableColumn<ValidModel, Integer> Durree ;
    @FXML
    TableColumn<ValidModel, String> Etat ;
    @FXML
    TableColumn<ValidModel, Integer> Priorite ;
    @FXML
    TableColumn<ValidModel, Boolean> CheckBoxListe ;
    @FXML
    TableColumn<ValidModel, Boolean> CheckBoxListe1 ;
    
    @FXML
    void exit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow() ;
        stage.close();
    }
    
    @SuppressWarnings("unchecked")
    public void initialize() {
    	if(IdR != 0) {
    		tableCandidatures.setEditable(true);

            //Create the table columns
            IdC.setCellValueFactory(new PropertyValueFactory<>("IdC"));
            Nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
            Prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
            IdS.setCellValueFactory(new PropertyValueFactory<>("IdS"));
            Titre.setCellValueFactory(new PropertyValueFactory<>("Titre"));
            Durree.setCellValueFactory(new PropertyValueFactory<>("Durree"));
            Etat.setCellValueFactory(new PropertyValueFactory<>("Etat"));
            Priorite.setCellValueFactory(new PropertyValueFactory<>("Priorite"));

            CheckBoxListe.setCellValueFactory(cellData -> cellData.getValue().ValideProperty());
            CheckBoxListe.setCellFactory(column -> new CheckBoxTableCell<ValidModel, Boolean>() {
                @Override
                public void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        ValidModel validModel = getTableRow().getItem();
                        if (validModel != null) {
                            setGraphic(null);
                            setText(null);
                            if (item != null) {
                                CheckBox checkBox = new CheckBox();
                                checkBox.setSelected(item);
                                setGraphic(checkBox);
                                checkBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                                    validModel.setValideCandidat(isSelected);
                                });
                            }
                        }
                    }
                }
            });

            CheckBoxListe1.setCellValueFactory(cellData -> cellData.getValue().RefuseProperty());
            CheckBoxListe1.setCellFactory(column -> new CheckBoxTableCell<ValidModel, Boolean>() {
                @Override
                public void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        ValidModel validModel = getTableRow().getItem();
                        if (validModel != null) {
                            setGraphic(null);
                            setText(null);
                            if (item != null) {
                                CheckBox checkBox = new CheckBox();
                                checkBox.setSelected(item);
                                setGraphic(checkBox);
                                checkBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                                    validModel.setRefuseCandidat(isSelected);
                                });
                            }
                        }
                    }
                }
            });

            try {
                Connection conn = Connexion.getConnection();
                Statement st = conn.createStatement();
        		String req =("SELECT candidature.IdC,Nom,Prenom,candidature.IdS,Titre,Duree,Etat,Priorite FROM candidat,candidature,stage WHERE candidature.IdC=candidat.IdCandidat AND candidature.IdS=stage.IdS AND Etat = 'retenu' AND IdR = "+IdR)  ;
                ResultSet rs = st.executeQuery(req) ;
                while (rs.next()) {
                    int IdC = rs.getInt(1);
                    String Nom = rs.getString(2);
                    String Prenom = rs.getString(3);
                    int IdS = rs.getInt(4);
                    String Titre = rs.getString(5);
                    int Durree = rs.getInt(6);
                    String Etat = rs.getString(7);
                    int Priorite = rs.getInt(8) ;
                   
                    ValidModel Candidature = new ValidModel(IdC, Nom, Prenom, IdS, Titre, Durree, Etat, Priorite);
                    tableCandidatures.getItems().add(Candidature);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } 
    	}
    	}

    @FXML
    void sélectionnerCandidat(ActionEvent event) {
    	 int result = 0;
         
         try {        	 
         	ObservableList<ValidModel> Items = tableCandidatures.getItems();
             Connection conn = Connexion.getConnection() ;
             PreparedStatement stmt = conn.prepareStatement("UPDATE candidature SET Etat = ? WHERE IdC = ? AND IdS=?") ;
             for (ValidModel ligne : Items) {
            	 if(ligne.isValideCandidat()) {
            		 if(ligne.getEtat().equals("retenu")) {
            			 	stmt.setString(1, "validé");
            			 	stmt.setInt(2, ligne.getIdC()) ;
            			 	stmt.setInt(3, ligne.getIdS());
            			 	result += stmt.executeUpdate();
            		 }          		 
            	 }
            	 if(ligne.isRefuseCandidat()) {
            		 if(ligne.getEtat().equals("retenu")) {
            			 	stmt.setString(1, "refusé");
            			 	stmt.setInt(2, ligne.getIdC()) ;
            			 	stmt.setInt(3, ligne.getIdS());
            			 	result += stmt.executeUpdate();
            		 }  
             }}
             if(result >0) {
             		tableCandidatures.getItems().removeAll(tableCandidatures.getItems());
             		initialize() ;
             	}
              
             
        } catch (SQLException e) {
             System.out.println("An error occurred while updating the rows: " + e.getMessage());
         }       	   	
    } 

}

