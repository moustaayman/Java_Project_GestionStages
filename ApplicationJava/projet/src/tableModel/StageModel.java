package tableModel;

public class StageModel {
    private int idStage;
    private String titre;
    private String sujet;
    private int duree;
    private int idR;
	public StageModel(int idStage, String titre, String sujet, int duree, int idR) {
		super();
		this.idStage = idStage;
		this.titre = titre;
		this.sujet = sujet;
		this.duree = duree;
		this.idR = idR;
	}
	public int getIdStage() {
		return idStage;
	}
	public void setIdStage(int idStage) {
		this.idStage = idStage;
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
	public int getIdR() {
		return idR;
	}
	public void setIdR(int idR) {
		this.idR = idR;
	}
	
}