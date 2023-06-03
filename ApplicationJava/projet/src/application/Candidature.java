package application;

import javafx.beans.property.SimpleBooleanProperty;

public class Candidature {
    private int IdCandidat;
    private String titre;
    private int IdStage;
    private int prioriteStage;
    private String etat;
  private SimpleBooleanProperty isSelected;
    public int getIdCandidat() {
        return IdCandidat;
    }

    public void setIdCandidat(int idCandidat) {
        IdCandidat = idCandidat;
    }

  public Candidature(int idStage, String titre, int prioriteStage, String etat) {
    this.IdStage = idStage;
    this.titre = titre;
    this.prioriteStage = prioriteStage;
    this.etat = etat;
    this.isSelected = new SimpleBooleanProperty(false);
  }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getIdStage() {
        return IdStage;
    }

    public void setIdStage(int idStage) {
        IdStage = idStage;
    }

    public int getPrioriteStage() {
        return prioriteStage;
    }

    public void setPrioriteStage(int prioriteStage) {
        this.prioriteStage = prioriteStage;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
  public boolean isSelected() {
    return isSelected.get();
  }

  public void setSelected(boolean selected) {
    isSelected.set(selected);
  }

  public SimpleBooleanProperty selectedProperty() {
    return isSelected;
  }

    @Override
    public String toString() {
        return "Candidature{" +
                "IdCandidat=" + IdCandidat +
                ", titre='" + titre + '\'' +
                ", IdStage=" + IdStage +
                ", prioriteStage=" + prioriteStage +
                ", etat='" + etat + '\'' +
                '}';
    }
}
