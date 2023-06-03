package fxFiles;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

import application.Connexion;
import application.Erreur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RespoLoginController {

	@FXML
	private	TextField userNameCandidat ;
	@FXML 
	private PasswordField passwordCandidat ;
	
    @FXML
    void exit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow() ;
        stage.close();
    }

    @FXML
    void seConnecter(ActionEvent event) {
    	String Fonction = null ;
    	try {
    		Boolean response = false ;
    		int IdR = 0 ;
    		String username = userNameCandidat.getText() ;
    		String passwd = passwordCandidat.getText() ;    	
     		Connection conn = Connexion.getConnection() ;
    		String req = ("SELECT NomUtilisateur, MotDePasse, Fonction, compters.IdR  FROM responsable, compters WHERE NomUtilisateur='"+username+"' AND MotDePasse= '"+passwd+"' AND compters.IdR = responsable.IdR") ;
    		Statement st = conn.createStatement() ;
    		ResultSet rs = st.executeQuery(req) ;
    		while(rs.next()) {
    			response = true ;
    			IdR = rs.getInt(4) ;
    			Fonction = rs.getString(3) ;
    		}
    		if(userNameCandidat.getText().isEmpty() || passwordCandidat.getText().isEmpty()) {
    			Erreur.msgEmptyfields();
    			return ;
    		}
    		else if(response == true) {
    			if(Fonction.equals("stage")) {
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxFiles/RespoStageMenu.fxml"));
    			Parent root = loader.load();
    			RespoStageMenuController getMenuController = loader.getController() ;
    			getMenuController.setIdR(IdR) ;
    			
    			Scene scene = new Scene(root);
    			Stage stage = new Stage() ;
    			stage.initStyle(StageStyle.UNDECORATED);    			
    			stage.setScene(scene);    			
    			stage.show();
    			exit(event) ;
    			}
    			else {
    				FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxFiles/RespoPersoMenu.fxml"));
        			Parent root = loader.load();
        			
        			Scene scene = new Scene(root);
        			Stage stage = new Stage() ;
        			stage.initStyle(StageStyle.UNDECORATED);    			
        			stage.setScene(scene);    			
        			stage.show();
        			exit(event) ;       			
    			}
    		}else {
    			Erreur.msgInfoInvalid() ;
    			clearInputFields() ;
    		}
    	}catch(Exception e) {
    		e.printStackTrace() ;
    	}
    }

	private void clearInputFields() {
		userNameCandidat.setText("");
		passwordCandidat.setText("");	
	}
}