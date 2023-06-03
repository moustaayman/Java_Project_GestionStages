package fxFiles;
import tableModel.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import application.Connexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RespoPersoGestInscriptionController {
	@FXML
	private TableView<InscriptionModel> tableInscription;
	@FXML
	TableColumn<InscriptionModel, Integer> candidatIdColumn;
	@FXML
	TableColumn<InscriptionModel, Integer> offreIdColumn;
	@FXML
	TableColumn<InscriptionModel, Integer> prioriteColumn;
	@FXML
	TableColumn<InscriptionModel, String> etatColumn;
	@FXML
	TableColumn<InscriptionModel, Boolean> checkBoxColumn;
	@FXML
	TableColumn<InscriptionModel, String> nomColumn;
	@FXML
	TableColumn<InscriptionModel, String> prenomColumn;
    @SuppressWarnings("unchecked")
	public void initialize() {
    	tableInscription.setEditable(true);
        // Create the table columns
    	checkBoxColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkBoxColumn));
    	checkBoxColumn.setCellValueFactory(new PropertyValueFactory<>("selected"));
        candidatIdColumn.setCellValueFactory(new PropertyValueFactory<>("candidatId"));
        offreIdColumn.setCellValueFactory(new PropertyValueFactory<>("offreId"));
        prioriteColumn.setCellValueFactory(new PropertyValueFactory<>("priorite"));
        etatColumn.setCellValueFactory(new PropertyValueFactory<>("etat"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        // Add the columns to the TableView
        tableInscription.getColumns().setAll(checkBoxColumn,candidatIdColumn,nomColumn,prenomColumn, offreIdColumn, prioriteColumn, etatColumn);
        // Fetch the data from the database or create sample data
        ObservableList<InscriptionModel> data = fetchDataFromDatabase();
        // Set the data in the TableView
        tableInscription.setItems(data);
    }
    private ObservableList<InscriptionModel> fetchDataFromDatabase() {
        ObservableList<InscriptionModel> data = FXCollections.observableArrayList();

        try {
            Connection conn = Connexion.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT candidature.*,Nom,Prenom FROM candidature,candidat where candidature.idC = candidat.IdCandidat and  etat = 'en attente'");
            while (rs.next()) {
                int candidatId = rs.getInt(1);
                int offreId = rs.getInt(2);
                int priorite = rs.getInt(3);
                String etat = rs.getString(4);
                String nom = rs.getString(5);
                String prenom = rs.getString(6);
                InscriptionModel candidature = new InscriptionModel(candidatId, offreId, priorite, etat, nom, prenom);
                data.add(candidature);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
    @FXML
    void accepterCandidature(ActionEvent event) {
    	ObservableList<InscriptionModel> selectedItems = tableInscription.getItems().filtered(InscriptionModel::isSelected);
        try {
            Connection conn = Connexion.getConnection();
            PreparedStatement st = conn.prepareStatement("UPDATE `candidature` SET `etat` = 'retenu' WHERE `candidature`.`idC` = ? AND `candidature`.`idS` = ?");
            for (InscriptionModel candidature : selectedItems) {
                st.setInt(1, candidature.getCandidatId()); 
                st.setInt(2, candidature.getOffreId()); 
                st.executeUpdate();
            }
            // Optional: Refresh the TableView after the operation
            tableInscription.getItems().removeAll(selectedItems);
            initialize();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initialize();
    }
    @FXML
    void annulerCandidature(ActionEvent event) {
    	ObservableList<InscriptionModel> selectedItems = tableInscription.getItems().filtered(InscriptionModel::isSelected);
        try {
            Connection conn = Connexion.getConnection();
            PreparedStatement st = conn.prepareStatement("UPDATE `candidature` SET `etat` = 'refusé' WHERE `candidature`.`idC` = ? AND `candidature`.`idS` = ?");

            for (InscriptionModel candidature : selectedItems) {
                st.setInt(1, candidature.getCandidatId()); 
                st.setInt(2, candidature.getOffreId()); 
                st.executeUpdate();
            }
            // Optional: Refresh the TableView after the operation
            tableInscription.getItems().removeAll(selectedItems);
            initialize();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initialize();
    }

    @FXML
    void exit(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxFiles/RespoPersoMenu.fxml"));
	        Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(scene);
			stage.show();
	        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        stage.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void supprimerCandidature(ActionEvent event) {
    	ObservableList<InscriptionModel> selectedItems = tableInscription.getItems().filtered(InscriptionModel::isSelected);
        try {
            Connection conn = Connexion.getConnection();
            PreparedStatement st = conn.prepareStatement("DELETE FROM candidature WHERE `candidature`.`idC` = ? AND `candidature`.`idS` = ?");

            for (InscriptionModel candidature : selectedItems) {
                st.setInt(1, candidature.getCandidatId()); 
                st.setInt(2, candidature.getOffreId()); 
                st.executeUpdate();
            }
            // Optional: Refresh the TableView after the operation
            tableInscription.getItems().removeAll(selectedItems);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initialize();
    }

}
