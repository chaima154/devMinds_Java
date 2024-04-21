package tn.devMinds.models;
import java.time.LocalDate;
public class Card {
        private Integer id;
        private String numero;
        private LocalDate dateExpiration;
        private Integer csv;
        private String mdp;
        private String statutCarte;
        private TypeCard typeCarte;

        private Compte compte;

        private Double solde;

    public Card() {
    }

    public Card(String numero, LocalDate dateExpiration, Integer csv, String mdp, String statutCarte, TypeCard typeCarte, Compte compte, Double solde) {
        this.numero = numero;
        this.dateExpiration = dateExpiration;
        this.csv = csv;
        this.mdp = mdp;
        this.statutCarte = statutCarte;
        this.typeCarte = typeCarte;
        this.compte = compte;
        this.solde = solde;
    }
// Getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public Integer getCsv() {
        return csv;
    }

    public void setCsv(Integer csv) {
        this.csv = csv;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getStatutCarte() {
        return statutCarte;
    }

    public void setStatutCarte(String statutCarte) {
        this.statutCarte = statutCarte;
    }

    public TypeCard getTypeCarte() {
        return typeCarte;
    }

    public void setTypeCarte(TypeCard typeCarte) {
        this.typeCarte = typeCarte;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public Double getSolde() {
        return solde;
    }

    public void setSolde(Double solde) {
        this.solde = solde;
    }










}

