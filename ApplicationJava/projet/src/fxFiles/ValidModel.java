package fxFiles;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ValidModel {

	private int IdC , IdS; 
	private String Nom , Prenom ;
	private String Titre ; 
	private int Durree ; 
	private String Etat ; 
	private int Priorite ;
	private BooleanProperty Valide ;
	private BooleanProperty Refuse ;

	public ValidModel(int idC,  String nom, String prenom,int idS, String titre,
			int durree, String etat, int priorite) {
		IdC = idC;
		IdS = idS;
		Nom = nom;
		Prenom = prenom;
		Titre = titre;
		Durree = durree;
		Etat = etat;
		Priorite = priorite;
		this.Valide = new SimpleBooleanProperty(false);
		this.Refuse = new SimpleBooleanProperty(false);
		
	}
	public int getIdC() {
		return IdC;
	}
	public void setIdC(int idC) {
		IdC = idC;
	}
	public int getIdS() {
		return IdS;
	}
	public void setIdS(int idS) {
		IdS = idS;
	}
	public String getNom() {
		return Nom;
	}
	public void setNom(String nom) {
		Nom = nom;
	}
	public String getPrenom() {
		return Prenom;
	}
	public void setPrenom(String prenom) {
		Prenom = prenom;
	}
	public String getTitre() {
		return Titre;
	}
	public void setTitre(String titre) {
		Titre = titre;
	}
	public int getDurree() {
		return Durree;
	}
	public void setDurree(int durree) {
		Durree = durree;
	}
	public String getEtat() {
		return Etat;
	}
	public void setEtat(String etat) {
		Etat = etat;
	}
	public int getPriorite() {
		return Priorite;
	}
	public void setPriorite(int priorite) {
		Priorite = priorite;
	} 
    public BooleanProperty ValideProperty() {
        return Valide;
    }

    public boolean isValideCandidat() {
        return Valide.get();
    }

    public void setValideCandidat(boolean Valide) {
        this.Valide.set(Valide);
    }

    public BooleanProperty RefuseProperty() {
        return Refuse ;
    }

    public boolean isRefuseCandidat() {
        return Refuse.get();
    }

    public void setRefuseCandidat(boolean refuseCandidat) {
        this.Refuse.set(refuseCandidat);
    }

	
}
