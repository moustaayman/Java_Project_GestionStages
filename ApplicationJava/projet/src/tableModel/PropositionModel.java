package tableModel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class PropositionModel {
    private int IdStage;
    private String Titre;
    private String Sujet;
    private int Duree;
    private BooleanProperty selected;

    public PropositionModel(Integer IdStage, String Titre, String Sujet, Integer Duree) {
        this.IdStage = IdStage;
        this.Titre = Titre;
        this.Sujet = Sujet;
        this.Duree = Duree;
        this.selected = new SimpleBooleanProperty(false);
    }

	public Integer getIdStage() {
		return IdStage;
	}

	public void setIdStage(Integer idStage) {
		IdStage = idStage;
	}

	public String getTitre() {
		return Titre;
	}

	public void setTitre(String titre) {
		Titre = titre;
	}

	public String getSujet() {
		return Sujet;
	}

	public void setSujet(String sujet) {
		Sujet = sujet;
	}

	public Integer getDuree() {
		return Duree;
	}

	public void setDuree(Integer duree) {
		Duree = duree;
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
