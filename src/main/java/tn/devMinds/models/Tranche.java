package tn.devMinds.models;

import java.time.LocalDate;

public class Tranche {
    private int id;
    private int CreditId;
    private LocalDate DateEcheance;
    private double MontantPaiement;
    private String StatutPaiement;

    public Tranche(){};

    public Tranche(int CreditId, LocalDate dateEcheance, double montantPaiement, String statutPaiement) {
        this.CreditId = CreditId;
        DateEcheance = dateEcheance;
        MontantPaiement = montantPaiement;
        StatutPaiement = statutPaiement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreditId() { return CreditId; }

    public void setCreditId(int creditId) { CreditId = creditId; }

    public double getMontantPaiement() {
        return MontantPaiement;
    }

    public void setMontantPaiement(double montantPaiement) {
        MontantPaiement = montantPaiement;
    }

    public String getStatutPaiement() {
        return StatutPaiement;
    }

    public void setStatutPaiement(String statutPaiement) {
        StatutPaiement = statutPaiement;
    }

    public LocalDate getDateEcheance() {
        return DateEcheance;
    }

    public void setDateEcheance(LocalDate dateEcheance) {
        DateEcheance = dateEcheance;
    }

    @Override
    public String toString() {
        return "Tranche{" +
                "id=" + id +
                ", CreditId=" + CreditId +
                ", DateEcheance=" + DateEcheance +
                ", MontantPaiement=" + MontantPaiement +
                ", StatutPaiement='" + StatutPaiement + '\'' +
                '}';
    }
}
