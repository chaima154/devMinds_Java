package tn.devMinds.controllers;


public final class UserSession {

    private static UserSession instance;

    private int id;
    private String email;
    private String mdp;
    private String prenom;
    private String nom;
    private String role;

    public int getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return mdp; }
    public String getFirstname() { return prenom; }
    public String getLastname() { return nom; }
    public String getRole() { return role; }
    public UserSession(int id, String email, String mdp, String prenom, String nom, String role) {
        this.id = id;
        this.email = email;
        this.mdp = mdp;
        this.prenom = prenom;
        this.nom = nom;
        this.role = role;
    }

    public static UserSession getInstance(int id, String email, String mdp, String prenom, String nom, String role) {
        if(instance == null) {
            instance = new UserSession(id, email, mdp, prenom, nom, role);
        }
        return instance;
    }
    public static UserSession getInstance() {
        if (instance == null) {
            throw new IllegalStateException("UserSession has not been initialized.");
        }
        return instance;
    }

    public void cleanUserSession() {
        id = 0;
        email = null;
        mdp = null;
        prenom = null;
        nom = null;
        role = null;

    }

    @Override
    public String toString() {
        return "UserSession{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", mdp='" + mdp + '\'' +
                ", prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}