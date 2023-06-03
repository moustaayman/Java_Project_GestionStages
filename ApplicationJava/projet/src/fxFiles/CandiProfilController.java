package fxFiles;

import application.Candidat;
import application.Connexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CandiProfilController {

    @FXML
    private PasswordField currentPassword;

    @FXML
    private TextField emailCandidat;

    @FXML
    private TextField etablissementCandidat;

    @FXML
    private PasswordField newPassword1;

    @FXML
    private PasswordField newPassword2;

    @FXML
    private TextField nomCandidat;

    @FXML
    private TextField prenomCandidat;

    @FXML
    private TextField telCandidat;
    private static Candidat candidat;
    public static void setCandidat(Candidat c) {
        candidat = c;
    }
    @FXML
    public void initialize() {
        nomCandidat.setText(candidat.getNom());
        prenomCandidat.setText(candidat.getPrenom());
        telCandidat.setText(candidat.getTelephone());
        emailCandidat.setText(candidat.getCourriel());

    }
    @FXML
    void exit(ActionEvent event) {
    	System.exit(0);
    }

    @FXML
    void modifierCordonnéesPersonnelles(ActionEvent event) {
        String newTel = telCandidat.getText();
        String newEmail = emailCandidat.getText();
        try {
            Connection conn= Connexion.getConnection();
            String updateQuery = "UPDATE candidat SET telephone = ?, courriel = ? WHERE IdCandidat=?";
            PreparedStatement statement = conn.prepareStatement(updateQuery);
            statement.setString(1, newTel);
            statement.setString(2, newEmail);
            statement.setInt(3, candidat.getIdC());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Mise à jour des données avec succès");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Erreur dans la mise à jour des données ");
                alert.showAndWait();
                System.out.println(".");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void modifierMotdePasse(ActionEvent event) {
        String currentPasswordText = currentPassword.getText();
        String newPassword1Text = newPassword1.getText();
        String newPassword2Text = newPassword2.getText();
        // Verify that the current password matches the one in the database
        if (verifyCurrentPassword(currentPasswordText)) {
            // Check if the new password and retyped password match
            if (newPassword1Text.equals(newPassword2Text)) {
                // Update the password in the database
                if (updatePassword(newPassword1Text)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Mise à jour du mot de passe avec succès");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Erreur lors de la mise à jour du mot de passe");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Vous n'avez pas saisi le même mot de passe !");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Mot de passe actuel incorrect");
            alert.showAndWait();
        }

        // Clear the password fields
        currentPassword.clear();
        newPassword1.clear();
        newPassword2.clear();
    }
    boolean verifyCurrentPassword(String currentPassword) {
        try {
            Connection conn= Connexion.getConnection();
            String selectQuery = "SELECT MotDePasse FROM comptesc WHERE idCandidat = ?";
            PreparedStatement statement = conn.prepareStatement(selectQuery);
            statement.setInt(1, candidat.getIdC());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String storedPassword = resultSet.getString("MotDePasse");
                return storedPassword.equals(currentPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    boolean updatePassword(String newPassword) {
        try {
            Connection conn= Connexion.getConnection();
            String updateQuery = "UPDATE comptesc SET MotDePasse = ? WHERE idCandidat = ?";
            PreparedStatement statement = conn.prepareStatement(updateQuery);
            statement.setString(1, newPassword);
            statement.setInt(2, candidat.getIdC());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
