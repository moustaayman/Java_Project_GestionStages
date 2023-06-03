package fxFiles;
import application.Erreur;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.Connexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tableModel.CandidatureModel;

public class RespoPersoGestCandidatureController {
	Connection conn = Connexion.getConnection();
    @FXML
    private TextField idCandidat;
    @FXML
    private TextField idOffre;
    @FXML
    private TextField priorité;
    @FXML
    private TableView<CandidatureModel> tableCandidatures;
    @FXML
    TableColumn<CandidatureModel, Boolean> checkBoxColumn ;
    @FXML
    TableColumn<CandidatureModel, Integer> candidatIdColumn ;
    @FXML
    TableColumn<CandidatureModel, Integer> offreIdColumn ;
    @FXML
    TableColumn<CandidatureModel, Integer> prioriteColumn ;
    @FXML
    TableColumn<CandidatureModel, String> nomColumn ;
    @FXML
    TableColumn<CandidatureModel, String> prenomColumn ;
	@SuppressWarnings("unchecked")
	public void initialize() {
    	tableCandidatures.setEditable(true);
        // Create the table columns
    	checkBoxColumn.setCellValueFactory(new PropertyValueFactory<>("selected"));
    	checkBoxColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkBoxColumn));
        candidatIdColumn.setCellValueFactory(new PropertyValueFactory<>("candidatId"));
        offreIdColumn.setCellValueFactory(new PropertyValueFactory<>("offreId"));
        prioriteColumn.setCellValueFactory(new PropertyValueFactory<>("priorite"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        // Add the columns to the TableView
        tableCandidatures.getColumns().setAll(checkBoxColumn,candidatIdColumn,nomColumn,prenomColumn, offreIdColumn, prioriteColumn);
        // Fetch the data from the database or create sample data
        ObservableList<CandidatureModel> data = fetchDataFromDatabase();
        // Set the data in the TableView
        tableCandidatures.setItems(data);
    }
    private ObservableList<CandidatureModel> fetchDataFromDatabase() {
        ObservableList<CandidatureModel> data = FXCollections.observableArrayList();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT candidature.idC,idS,priorite,Nom,Prenom FROM candidature,candidat where candidature.idC = candidat.IdCandidat and etat = 'en attente'");
            while (rs.next()) {
                int candidatId = rs.getInt(1);
                int offreId = rs.getInt(2);
                int priorite = rs.getInt(3);
                String nom = rs.getString(4);
                String prenom = rs.getString(5);

                CandidatureModel candidature = new CandidatureModel(candidatId, offreId, priorite,nom,prenom);
                data.add(candidature);
            }
            st.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
    @FXML
    void ajouterCandidature(ActionEvent event) {

        if(idCandidat.getText().isEmpty()||idOffre.getText().isEmpty()||priorité.getText().isEmpty()) {
        	Erreur.msgEmptyfields();
        }else if(!isNumeric(idCandidat.getText())){
        	Erreur.msgNotNumeric("Id Candidat");
        }else if(!isNumeric(idOffre.getText())){
        	Erreur.msgNotNumeric("idOffre");
        }else if(!isNumeric(priorité.getText())) {
        	Erreur.msgNotNumeric("priorité");
        }else{
            try {
            	Connection conn = Connexion.getConnection();
                int idC = Integer.parseInt(idCandidat.getText());
                int idoffre = Integer.parseInt(idOffre.getText());
                int priorite = Integer.parseInt(priorité.getText());
                PreparedStatement psCheck = conn.prepareStatement("SELECT * FROM candidature where idC = ? and idS = ?");
                psCheck.setInt(1, idC);
                psCheck.setInt(2, idoffre);
                ResultSet rsCheck = psCheck.executeQuery(); 
                if (!rsCheck.next()) {
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select * from candidat where idCandidat ="+ idC);
                if(rs.next()) {
                	rs = st.executeQuery("select * from stage where idS ="+ idoffre);
                	if (rs.next()) {
                		rs = st.executeQuery("select * from candidature where idC ="+ idC);
        				int i = 0;
        				while (rs.next()) {
							i++;
						}
        				if (i == 3) {
        					Alert alert = new Alert(AlertType.ERROR);
        			        alert.setTitle("Error");
        			        alert.setHeaderText("Invalid input!");
        			        alert.setContentText( "Vous avez dépassé le nombre limite des candidatures.");
        			        alert.showAndWait();
						}else {
							rs = st.executeQuery("select priorite from candidature where idC ="+ idC);
							boolean checkPriorite = false;
							while (rs.next()) {
								if (rs.getInt(1)==priorite) {
									checkPriorite = true;
								}
							}
							if (checkPriorite) {
								 	Alert alert = new Alert(AlertType.ERROR);
								 	alert.setTitle("Error");
		        			        alert.setHeaderText("Invalid input!");
		        			        alert.setContentText( "deja choisie cette priorite.");
		        			        alert.showAndWait();
							}else {
								rs = st.executeQuery("select * from candidat where idCandidat ="+ idC);
								PreparedStatement ps = conn.prepareStatement("INSERT INTO `candidature` (`idC`, `idS`, `priorite`, `etat`) VALUES (?, ?, ?, 'en attente')");
								ps.setInt(1, idC);
								ps.setInt(2, idoffre);
								ps.setInt(3, priorite);
								ps.executeUpdate();
								ps.close();
								initialize();
							}
						}
					}else {
						Erreur.msgKeyNotExist("idoffre");
					}
                }else {
					Erreur.msgKeyNotExist("idCandidat");
				}
                }else {
                	Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid input!");
                    alert.setContentText( "vous avez déjà postuler à cet offre");
                    alert.showAndWait();
                }
            } catch (SQLException e	) {
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
    void supprimerCandidature(ActionEvent event) {
    	ObservableList<CandidatureModel> selectedItems = tableCandidatures.getItems().filtered(CandidatureModel::isSelected);
		try {
		    Connection conn = Connexion.getConnection();
		    PreparedStatement st = conn.prepareStatement("DELETE FROM candidature WHERE `candidature`.`idC` = ? AND `candidature`.`idS` = ?");
		    for (CandidatureModel candidature : selectedItems) {
		        st.setInt(1, candidature.getCandidatId()); 
		        st.setInt(2, candidature.getOffreId()); 
		        st.executeUpdate();
		    }
		    // Optional: Refresh the TableView after the operation
		    tableCandidatures.getItems().removeAll(selectedItems);
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		initialize();
    }

}
