package fxFiles;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.Connexion;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tableModel.StageModel;

public class RespoPersoMenuController {

    @FXML
    void déconnexion(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxFiles/RespoLogin.fxml"));
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
    void exit(ActionEvent event) {
		Stage stage = new Stage();
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
    }

    @FXML
    void gestCandidature(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxFiles/RespoPersoGestCandidature.fxml"));
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
    void gestComptesCand(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxFiles/RespoPersoGestComptesCand.fxml"));
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
    void gestComptesRespo(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxFiles/RespoPersoGestComptesRespo.fxml"));
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
    void gestInscription(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxFiles/RespoPersoGestInscription.fxml"));
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
    void gestStagiaire(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxFiles/RespoPersoGestStagiaires.fxml"));
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

    @SuppressWarnings("unchecked")
	@FXML
    void imprimerCatalogue(ActionEvent event) {
        PrinterJob job = PrinterJob.createPrinterJob();
        TableView<StageModel> tableView = new TableView<>();
        TableColumn<StageModel, Integer> stageIdsColumn = new TableColumn<>("Id Stage");
        TableColumn<StageModel, String> stageTitleColumn = new TableColumn<>("Titre");
        TableColumn<StageModel, String> stageSubjectColumn = new TableColumn<>("Sujet");
        TableColumn<StageModel, Integer> stageDurationColumn = new TableColumn<>("Durée");
        
        // Set the cell value factories for the table columns
        stageIdsColumn.setCellValueFactory(new PropertyValueFactory<>("idStage"));
        stageTitleColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        stageSubjectColumn.setCellValueFactory(new PropertyValueFactory<>("sujet"));
        stageDurationColumn.setCellValueFactory(new PropertyValueFactory<>("duree"));


        tableView.getColumns().setAll(stageIdsColumn,stageTitleColumn,stageSubjectColumn,stageDurationColumn);
        if (job != null) {
            // Obtenez le contenu de votre tableau (ici, TableView) à imprimer
            // Ajoutez les données de votre tableau à la TableView
            try {
            	ObservableList<StageModel> data = FXCollections.observableArrayList();
                Connection conn = Connexion.getConnection();
                // Execute the query
                String query = "SELECT * FROM stage";
                Statement statement = conn.createStatement();
                ResultSet rs;
                rs = statement.executeQuery(query);
                // Retrieve the values and populate the ChoiceBox
                while (rs.next()) {
                    int idS = rs.getInt(1);
                    String titre = rs.getString(2);
                    String sujet = rs.getString(3);
                    int duree = rs.getInt(4);
                    int idR = rs.getInt(5);
                    data.add(new StageModel(idS, titre, sujet, duree,idR));
                }
                tableView.setItems(data);
                // Create the ChoiceBox and set the items
            } catch (SQLException e) {
                e.printStackTrace();
            }
            // Obtenez le noeud racine de votre scène
            Node root = tableView;
            root.setStyle("-fx-pref-height: 1080px; -fx-pref-width: 420px;");
            // Imprimez le contenu de la TableView
            boolean success = job.printPage(root);
            
            if (success) {
                job.endJob();
            } else {
                // Gestion des erreurs lors de l
            }
        }
    }

}
