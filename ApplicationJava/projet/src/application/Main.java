package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
// Login:
		//  Principale:					FirstLogin.fxml
		// 	Responsable: 				RespoLogin.fxml
		//  Candidat:					CandiLogin.fxml
		
// Candidat:
		//	Menu:						CandiMenu.fxml
		//  Offres:						CandiOffres.fxml
		//	Mes Candidatures:			CandiCandidature.fxml
		//  Mon Profil:					CandiProfil.fxml
		
// Responsable Personnel:
		//	Menu:						RespoPersoMenu.fxml
		//	Gestion d'Inscriptions: 	RespoPersoGestInscription.fxml
		//	Gestion des Candidatures:	RespoPersoGestCandidature.fxml
		//	Gestion des Stagiaires:		RespoPersoGestStagiaires.fxml
		//	Gestion des Comptes candidat:		RespoPersoGestComptesCand.fxml		
		//	Gestion des Comptes responsable:		RespoPersoGestComptesRespo.fxml		
		
// Responsable Stage:
		//  Menu:						RespoStageMenu.fxml				
		//	Proposition des Stages		RespoStagePropStages.fxml
		//	Validation des Candidats	RespoStageValidCandi.fxml
		//	Evaluation des Stagiaiares	RespoStageEvalStagiaires.fxml
		
		
			Parent root = FXMLLoader.load(getClass().getResource("/fxFiles/FirstLogin.fxml"));
			Scene scene = new Scene(root);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			
			primaryStage.setScene(scene);
			primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
