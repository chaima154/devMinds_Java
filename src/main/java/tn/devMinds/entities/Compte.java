package tn.devMinds.entities;

public class Compte {

    private  int id;
    private int solde;
    private int user_id;

    private String typecompte, agence;
    private String rib;

    public Compte() {
    }

    public Compte (int id ,int solde, String typecompte, String agence,String rib, int user_id) {
        this.id=id;
        this.solde = solde;
        this.typecompte = typecompte;
        this.agence = agence;
        this.rib=rib;
        this.user_id = user_id;
    }
    public Compte (int id ,int solde, String typecompte, String agence,String rib) {
        this.id=id;
        this.solde = solde;
        this.typecompte = typecompte;
        this.agence = agence;
        this.rib=rib;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSolde() {
        return solde;
    }

    public void setSolde(int solde) {
        this.solde = solde;
    }

    public String getTypecompte() {
        return typecompte;
    }

    public void setTypecompte(String typecompte) {
        this.typecompte = typecompte;
    }

    public String getAgence() {
        return agence;
    }

    public void setAgence(String agence) {
        this.agence = agence;
    }

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    @Override
    public String toString() {
        return "Compte{" +
                "id=" + id +
                ", solde=" + solde +
                ", typecompte='" + typecompte + '\'' +
                ", agence='" + agence + '\'' +"rib=" + rib + '\'' +
                '}';
    }


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
