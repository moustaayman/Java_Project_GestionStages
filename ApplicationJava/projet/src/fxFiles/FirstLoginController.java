package fxFiles;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FirstLoginController {

  @FXML
  void exit(ActionEvent event) {
    System.exit(0);
  }

  @FXML
  void interfaceCandidat(ActionEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxFiles/CandiLogin.fxml"));
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
  void interfaceRespo(ActionEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxFiles/RespoLogin.fxml"));
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

}
