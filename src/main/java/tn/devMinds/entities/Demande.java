package tn.devMinds.entities;

import java.util.Date;

public class Demande {
    private int id;
    private String nom_client; // Renamed from nomClient
    private Date date_naissance_client; // Renamed from dateNaissanceClient
    private String adresse_client; // Renamed from adresseClient
    private Assurence a; // Renamed from a
    private Date date_debut_contrat; // Renamed from dateDebutContrat
    private Date duree_contrat; // Renamed from dureeContrat
    private double montant_couverture; // Renamed from montantCouverture
    private String mode_paiement; // Renamed from modePaiement
    private String etat;


    public Demande() {
        // Default constructor
        this.etat = "pending"; // Set the default value of etat to "pending"
        this.a = new Assurence();
    }

    public Demande(int id, String nomClient, Date dateNaissanceClient, String adresseClient, Assurence a,
                   Date dateDebutContrat, Date dureeContrat, double montantCouverture, String modePaiement,
                   String etat) {
        this.id = id;
        this.nom_client = nomClient;
        this.date_naissance_client = dateNaissanceClient;
        this.adresse_client = adresseClient;
        this.a = a;
        this.date_debut_contrat = dateDebutContrat;
        this.duree_contrat = dureeContrat;
        this.montant_couverture = montantCouverture;
        this.mode_paiement = modePaiement;
        this.etat = etat;
    }

    public Assurence getA() {
        return a;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNomClient() {
        return nom_client;
    }

    public void setNomClient(String nomClient) {
        this.nom_client = nomClient;
    }

    public Date getDateNaissanceClient() {
        return date_naissance_client;
    }

    public void setDateNaissanceClient(Date dateNaissanceClient) {
        this.date_naissance_client = dateNaissanceClient;
    }

    public String getAdresseClient() {
        return adresse_client;
    }

    public void setAdresseClient(String adresseClient) {
        this.adresse_client = adresseClient;
    }

    public Date getDateDebutContrat() {
        return date_debut_contrat;
    }

    public void setDateDebutContrat(Date dateDebutContrat) {
        this.date_debut_contrat = dateDebutContrat;
    }

    public Date getDureeContrat() {
        return duree_contrat;
    }

    public void setDureeContrat(Date dureeContrat) {
        this.duree_contrat = dureeContrat;
    }

    public double getMontantCouverture() {
        return montant_couverture;
    }

    public void setMontantCouverture(double montantCouverture) {
        this.montant_couverture = montantCouverture;
    }

    public String getModePaiement() {
        return mode_paiement;
    }

    public void setModePaiement(String modePaiement) {
        this.mode_paiement = modePaiement;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Demande{" +
                "id=" + id +
                ", a=" + a +
                ", nomClient='" + nom_client + '\'' +
                ", dateNaissanceClient=" + date_naissance_client +
                ", adresseClient='" + adresse_client + '\'' +
                ", dateDebutContrat=" + date_debut_contrat +
                ", dureeContrat=" + duree_contrat +
                ", montantCouverture=" + montant_couverture +
                ", modePaiement='" + mode_paiement + '\'' +
                ", etat='" + etat + '\'' +
                '}';
    }
    public void setA(Assurence a) {
        this.a = a;
    }
}

