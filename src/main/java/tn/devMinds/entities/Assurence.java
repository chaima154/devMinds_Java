package tn.devMinds.entities;

public class Assurence {
    protected int id;
    protected String nom;
    protected String description;
    protected int prime;
    protected double franchise;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrime() {
        return prime;
    }

    public void setPrime(int prime) {
        this.prime = prime;
    }

    public double getFranchise() {
        return franchise;
    }

    public void setFranchise(double franchise) {
        this.franchise = franchise;
    }

    @Override
    public String toString() {
        return "Assurance{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", prime=" + prime +
                ", franchise=" + franchise +
                '}';
    }
}
