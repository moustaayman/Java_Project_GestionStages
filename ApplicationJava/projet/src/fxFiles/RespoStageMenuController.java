package fxFiles;

import java.io.IOException;

import application.Main;
import javafx.scene.Node;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RespoStageMenuController {
	int IdR ;	

	void setIdR(int id) {
		IdR = id ;		
	}
	
	@FXML
	public void déconnexion(ActionEvent event) {
		exit(event) ;
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxFiles/RespoLogin.fxml"));
			root = loader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage() ;
			stage.initStyle(StageStyle.UNDECORATED);    			
			stage.setScene(scene);
			stage.show();	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    @FXML
    void evalStagiaire(ActionEvent event) {
       	Parent root;
    		try {
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxFiles/RespoStageEvalStagiaires.fxml"));
    			root = loader.load();
    			RespoStageEvalStagiairesController getController = loader.getController() ;
    			getController.setIdR(IdR) ;
    			Scene scene = new Scene(root);
    			Stage stage = new Stage() ;
    			stage.initStyle(StageStyle.UNDECORATED);    			
    			stage.setScene(scene);
    			stage.show();
    			exit(event) ;    			    			
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    }

    @FXML
    void exit(ActionEvent event) {
	    Stage stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow() ;
	    stage1.close();
	}

    @FXML
    void propStage(ActionEvent event) {
    	Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxFiles/RespoStagePropStages.fxml"));
			root = loader.load();
			RespoStagePropStagesController getController = loader.getController() ;
			getController.setIdR(IdR) ;
			Scene scene = new Scene(root);
			Stage stage = new Stage() ;
			stage.initStyle(StageStyle.UNDECORATED);    			
			stage.setScene(scene);
			stage.show();
			exit(event) ;			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void validCandid(ActionEvent event) {
       	Parent root;
    		try {
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxFiles/RespoStageValidCandi.fxml"));
    			root = loader.load();
    			RespoStageValidCandiController getController = loader.getController() ;
    			getController.setIdR(IdR) ;
    			Scene scene = new Scene(root);
    			Stage stage = new Stage() ;
    			stage.initStyle(StageStyle.UNDECORATED);    			
    			stage.setScene(scene);
    			stage.show();
    			exit(event) ;
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    }
}