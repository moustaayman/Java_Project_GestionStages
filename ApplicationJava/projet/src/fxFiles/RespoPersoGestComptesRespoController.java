package fxFiles;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.Connexion;
import application.Erreur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tableModel.CompteResponsableModel;

public class RespoPersoGestComptesRespoController {
    Connection conn = Connexion.getConnection();

    @FXML
    private TableView<CompteResponsableModel> tablesComptes;
    @FXML
    private TableColumn<CompteResponsableModel, Integer> idResponsableColumn;
    @FXML
    private TableColumn<CompteResponsableModel, String> nomColumn;
    @FXML
    private TableColumn<CompteResponsableModel, String> prenomColumn;
    @FXML
    private TableColumn<CompteResponsableModel, String> telColumn;
    @FXML
    private TableColumn<CompteResponsableModel, String> emailColumn;
    @FXML
    private TableColumn<CompteResponsableModel, String> fonctionColumn;
    @FXML
    private TableColumn<CompteResponsableModel, String> usernameColumn;
    @FXML
    private TableColumn<CompteResponsableModel, String> passwordColumn;

    @FXML
    private TextField nomResponsableField;
    @FXML
    private TextField prenomResponsableField;
    @FXML
    private TextField telResponsableField;
    @FXML
    private TextField emailResponsableField;
    @FXML
    private ChoiceBox<String> fonctionResponsableChoiceBox;

    public void initialize() {
        tablesComptes.setEditable(true);

        idResponsableColumn.setCellValueFactory(new PropertyValueFactory<>("idResponsable"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        telColumn.setCellValueFactory(new PropertyValueFactory<>("tel"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        fonctionColumn.setCellValueFactory(new PropertyValueFactory<>("fonction"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        ObservableList<CompteResponsableModel> data = fetchDataFromDatabase();
        tablesComptes.setItems(data);
        ObservableList<String> ecoleList = FXCollections.observableArrayList();
                ecoleList.add("stage");
                ecoleList.add("personnel");
                fonctionResponsableChoiceBox.setItems(ecoleList);
    }

    private ObservableList<CompteResponsableModel> fetchDataFromDatabase() {
        ObservableList<CompteResponsableModel> data = FXCollections.observableArrayList();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT responsable.IdR, responsable.Nom, Prenom, Telephone, Courrier, NomUtilisateur, MotDePasse,Fonction FROM `responsable`, compters WHERE responsable.IdR = compters.IDR ");
            while (rs.next()) {
                int idResponsable = rs.getInt(1);
                String nom = rs.getString(2);
                String prenom = rs.getString(3);
                String tel = rs.getString(4);
                String email = rs.getString(5);
                String username = rs.getString(6);
                String password = rs.getString(7);
                String fonction = rs.getString(8);

                CompteResponsableModel compteResponsable = new CompteResponsableModel(idResponsable, nom, prenom, tel, email, username, password,fonction);
                data.add(compteResponsable);
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    @FXML
    void ajouterCompteResponsable(ActionEvent event) {
        String nom = nomResponsableField.getText();
        String prenom = prenomResponsableField.getText();
        String tel = telResponsableField.getText();
        String email = emailResponsableField.getText();
        String fonction = fonctionResponsableChoiceBox.getValue();

        if (nomResponsableField.getText().isEmpty()||prenomResponsableField.getText().isEmpty()||telResponsableField.getText().isEmpty()||
        		emailResponsableField.getText().isEmpty()||fonctionResponsableChoiceBox.getValue()==null) {
            Erreur.msgEmptyfields();
        }else if(!isNumeric(telResponsableField.getText())) {
        	Erreur.msgNotNumeric("Telephone");
        }else{
            try {
                String query = "INSERT INTO `responsable` (`IdR`, `Nom`, `Prenom`, `Telephone`, `Courrier`, Fonction) VALUES (NULL, ?, ?, ?, ?, ?)";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, nom);
                pst.setString(2, prenom);
                pst.setString(3, tel);
                pst.setString(4, email);
                pst.setString(5, fonction);
                pst.executeUpdate();
                query = "SELECT IdR FROM responsable WHERE Nom = ?";
                pst = conn.prepareStatement(query);
                pst.setString(1, nom);
                ResultSet rs = pst.executeQuery();
                rs.next();
                int idR = rs.getInt(1);
                query = "INSERT INTO `compters` (`IdCompte`, `NomUtilisateur`, `MotDePasse`, `IdR`) VALUES (NULL, ?, ?, ?)";
                pst = conn.prepareStatement(query);
                String username = nom + prenom;
                String password = nom + prenom + idR;
                pst.setString(1, username);
                pst.setString(2, password);
                pst.setInt(3, idR);
                pst.executeUpdate();
                clearInputFields();
                tablesComptes.getItems().removeAll(tablesComptes.getItems());
                initialize();
                pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
        nomResponsableField.setText("");
        prenomResponsableField.setText("");
        telResponsableField.setText("");
        emailResponsableField.setText("");
        fonctionResponsableChoiceBox.getSelectionModel().clearSelection();
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
    void reinitialiserMotdePasse(ActionEvent event) {
        CompteResponsableModel selectedCompte = tablesComptes.getSelectionModel().getSelectedItem();
        if (selectedCompte != null) {
            String username = selectedCompte.getUsername();
            String newPassword = selectedCompte.getNom() + selectedCompte.getPrenom() + selectedCompte.getIdResponsable();
            try {
                String query = "UPDATE compters SET MotDePasse = ? WHERE NomUtilisateur = ?";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, newPassword);
                pst.setString(2, username);

                int rowsUpdated = pst.executeUpdate();
                if (rowsUpdated > 0) {
                    selectedCompte.setPassword(newPassword);
                    tablesComptes.refresh();
                }
                pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void supprimerCompte(ActionEvent event) {
        CompteResponsableModel selectedCompte = tablesComptes.getSelectionModel().getSelectedItem();
        if (selectedCompte != null) {
            int idResponsable = selectedCompte.getIdResponsable();
            try {
                String query = "DELETE FROM compters WHERE IDR = ?";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, idResponsable);
                pst.executeUpdate();
                tablesComptes.getItems().removeAll(tablesComptes.getItems());
                initialize();
                pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
