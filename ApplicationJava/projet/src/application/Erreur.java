package application;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class  Erreur {
	
	public static void msgNotNumeric(String str) {
		Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid input!");
        alert.setContentText( str + " doit etre un nombre");
        alert.showAndWait();
	}
	public static void msgEmptyfields() {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("Error");
	    alert.setHeaderText("Some fields are empty!");
	    alert.setContentText("Please fill in all the required fields.");
	    alert.showAndWait();
	}
	public static void msgKeyNotExist(String str) {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("Error");
	    alert.setHeaderText(str + " n'existe pas dans la base de donnée !");
	    alert.setContentText("Reécrire " + str);
	    alert.showAndWait();
	}
	public static void msgInfoInvalid() {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("Error");
	    alert.setHeaderText("Invalid User Name or Password !");
	    alert.setContentText("Veuillez vérifier vos informations ");
	    alert.showAndWait();
	}
	public static void msgNumberOutOfRange() {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("Error");
	    alert.setHeaderText("Note Invalid!");
	    alert.setContentText("Veuillez vérifier les notes données ");
	    alert.showAndWait();
	}
}
