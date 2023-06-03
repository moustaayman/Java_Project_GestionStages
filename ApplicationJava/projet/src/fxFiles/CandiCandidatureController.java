package fxFiles;

import application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CandiCandidatureController {
  private boolean applicationValidated = false;
    @FXML
    private TableView<Candidature> tableCandidature;
    @FXML
    private TableColumn<Candidature, Integer> ApplicationNumberColumn;

    @FXML
    private TableColumn<Candidature, String> ApplicationTitleColumn;

    @FXML
    private TableColumn<Candidature, Integer> ApplicationPriorityColumn;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private TableColumn<Candidature, Integer> ApplicationStateColumn;
  @FXML
  private TableColumn<Candidature, Boolean> selectColumn;

    private utilitaireCandidature uc = new utilitaireCandidature();
    private static Candidat candidat;
    private Candidature selectedCandidature;

    public static void setCandidat(Candidat c) {
        candidat = c;
    }

    @FXML
    public void initialize() {
        choiceBox.setItems(FXCollections.observableArrayList("1", "2", "3"));

        ApplicationNumberColumn.setCellValueFactory(new PropertyValueFactory<>("IdStage"));
        ApplicationTitleColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        ApplicationPriorityColumn.setCellValueFactory(new PropertyValueFactory<>("prioriteStage"));
        ApplicationStateColumn.setCellValueFactory(new PropertyValueFactory<>("etat"));
        selectColumn.setCellValueFactory(new PropertyValueFactory<>("selected"));
      selectColumn.setCellFactory(CheckBoxTableCell.forTableColumn(selectColumn));
      selectColumn.setEditable(false);

      remplirTableCandidature();
    }
    public void remplirTableCandidature() {
        ObservableList<Candidature> items = tableCandidature.getItems();
        items.clear();
        ArrayList<Candidature> candidatures = uc.afficherLesCandidatures(candidat.getIdC());
        for (Candidature candidature : candidatures) {
            items.add(candidature);
            if (candidature == selectedCandidature) {
                tableCandidature.getSelectionModel().select(candidature);
            }
        }
      selectColumn.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
      selectColumn.setEditable(true);
      tableCandidature.setEditable(true);

      selectColumn.setCellFactory(column -> new CheckBoxTableCell<Candidature, Boolean>() {
        @Override
        public void updateItem(Boolean item, boolean empty) {
          super.updateItem(item, empty);

          Candidature candidature = getTableRow().getItem();
          if (candidature != null) {
            if (candidature.getEtat().equals("validé")) {
              setDisable(false);
            } else {
              setDisable(true);
            }
          }
        }
      });
    }
    @FXML
    void onChoiceBoxSelected(ActionEvent event) {
        System.out.println("onChoiceBoxSelected method called");
        selectedCandidature = tableCandidature.getSelectionModel().getSelectedItem();
        System.out.println("Selected Candidature: " + selectedCandidature);
        String selectedPriority = choiceBox.getValue();

        if (selectedPriority != null) {
            int priority = Integer.parseInt(selectedPriority);
            System.out.println("Updated Priority: " + selectedCandidature.getPrioriteStage());
            // Check if the selected priority is already assigned to another application
            boolean isPriorityAssigned = checkIfPriorityAssigned(priority);

            if (isPriorityAssigned) {
                // Display an alert message if the priority is already assigned
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("This priority is already assigned to another application.");
                alert.showAndWait();
            } else {
                // Update the priority of the selected application
                selectedCandidature.setPrioriteStage(priority);
                uc.updateCandidaturePriority(selectedCandidature.getIdStage(), priority);
                remplirTableCandidature();
            }
        } else {
            // Display an alert message if no application is selected
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please select an application.");
            alert.showAndWait();
        }
    }
    private boolean checkIfPriorityAssigned(int priority) {
        ObservableList<Candidature> items = tableCandidature.getItems();
        for (Candidature candidature : items) {
            if (candidature.getPrioriteStage() == priority && candidature != selectedCandidature) {
                return true; // Priority is already assigned to another application
            }
        }
        return false; // Priority is not assigned to any other application
    }
    @FXML
    void supprimerCandidature(ActionEvent event) {
        Candidature selectedCandidature = tableCandidature.getSelectionModel().getSelectedItem();
        if (selectedCandidature != null) {
            int IdS = selectedCandidature.getIdStage();
            try {
                if (uc.supprimerUneCandidature(candidat.getIdC(), IdS)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Suppression réussie");
                    alert.showAndWait();
                    remplirTableCandidature();
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Echec lors de la suppression de la candidature" + e.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Vous n'avez pas choisi une candidature");
            alert.showAndWait();
        }
    }
  @FXML
  void validerApplications(ActionEvent actionEvent) {
    if (applicationValidated) {
      // Show a message that the candidate has already validated an application
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setContentText("Vous avez déjà choisi une application !");
      alert.showAndWait();
      return;
    }
    Candidature selectedCandidature = tableCandidature.getSelectionModel().getSelectedItem();

    if (selectedCandidature != null && selectedCandidature.isSelected()) {
      try {
        // Get the candidate's ID
        int candidatId = candidat.getIdC();
        int stageId = selectedCandidature.getIdStage();

        uc.addCandidatToStagiaire(candidatId);
//        uc.supprimerUneCandidatureDeStage(stageId);

        // Show a success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Félicitations, vous êtes dersormais un stagiaire !");
        alert.showAndWait();
        // Set the flag to indicate that an application has been validated
        applicationValidated = true;
        // Refresh the table
        remplirTableCandidature();
      } catch (Exception e) {
        // Show an error message if an exception occurs
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Une erreur est survenue " + e.getMessage());
        alert.showAndWait();
      }
    } else {
      // Show a message that no application is selected
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setContentText("Aucune application n'est sélectionnée");
      alert.showAndWait();
    }
  }
  @FXML
    void exit(ActionEvent event) {
    System.exit(0);
    }
}
