package tn.devMinds.entities;

public class user {
    private int id;
    private String email;
    private String mdp;
    private String nom;
    private String prenom;
    private String role;

    public user(int id, String text, String text1, String text2) {
    }

    //Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getEmail() {return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMdp() { return mdp; }
    public void setMdp(String mdp) { this.mdp = mdp; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public user() {}


    //Add Constructor
    public user( String email, String mdp, String prenom, String nom) {

        this.email = email;
        this.mdp = mdp;
        this.prenom = prenom;
        this.nom = nom;
        this.role = "user";
    }
    //Constructor for the update
    public user(int id, String email, String mdp, String prenom, String nom) {
        this.id = id;
        this.email = email;
        this.mdp = mdp;
        this.prenom = prenom;
        this.nom = nom;
    }
    //Login Constructor
    public user(int id, String email, String mdp, String prenom, String nom, String role) {
        this.id = id;
        this.email = email;
        this.mdp = mdp;
        this.prenom = prenom;
        this.nom = nom;
        this.role = role;
    }

    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", mdp='" + mdp + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
