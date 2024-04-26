package tn.devMinds.models;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

public class Credit {
    private int id;
    private double MontantCredit;
    private int Duree;
    private double TauxInteret;
    private LocalDate DateObtention;
    private double MontantRestant;
    private String StatutCredit;
    private String TypeCredit;
    private String documentcin;
    private double Salaire;
    private String CategorieProfessionelle;
    private String TypeSecteur;
    private String SecteurActivite;

    private Set<Tranche> tranches = new TreeSet<>();

    private int compteId;

    public Credit(int id, double montantCredit, int duree, double tauxInteret, LocalDate dateObtention, double montantRestant, String statutCredit, String typeCredit, String documentcin, double salaire, String categorieProfessionelle, String typeSecteur, String secteurActivite, int compteId) {
        this.id = id;
        MontantCredit = montantCredit;
        Duree = duree;
        TauxInteret = tauxInteret;
        DateObtention = dateObtention;
        MontantRestant = montantRestant;
        StatutCredit = statutCredit;
        TypeCredit = typeCredit;
        this.documentcin = documentcin;
        Salaire = salaire;
        CategorieProfessionelle = categorieProfessionelle;
        TypeSecteur = typeSecteur;
        SecteurActivite = secteurActivite;
        this.compteId = compteId;
    }

    public Credit(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMontantCredit() {
        return MontantCredit;
    }

    public void setMontantCredit(double montantCredit) {
        MontantCredit = montantCredit;
    }

    public int getDuree() {
        return Duree;
    }

    public void setDuree(int duree) {
        Duree = duree;
    }

    public double getTauxInteret() {
        return TauxInteret;
    }

    public void setTauxInteret(double tauxInteret) {
        TauxInteret = tauxInteret;
    }

    public LocalDate getDateObtention() {
        return DateObtention;
    }

    public void setDateObtention(LocalDate dateObtention) {
        DateObtention = dateObtention;
    }

    public double getMontantRestant() {
        return MontantRestant;
    }

    public void setMontantRestant(double montantRestant) {
        MontantRestant = montantRestant;
    }

    public String getStatutCredit() {
        return StatutCredit;
    }

    public void setStatutCredit(String statutCredit) {
        StatutCredit = statutCredit;
    }

    public String getTypeCredit() {
        return TypeCredit;
    }

    public void setTypeCredit(String typeCredit) {
        TypeCredit = typeCredit;
    }

    public double getSalaire() {
        return Salaire;
    }

    public void setSalaire(double salaire) {
        Salaire = salaire;
    }

    public String getCategorieProfessionelle() {
        return CategorieProfessionelle;
    }

    public void setCategorieProfessionelle(String categorieProfessionelle) {
        CategorieProfessionelle = categorieProfessionelle;
    }

    public String getTypeSecteur() {
        return TypeSecteur;
    }

    public void setTypeSecteur(String typeSecteur) {
        TypeSecteur = typeSecteur;
    }

    public String getSecteurActivite() {
        return SecteurActivite;
    }

    public void setSecteurActivite(String secteurActivite) {
        SecteurActivite = secteurActivite;
    }

    public Set<Tranche> getTranches() {
        return tranches;
    }

    public void setTranches(Set<Tranche> tranches) {
        this.tranches = tranches;
    }

    public int getCompteId() {
        return compteId;
    }

    public void setCompteId(int compteId) {
        this.compteId = compteId;
    }

    public String getDocumentcin() {
        return documentcin;
    }

    public void setDocumentcin(String documentcin) {
        this.documentcin = documentcin;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "id=" + id +
                ", MontantCredit=" + MontantCredit +
                ", Duree=" + Duree +
                ", TauxInteret=" + TauxInteret +
                ", DateObtention=" + DateObtention +
                ", MontantRestant=" + MontantRestant +
                ", StatutCredit='" + StatutCredit + '\'' +
                ", TypeCredit='" + TypeCredit + '\'' +
                ", documentcin='" + documentcin + '\'' +
                ", Salaire=" + Salaire +
                ", CategorieProfessionelle='" + CategorieProfessionelle + '\'' +
                ", TypeSecteur='" + TypeSecteur + '\'' +
                ", SecteurActivite='" + SecteurActivite + '\'' +
                ", compteId=" + compteId +
                '}';
    }
}
