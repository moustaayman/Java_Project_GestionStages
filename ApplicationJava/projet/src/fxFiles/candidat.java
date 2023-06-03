package fxFiles;

public class candidat {
		private int idC ;
	    private String nom;
	    private String prenom;
	    private String adresse;
	    private String email;
	    private String telephone;
	    private int idE;

	    public candidat(int idC,String nom, String prenom, String adresse, String email, String telephone) {
	        this.idC = idC;
	    	this.nom = nom;
	        this.prenom = prenom;
	        this.adresse = adresse;
	        this.email = email;
	        this.telephone = telephone;
	    }

	    // Getters et Setters

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

	    public String getAdresse() {
	        return adresse;
	    }

	    public void setAdresse(String adresse) {
	        this.adresse = adresse;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getTelephone() {
	        return telephone;
	    }

	    public void setTelephone(String telephone) {
	        this.telephone = telephone;
	    }

	   /*public String getEcole() {
	        return idE;
	    }

	    public void setEcole(String e) {
	        this.universite = universite;
	    }*/
	}