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
import tableModel.CompteCandidatModel;

public class RespoPersoGestComptesCandController {
    Connection conn = Connexion.getConnection();

    @FXML
    private TableView<CompteCandidatModel> tablesComptes;
    @FXML
    private TableColumn<CompteCandidatModel, Integer> idCandidatColumn;
    @FXML
    private TableColumn<CompteCandidatModel, String> nomColumn;
    @FXML
    private TableColumn<CompteCandidatModel, String> prenomColumn;
    @FXML
    private TableColumn<CompteCandidatModel, String> telColumn;
    @FXML
    private TableColumn<CompteCandidatModel, String> emailColumn;
    @FXML
    private TableColumn<CompteCandidatModel, String> ecoleColumn;
    @FXML
    private TableColumn<CompteCandidatModel, String> usernameColumn;
    @FXML
    private TableColumn<CompteCandidatModel, String> passwordColumn;

    @FXML
    private TextField nomCandidatField;
    @FXML
    private TextField prenomCandidatField;
    @FXML
    private TextField telCandidatField;
    @FXML
    private TextField emailCandidatField;
    @FXML
    private ChoiceBox<String> ecoleCandidatChoiceBox;

    public void initialize() {
        tablesComptes.setEditable(true);

        idCandidatColumn.setCellValueFactory(new PropertyValueFactory<>("idCandidat"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        telColumn.setCellValueFactory(new PropertyValueFactory<>("tel"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        ecoleColumn.setCellValueFactory(new PropertyValueFactory<>("ecole"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        ObservableList<CompteCandidatModel> data = fetchDataFromDatabase();
        tablesComptes.setItems(data);
        ObservableList<String> ecoleList = FXCollections.observableArrayList();
        try {
            // Execute the query
            String query = "SELECT Nom FROM `ecole`";
            Statement statement = conn.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery(query);
            // Retrieve the values and populate the ChoiceBox
            while (resultSet.next()) {
                String ecoleName = resultSet.getString(1);
                ecoleList.add(ecoleName);
            }
            ecoleCandidatChoiceBox.setItems(ecoleList);
            // Create the ChoiceBox and set the items
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private ObservableList<CompteCandidatModel> fetchDataFromDatabase() {
        ObservableList<CompteCandidatModel> data = FXCollections.observableArrayList();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT candidat.IdCandidat, candidat.Nom, Prenom, Telephone, Courriel, ecole.Nom, NomUtilisateur, MotDePasse FROM `candidat`, comptesc, ecole WHERE candidat.IdCandidat = comptesc.IDC and ecole.IdEcole = candidat.IdEcole;");
            while (rs.next()) {
                int idCandidat = rs.getInt(1);
                String nom = rs.getString(2);
                String prenom = rs.getString(3);
                String tel = rs.getString(4);
                String email = rs.getString(5);
                String ecole = rs.getString(6);
                String username = rs.getString(7);
                String password = rs.getString(8);

                CompteCandidatModel compteCandidat = new CompteCandidatModel(idCandidat, nom, prenom, tel, email,
                        ecole, username, password);
                data.add(compteCandidat);
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    @FXML
    void ajouterCompteCandidat(ActionEvent event) {

        if (nomCandidatField.getText().isEmpty()||prenomCandidatField.getText().isEmpty()||telCandidatField.getText().isEmpty()||
        		emailCandidatField.getText().isEmpty()||ecoleColumn.getText().isEmpty()) {
            Erreur.msgEmptyfields();
        }else if(!isNumeric(telCandidatField.getText())) {
        	Erreur.msgNotNumeric("Telephone");
        }else{
        	String nom = nomCandidatField.getText().trim();
        	String prenom = prenomCandidatField.getText().trim();
        	String tel = telCandidatField.getText().trim();
        	String email = emailCandidatField.getText().trim();
        	String ecole = ecoleCandidatChoiceBox.getValue().trim();
            try {
                String query = "SELECT IdEcole FROM `ecole` WHERE Nom = ?";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, ecole);
                ResultSet rs = pst.executeQuery();
                rs.next();
                int idEcole = rs.getInt(1);
                query = "INSERT INTO `candidat` (`IdCandidat`, `Nom`, `Prenom`, `Telephone`, `Courriel`, `IdEcole`) VALUES (NULL, ?, ?, ?, ?, ?)";
                pst = conn.prepareStatement(query);
                pst.setString(1, nom);
                pst.setString(2, prenom);
                pst.setString(3, tel);
                pst.setString(4, email);
                pst.setInt(5, idEcole);
                pst.executeUpdate();
                query = "SELECT IdCandidat FROM candidat WHERE Nom = ?";
                pst = conn.prepareStatement(query);
                pst.setString(1, nom);
                rs = pst.executeQuery();
                rs.next();
                int idC = rs.getInt(1);
                query = "INSERT INTO `comptesc` (`IDCC`, `NomUtilisateur`, `MotDePasse`, `IDC`) VALUES (NULL, ?, ?, ?)";
                pst = conn.prepareStatement(query);
                String username = nom + idEcole;
                String password = nom + prenom + idC ;
                pst.setString(1, username);
                pst.setString(2, password);
                pst.setInt(3, idC);
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

    private void clearInputFields() {
        nomCandidatField.setText("");
        prenomCandidatField.setText("");
        telCandidatField.setText("");
        emailCandidatField.setText("");
        ecoleCandidatChoiceBox.getSelectionModel().clearSelection();
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
        CompteCandidatModel selectedCompte = tablesComptes.getSelectionModel().getSelectedItem();
        if (selectedCompte != null) {
            String username = selectedCompte.getUsername();
            String newPassword = selectedCompte.getNom() + selectedCompte.getPrenom() + selectedCompte.getIdCandidat();
            try {
                String query = "UPDATE comptesc SET MotDePasse = ? WHERE NomUtilisateur = ?";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, newPassword);
                pst.setString(2, username);
                tablesComptes.getItems().removeAll(tablesComptes.getItems());
                initialize();
                pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void supprimerCompte(ActionEvent event) {
        CompteCandidatModel selectedCompte = tablesComptes.getSelectionModel().getSelectedItem();
        if (selectedCompte != null) {
            int idCandidat = selectedCompte.getIdCandidat();
            try {
                String query = "DELETE FROM comptesc WHERE IDC = ?";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, idCandidat);
                pst.executeUpdate();
                tablesComptes.getItems().removeAll(tablesComptes.getItems());
                initialize();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
