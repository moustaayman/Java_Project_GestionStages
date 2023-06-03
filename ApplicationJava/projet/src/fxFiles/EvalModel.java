package fxFiles;

public class EvalModel {

	private int IdStagiaire ;
	private String Nom ;
	private String Prenom ;
	private String NumBadge ;
	private double NoteTravail ; 
	private double NoteComportement ;
	private double NoteRapportEcrit ;
	
	public EvalModel(int idStagiaire, String nom, String prenom, String numBadge, double noteTravail, double noteComportement,
			double noteRE) {
		IdStagiaire = idStagiaire;
		Nom = nom;
		Prenom = prenom;
		NumBadge = numBadge;
		NoteTravail = noteTravail;
		NoteComportement = noteComportement;
		NoteRapportEcrit = noteRE;
	}
	
	public int getIdStagiaire() {
		return IdStagiaire;
	}
	public void setIdStagiaire(int idStagiaire) {
		IdStagiaire = idStagiaire;
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
	public String getNumBadge() {
		return NumBadge;
	}
	public void setNumBadge(String numBadge) {
		NumBadge = numBadge;
	}
	public double getNoteTravail() {
		return NoteTravail;
	}
	public void setNoteTravail(int noteTravail) {
		NoteTravail = noteTravail;
	}
	public double getNoteComportement() {
		return NoteComportement;
	}
	public void setNoteComportement(int noteComportement) {
		NoteComportement = noteComportement;
	}
	public double getNoteRapportEcrit() {
		return NoteRapportEcrit;
	}
	public void setNoteRapportEcrit(int noteRE) {
		NoteRapportEcrit = noteRE;
	}	
}
