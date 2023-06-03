package tableModel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class InscriptionModel {
    private Integer candidatId;
    private Integer offreId;
    private Integer priorite;
    private String etat;
    private BooleanProperty selected;
    private String nom;
    private String prenom;

    public InscriptionModel(Integer candidatId, Integer offreId, Integer priorite, String etat,String nom,String prenom) {
        this.candidatId = candidatId;
        this.offreId = offreId;
        this.priorite = priorite;
        this.etat = etat;
        this.selected = new SimpleBooleanProperty(false);
        this.nom = nom;
        this.prenom = prenom;
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

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
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
