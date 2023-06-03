package fxFiles;

public class stagiaire {
    private int idStagiaire ;
    private int idC ;
	private int idRS ;

    public stagiaire(int idStagiaire, int idC , int idRS) {
    	this.idStagiaire=idStagiaire;
    	this.idC = idC ;
    	this.idRS = idRS ;
    }
    // GettersSetters
	public int getIdStagiaire() {
		return idStagiaire;
	}

	public void setIdStagiaire(int idStagiaire) {
		this.idStagiaire = idStagiaire;
	}

	public int getIdC() {
		return idC;
	}

	public void setIdC(int idC) {
		this.idC = idC;
	}

	public int getIdRS() {
		return idRS;
	}

	public void setIdRS(int idRS) {
		this.idRS = idRS;
	}
}

