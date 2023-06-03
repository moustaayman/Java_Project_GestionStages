package fxFiles;

import application.Candidat;
import application.utilitaireCandidatStage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;

public class CandiMenuController {
    private Candidat candidat;

    public CandiMenuController() {
    }
    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }
    @FXML
    void offreStage(ActionEvent event) {
        CandiOffresController.setCandidat(candidat);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CandiOffres.fxml"));
            Parent root = loader.load();

            CandiOffresController candiOffresController = loader.getController();
//            candiOffresController.setCandidat(candidat); // Set the candidat before loading the FXML
         
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.show();
//          Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
//          s.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    void candaiCandidat(ActionEvent event) {
        CandiCandidatureController.setCandidat(candidat);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CandiCandidature.fxml"));
            Parent root = loader.load();
            CandiCandidatureController candiCandidatureController = loader.getController();
            // Set up the stage and scene
            javafx.stage.Stage stage = new javafx.stage.Stage();
            stage.setScene(new Scene(root));
            stage.show();
//          Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
//          s.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    void profilCandidat(ActionEvent event) {
        CandiProfilController.setCandidat(candidat);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CandiProfil.fxml"));
            Parent root = loader.load();
            CandiProfilController candiProfilController = loader.getController();
            // Set up the stage and scene
            javafx.stage.Stage stage = new javafx.stage.Stage();
            stage.setScene(new Scene(root));
            stage.show();
//          Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
//          s.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
  @FXML
  void deconnexion(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("CandiLogin.fxml"));
    Parent root = loader.load();
    CandiLoginController candiLoginController = loader.getController();
    // Set up the stage and scene
    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.show();
    // Close the current login window (optional)
    Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
    s.close();
  }
    @FXML
    void exit(ActionEvent event) {
    	System.exit(0);
    }
}
