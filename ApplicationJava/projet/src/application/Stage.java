package application;

public class Stage {
    private int idStage;
    private String titre;
    private String sujet;
    private int duree;
    private int IdResp;

  public int getIdResp() {
    return IdResp;
  }

  public void setIdResp(int idResp) {
    IdResp = idResp;
  }

  public int getIdStage() {
        return idStage;
    }
    public void setIdStage(int idOffre) {
        this.idStage = idOffre;
    }
    public String getTitre() {
        return titre;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }
    public String getSujet() {
        return sujet;
    }
    public void setSujet(String sujet) {
        this.sujet = sujet;
    }
    public int getDuree() {
        return duree;
    }
    public void setDuree(int duree) {
        this.duree = duree;
    }
    public Stage(String titre, String sujet, int duree) {
        super();
        this.titre = titre;
        this.sujet = sujet;
        this.duree = duree;
    }
    @Override
    public String toString() {
        return "Offre [idOffre=" + idStage + ", titre=" + titre + ", sujet=" + sujet + ", duree=" + duree + "]";
    }
    public Stage(int idStage, String titre, String sujet, int duree) {
        super();
        this.idStage = idStage;
        this.titre = titre;
        this.sujet = sujet;
        this.duree = duree;
    }

}
