package tn.devMinds.models;

import java.time.LocalDate;

public class Tranche {
    private int id;
    private int CreditId;
    private LocalDate DateEcheance;
    private float MontantPaiement;
    private String StatutPaiement;

    public Tranche(){};

    public Tranche(int id, int CreditId, LocalDate dateEcheance, float montantPaiement, String statutPaiement) {
        this.id = id;
        CreditId = CreditId;
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

    public float getMontantPaiement() {
        return MontantPaiement;
    }

    public void setMontantPaiement(float montantPaiement) {
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
