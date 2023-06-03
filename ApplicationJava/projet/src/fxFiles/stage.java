package fxFiles;

public class stage {
    private int idStage;
    private String Titre;
    private String Sujet;
    private int duree;
    private int idR ;
    
    public stage(String intitule, String description, int duree, int idR) {
        this.Titre = intitule;
        this.Sujet = description;
        this.duree = duree;
        this.idR = idR ;
    }

    // Getters Setters

    public int getId() {
        return idStage;
    }

    public void setId(int id) {
        this.idStage = id;
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