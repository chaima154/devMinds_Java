package tn.devMinds.entities;

public class TypeTransaction {
    private int id;
    private String libelle;

    public TypeTransaction() {
    }

    public TypeTransaction(String libelle) {

        this.libelle = libelle;
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

    @Override
    public String toString() {
        return "TypeTransaction{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
