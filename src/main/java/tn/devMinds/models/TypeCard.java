package tn.devMinds.models;

public class TypeCard {

    private Integer id;
    private String typeCarte;
    private String descriptionCarte;
    private Float frais;
    private String statusTypeCarte;

    public TypeCard() {
    }

    public TypeCard(String typeCarte, String descriptionCarte, Float frais, String statusTypeCarte) {
        this.typeCarte = typeCarte;
        this.descriptionCarte = descriptionCarte;
        this.frais = frais;
        this.statusTypeCarte = statusTypeCarte;
    }

    public String getStatusTypeCarte() {
        return statusTypeCarte;
    }
    public void setStatusTypeCarte(String statusTypeCarte) {
        this.statusTypeCarte = statusTypeCarte;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeCarte() {
        return typeCarte;
    }

    public void setTypeCarte(String typeCarte) {
        this.typeCarte = typeCarte;
    }

    public String getDescriptionCarte() {
        return descriptionCarte;
    }

    public void setDescriptionCarte(String descriptionCarte) {
        this.descriptionCarte = descriptionCarte;
    }

    public Float getFrais() {
        return frais;
    }

    public void setFrais(Float frais) {
        this.frais = frais;
    }
}
