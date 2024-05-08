package tn.devMinds.models;

public class Compte {

    private Integer id;

    private String rib;

    private Float solde;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Compte() {
    }

    public Compte(Integer id) {
        this.id = id;
    }

    public Compte(Integer id, String rib) {
        this.id = id;
        this.rib = rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public void setSolde(Float solde) {
        this.solde = solde;
    }

    public String getRib() {
        return rib;
    }

    public Float getSolde() {
        return solde;
    }

    @Override
    public String toString() {
        return "Compte{" +
                "id=" + id +
                ", rib='" + rib + '\'' +
                ", solde=" + solde +
                '}';
    }
}
