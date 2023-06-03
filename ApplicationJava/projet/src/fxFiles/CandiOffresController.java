package fxFiles;

import application.Candidat;
import application.Stage;
import application.utilitaireCandidatStage;
import application.utilitaireCandidature;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;

public class CandiOffresController {
    private Label infoLabel;
    @FXML
    private TextFlow nomCandidat;

    @FXML
    private TextFlow nombreCandidature;

    @FXML
    private TableView<Stage> stageTable;

    @FXML
    private Label nomLabel;
    @FXML
    private Label prenomLabel;
    @FXML
    private Label maxCand;

    @FXML
    private TableColumn<Stage, String> stageNumberColumn;

    @FXML
    private TableColumn<Stage, String> stageTitleColumn;

    @FXML
    private TableColumn<Stage, String> stageSubjectColumn;

    @FXML
    private TableColumn<Stage, String> stageDurationColumn;

    private utilitaireCandidatStage us = new utilitaireCandidatStage();
    private utilitaireCandidature uc = new utilitaireCandidature();
    private static Candidat candidat;
    private  ObservableList<Stage> selectedStages = FXCollections.observableArrayList();

    public CandiOffresController() {
    }

    public static void setCandidat(Candidat c) {
        candidat = c;
    }

    public void remplirTableOffre() {
       ObservableList<Stage> items = stageTable.getItems();
//        items.clear(); // Clear existing data in the table

        ArrayList<Stage> stages = us.afficherToutesLesOffres();

        for (Stage stage : stages) {
            items.add(stage);
        }

    }
    @FXML
    public void initialize() {
        nomLabel.setText(candidat.getNom());
        prenomLabel.setText(candidat.getPrenom());
        maxCand.setText(String.valueOf(uc.nombrePostulations(candidat.getIdC())));
        stageNumberColumn.setCellValueFactory(new PropertyValueFactory<>("idStage"));
        stageTitleColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        stageSubjectColumn.setCellValueFactory(new PropertyValueFactory<>("sujet"));
        stageDurationColumn.setCellValueFactory(new PropertyValueFactory<>("duree"));
        remplirTableOffre();

    }

    @FXML
    void candidater(ActionEvent event) {
        offreSelected();
    }

    public void offreSelected() {
        int selectedRow = stageTable.getSelectionModel().getSelectedIndex();
        if (selectedRow == -1) {
            //infoLabel.setText("Veuillez sélectionner une offre.");
        } else {
            int stageId = stageTable.getItems().get(selectedRow).getIdStage();
            Stage selectedStage = getStageById(stageId);
            if (selectedStages.size() == 3) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Vous avez atteint le nombre maximum de candidatures");
                alert.showAndWait();
                //infoLabel.setText("Vous avez atteint le nombre maximum de candidatures.");
            } else if (selectedStage == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Erreur lors de la récupération de l'offre sélectionnée");
                alert.showAndWait();
                //infoLabel.setText("Erreur lors de la récupération de l'offre sélectionnée.");
            }else if (!uc.isCandidatureExist(stageId, candidat.getIdC())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Vous avez déjà sélectionné cette offre");
                alert.showAndWait();
                //infoLabel.setText("Vous avez déjà sélectionné cette offre.");
            } else {
                selectedStages.add(selectedStage);
                //infoLabel.setText("Offre sélectionnée: " + selectedStage.getTitre());
                try {
                    // Call the postuler method
                    us.postuler(candidat.getIdC(), selectedStage.getIdStage());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Candidature envoyée pour l'offre: " + selectedStage.getTitre());
                    alert.showAndWait();
                    //infoLabel.setText("Candidature envoyée pour l'offre: " + selectedStage.getTitre());
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Erreur lors de l'envoi de la candidature: " + e.getMessage());
                    alert.showAndWait();
                    //infoLabel.setText("Erreur lors de l'envoi de la candidature: " + e.getMessage());
                }
            }
        }
    }
    private Stage getStageById(int stageId) {
        ArrayList<Stage> stages = us.afficherToutesLesOffres();
        for (Stage stage : stages) {
            if (stage.getIdStage() == stageId) {
                return stage;
            }
        }
        return null;
    }
    private boolean isOfferSelected(Stage stage) {
        for (Stage selectedStage : selectedStages) {
            if (selectedStage.getIdStage() == stage.getIdStage()) {
                return true;
            }
        }
        return false;
    }
    @FXML
    void exit(ActionEvent event) {
    	System.exit(0);
    }

}
