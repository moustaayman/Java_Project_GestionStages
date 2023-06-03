package fxFiles;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.Candidat;
import application.Connexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.IOException;


import javax.swing.*;


public class CandiLoginController {
    Candidat candidat;

    @FXML
    private TextField userNameCandidat;

    @FXML
    private PasswordField passwordCandidat;


    @FXML
    void exit(ActionEvent event) {
    	System.exit(0);
    }

    public void seConnecter(ActionEvent e) {
            String username = userNameCandidat.getText();
            String password = new String(passwordCandidat.getText());
            candidat = checkLogin(username, password);
        if (candidat != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Connexion réussie!");
            alert.showAndWait();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CandiMenu.fxml"));
                Parent root = loader.load();
                CandiMenuController candiMenuController = loader.getController();
                candiMenuController.setCandidat(candidat);
                // Set up the stage and scene
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                // Close the current login window (optional)
                Stage loginStage = (Stage) userNameCandidat.getScene().getWindow();
                loginStage.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("usename ou password invalide!");
            alert.showAndWait();        }
    }

    private Candidat checkLogin(String username, String password) {
        try {
            // connect to the database
            Connection conn=Connexion.getConnection();

            // prepare the statement
            String sqluser = "SELECT * FROM comptesc WHERE NomUtilisateur=? AND MotDePasse=?";
            PreparedStatement statementuser = conn.prepareStatement(sqluser);
            statementuser.setString(1, username);
            statementuser.setString(2, password);
            String sqlcandidat = "SELECT * FROM candidat WHERE candidat.IdCandidat=?";
            // execute the query
            ResultSet resultuser = statementuser.executeQuery();

            // check if there is a result
            boolean success = resultuser.next();

            if(success) {
                PreparedStatement statementcandidat = conn.prepareStatement(sqlcandidat);
                statementcandidat.setInt(1, resultuser.getInt("IdC"));
                ResultSet resultcandidat = statementcandidat.executeQuery();
                resultcandidat.next();
                candidat = new Candidat(resultcandidat.getInt("IdCandidat"), resultcandidat.getString("nom"), resultcandidat.getString("prenom"), resultcandidat.getString("telephone"), resultcandidat.getString("courriel"), resultuser.getString("NomUtilisateur"), resultuser.getString("MotDePasse"));
                resultcandidat.close();
                statementcandidat.close();
                return candidat ;
            }

            // close the resources
            resultuser.close();
            statementuser.close();
            //conn.close();

            return null;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }



}
