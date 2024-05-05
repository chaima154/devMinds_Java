package tn.devMinds.entities;


import tn.devMinds.controllers.GoogleAuthenticatorUtil;

public class User {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String mdp;
    private Role role;
    private String secretKey;


    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


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

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + nom + '\'' +
                ", lastName='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + mdp + '\'' +
                ", role=" + role +
                '}';
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public class AuthenticationService {

        public boolean authenticateUser(User user, int userCode) {
            // Récupérer le secret partagé associé à l'utilisateur depuis la base de données
            String secretKey = user.getSecretKey();

            // Vérifier le code d'authentification
            return GoogleAuthenticatorUtil.isCodeValid(secretKey, userCode);
        }
    }
}

