package fxFiles;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tableModel.stagiaireModel;
import application.Erreur;
public class RespoPersoGestStagiairesController {
    @FXML
    private TextField annéeArrivé;
    @FXML
    private TextField annéeDépart;
    @FXML
    private TextField jourArrivé;
    @FXML
    private TextField jourDépart;
    @FXML
    private TextField joursCongéPris;
    @FXML
    private TextField moisArrivé;
    @FXML
    private TextField moisDépart;
    @FXML
    private TextField numBadge;
    @FXML 
    private TextField IDCandidat;
    @FXML
    TableColumn<stagiaireModel, String> nom ;
    @FXML
    TableColumn<stagiaireModel, String> prenom ;
    @FXML
    TableColumn<stagiaireModel, Integer> IdStagiaire ;
    @FXML
    TableColumn<stagiaireModel, LocalDate> dtDarrive ;
    @FXML    
    TableColumn<stagiaireModel, LocalDate> dtDepart ;
    @FXML    
    TableColumn<stagiaireModel, Integer> joursConge ;
    @FXML    
    TableColumn<stagiaireModel, Integer> numBdg ;
    @FXML
    private TableView<stagiaireModel> tableCandidatures;
	@SuppressWarnings("unchecked")
	public void initialize() {
		// Configure table columns
		tableCandidatures.setEditable(true);
		IdStagiaire.setCellValueFactory(new PropertyValueFactory<>("IdStagiaire"));
		nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
		prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
		dtDarrive.setCellValueFactory(new PropertyValueFactory<>("dateDarrive"));
		dtDepart.setCellValueFactory(new PropertyValueFactory<>("dateDepart"));
		joursConge.setCellValueFactory(new PropertyValueFactory<>("joursConge"));
		numBdg.setCellValueFactory(new PropertyValueFactory<>("numBdg"));
		 // Add the columns to the TableView
        tableCandidatures.getColumns().setAll(IdStagiaire,nom,prenom,dtDarrive,dtDepart,joursConge,numBdg);
     // Fetch the data from the database or create sample data
        ObservableList<stagiaireModel> data = fetchDataFromDatabase();
        // Set the data in the TableView
        tableCandidatures.setItems(data);
	}
	private ObservableList<stagiaireModel> fetchDataFromDatabase(){
		ObservableList<stagiaireModel> data = FXCollections.observableArrayList();
		try {
			Connection conn = Connexion.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT `IdStagiaire`,`DateArrivee`,`DateDepart`,`JourCongePris`,`NumBadge`,candidat.Nom,candidat.Prenom FROM `stagiaire`,`candidat` where `stagiaire`.`IdCandidat` = `candidat`.`IdCandidat`;  ");
			while (rs.next()) {
				int IdStagiaire = rs.getInt(1);
				LocalDate dateDarrive = null;
                LocalDate dateDepart = null;
                if (rs.getDate(2)!=null) {
                    dateDarrive = rs.getDate(2).toLocalDate();
                }
                if (rs.getDate(2)!=null) {
                    dateDepart = rs.getDate(3).toLocalDate();
                }
				int joursConge = rs.getInt(4);
				int numBdg = rs.getInt(5);
				String nom = rs.getString(6);
				String prenom = rs.getString(7);
				stagiaireModel stagiaire = new stagiaireModel(IdStagiaire, dateDarrive, dateDepart, joursConge, numBdg,nom,prenom);
				data.add(stagiaire);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
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
    void valider(ActionEvent event) {
    	try {
    		if (annéeArrivé.getText().isEmpty() || moisArrivé.getText().isEmpty() || jourArrivé.getText().isEmpty()
    		        || annéeDépart.getText().isEmpty() || moisDépart.getText().isEmpty() || jourDépart.getText().isEmpty()
    		        || numBadge.getText().isEmpty() || joursCongéPris.getText().isEmpty() || IDCandidat.getText().isEmpty()) {
    			Erreur.msgEmptyfields();
    		}else if(!isNumeric(IDCandidat.getText())){
            	Erreur.msgNotNumeric("Id Candidat");
            }else if(!isNumeric(joursCongéPris.getText())){
            	Erreur.msgNotNumeric("joursCongéPris");
            }else if(!isNumeric(numBadge.getText())) {
            	Erreur.msgNotNumeric("numBadge");
            }else if(!isNumeric(jourDépart.getText())) {
            	Erreur.msgNotNumeric("jourDépart");
            }else if(!isNumeric(moisDépart.getText())) {
            	Erreur.msgNotNumeric("moisDépart");
            }else if(!isNumeric(annéeDépart.getText())) {
            	Erreur.msgNotNumeric("annéeDépart");
            }else if(!isNumeric(jourArrivé.getText())) {
            	Erreur.msgNotNumeric("jourArrivé");
            }else if(!isNumeric(moisArrivé.getText())) {
            	Erreur.msgNotNumeric("moisArrivé");
            }else if(!isNumeric(annéeArrivé.getText())) {
            	Erreur.msgNotNumeric("annéeArrivé");
            }else{
    			int anneeDarrive = Integer.parseInt(annéeArrivé.getText());
    			int moisDarrive = Integer.parseInt(moisArrivé.getText());
    			int jourDarrive = Integer.parseInt(jourArrivé.getText());
    			
    			int anneeDepart = Integer.parseInt(annéeDépart.getText());
    			int moisDepart = Integer.parseInt(moisDépart.getText());
    			int jourDepart = Integer.parseInt(jourDépart.getText());
    			
    			int numBdg = Integer.parseInt(numBadge.getText());
    			int jourConge = Integer.parseInt(joursCongéPris.getText());
    			int idcandidat = Integer.parseInt(IDCandidat.getText());
    			Connection conn = Connexion.getConnection();
    			
    			Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select * from stagiaire where IdStagiaire  ="+ idcandidat);
                if(rs.next()) {
                	LocalDate dateDarriver = LocalDate.of(anneeDarrive, moisDarrive, jourDarrive);
        			LocalDate dateDepart = LocalDate.of(anneeDepart, moisDepart, jourDepart);
    		
        			if(dateDarriver.isAfter(dateDepart)) {
        				Alert alert = new Alert(AlertType.ERROR);
        				alert.setTitle("Error");
        				alert.setHeaderText("La date d'arrivée ne peut pas être postérieure à la date de départ !");
        				alert.setContentText("Réécrire la date d'arrivée et la date de départ.");
        				alert.showAndWait();
        			}else {

        					PreparedStatement ps = conn.prepareStatement("UPDATE `stagiaire` SET `DateArrivee` = ?, `DateDepart` = ?, "
        							+ "`JourCongePris` = ?, `NumBadge` = ? WHERE `stagiaire`.`IdStagiaire` = ?");
        					ps.setDate(1, Date.valueOf(dateDarriver));
        					ps.setDate(2, Date.valueOf(dateDepart));
        					ps.setInt(3, jourConge);
        					ps.setInt(4, numBdg);
        					ps.setInt(5, idcandidat);
        					ps.executeUpdate();
        					ps.close();
        					initialize();
        			}
                }
			}
		}catch (Exception e) {
			e.printStackTrace();
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

}
