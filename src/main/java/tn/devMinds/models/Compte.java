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
}
