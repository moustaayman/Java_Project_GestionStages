package application;

public class Candidat extends Utilisateur{
    private int IdC;

  public Candidat(int IdC, String nom, String prenom, String telephone, String courriel, String username, String password) {
        super(nom, prenom, telephone, courriel, username, password);
        this.IdC = IdC;
    }

    public int getIdC() {
        return IdC;
    }

    public void setIdC(int idC) {
        IdC = idC;
    }
    @Override
    public String toString() {
        return "Utilisateur{" +
                "nom='" + super.getNom() + '\'' +
                ", prenom='" + super.getPrenom()  +
                "idC= " + getIdC() +
                '}';
    }
}
