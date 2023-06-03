package tableModel;

import java.time.LocalDate;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class stagiaireModel {
    private Integer IdStagiaire;
    private LocalDate dateDarrive;
    private LocalDate dateDepart;
    private Integer joursConge;
    private Integer numBdg;
    private BooleanProperty selected;
    private String nom;
    private String prenom;
    

	public stagiaireModel(Integer IdStagiaire, LocalDate LocalDateDarrive, LocalDate LocalDateDepart, Integer joursConge,
			Integer numBdg,String nom,String prenom) {
		this.IdStagiaire = IdStagiaire;
		this.dateDarrive = LocalDateDarrive;
		this.dateDepart = LocalDateDepart;
		this.joursConge = joursConge;
		this.numBdg = numBdg;
        this.selected = new SimpleBooleanProperty(false);
        this.nom = nom;
        this.prenom = prenom;
	}

    public Integer getIdStagiaire() {
		return IdStagiaire;
	}

	public void setIdStagiaire(Integer idStagiaire) {
		IdStagiaire = idStagiaire;
	}

	public LocalDate getDateDarrive() {
		return dateDarrive;
	}

	public void setDateDarrive(LocalDate dateDarrive) {
		this.dateDarrive = dateDarrive;
	}

	public LocalDate getDateDepart() {
		return dateDepart;
	}

	public void setDateDepart(LocalDate dateDepart) {
		this.dateDepart = dateDepart;
	}

	public Integer getJoursConge() {
		return joursConge;
	}

	public void setJoursConge(Integer joursConge) {
		this.joursConge = joursConge;
	}

	public Integer getNumBdg() {
		return numBdg;
	}

	public void setNumBdg(Integer numBdg) {
		this.numBdg = numBdg;
	}

	public BooleanProperty getSelected() {
		return selected;
	}

	public void setSelected(BooleanProperty selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
        return selected.get();
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }
    
    public String getNom() {
    	return nom;
    }
    
    public void setNom(String nom) {
    	this.nom = nom;
    }
    
    public String getPrenom() {
    	return prenom;
    }
    
    public void setPrenom(String prenom) {
    	this.prenom = prenom;
    }
}
