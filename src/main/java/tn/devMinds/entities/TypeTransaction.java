package tn.devMinds.entities;

public class TypeTransaction {
    private int id;
    private String libelle;

    private Double Commission;

    public TypeTransaction(int id, String libelle, Double commission) {
        this.id = id;
        this.libelle = libelle;
        Commission = commission;
    }

    public TypeTransaction() {
        this.Commission = 0.0; // Set default commission to 0
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Double getCommission() {
        return Commission;
    }

    public void setCommission(Double commission) {
        Commission = commission;
    }

    @Override
    public String toString() {
        return "TypeTransaction{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                ", Commission=" + Commission +
                '}';
    }
}
