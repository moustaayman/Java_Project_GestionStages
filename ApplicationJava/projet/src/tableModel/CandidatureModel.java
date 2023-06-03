package tableModel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class CandidatureModel {
    private Integer candidatId;
    private Integer offreId;
    private Integer priorite;
    private String nom;
    private String prenom;
    private BooleanProperty selected;

    public CandidatureModel(Integer candidatId, Integer offreId, Integer priorite,String nom,String prenom) {
        this.candidatId = candidatId;
        this.offreId = offreId;
        this.priorite = priorite;
        this.nom = nom;
        this.prenom = prenom;
        this.selected = new SimpleBooleanProperty(false);
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

	public Integer getCandidatId() {
        return candidatId;
    }

    public void setCandidatId(Integer candidatId) {
        this.candidatId = candidatId;
    }

    public Integer getOffreId() {
        return offreId;
    }

    public void setOffreId(Integer offreId) {
        this.offreId = offreId;
    }

    public Integer getPriorite() {
        return priorite;
    }

    public void setPriorite(Integer priorite) {
        this.priorite = priorite;
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
}
