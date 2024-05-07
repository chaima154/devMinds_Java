package tn.devMinds.entities;

public class Transaction {
    private int id;
    private String date;
    private String statut;
    private double montant_transaction;
    private String numcheque;
    private int typetransaction_id;
    private int compte_id;
    private int destinataire_compte_id_id;

    public Transaction() {
    }

    public Transaction(int id, String date, String statut, double montant_transaction, String numcheque, int typetransaction_id, int compte_id, int destinataire_compte_id_id) {
        this.id = id;
        this.date = date;
        this.statut = statut;
        this.montant_transaction = montant_transaction;
        this.numcheque = numcheque;
        this.typetransaction_id = typetransaction_id;
        this.compte_id = compte_id;
        this.destinataire_compte_id_id = destinataire_compte_id_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public double getMontant_transaction() {
        return montant_transaction;
    }

    public void setMontant_transaction(double montant_transaction) {
        this.montant_transaction = montant_transaction;
    }

    public String getNumcheque() {
        return numcheque;
    }

    public void setNumcheque(String numcheque) {
        this.numcheque = numcheque;
    }

    public int getTypetransaction_id() {
        return typetransaction_id;
    }

    public void setTypetransaction_id(int typetransaction_id) {
        this.typetransaction_id = typetransaction_id;
    }

    public int getCompte_id() {
        return compte_id;
    }

    public void setCompte_id(int compte_id) {
        this.compte_id = compte_id;
    }

    public int getDestinataire_compte_id_id() {
        return destinataire_compte_id_id;
    }

    public void setDestinataire_compte_id_id(int destinataire_compte_id_id) {
        this.destinataire_compte_id_id = destinataire_compte_id_id;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", statut='" + statut + '\'' +
                ", montant_transaction=" + montant_transaction +
                ", numcheque='" + numcheque + '\'' +
                ", typetransaction_id=" + typetransaction_id +
                ", compte_id=" + compte_id +
                ", destinataire_compte_id_id=" + destinataire_compte_id_id +
                '}';
    }
}
