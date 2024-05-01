package tn.devMinds.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tn.devMinds.tools.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AjoutVirementController {

    @FXML
    private TextField compteEmetteur;

    @FXML
    private TextField compteDestinataire;

    @FXML
    private TextField montant;

    @FXML
    private Label soldeEmetteurLabel;

    private Double Commission ;

    private MyConnection cnx;

    public AjoutVirementController() {
        cnx = MyConnection.getInstance();
    }

    @FXML
    void initialize() {
        // Add listener to compteEmetteur TextField
        compteEmetteur.textProperty().addListener((observable, oldValue, newValue) -> {
            updateSoldeEmetteur(newValue);
        });
    }

    private void updateSoldeEmetteur(String rib) {
        double solde = getSoldeForAccount(rib);
        soldeEmetteurLabel.setText("Solde émetteur: " + solde);
    }

    @FXML
    void AddTransaction(ActionEvent event) {
        String compteEmetteurRIB = compteEmetteur.getText();
        String compteDestinataireRIB = compteDestinataire.getText();
        double montantTransaction = Double.parseDouble(montant.getText());

        Connection connection = cnx.getCnx();

        if (!compteExists(compteEmetteurRIB)) {
            displayAlert("Alerte", "Le compte émetteur n'existe pas.", Alert.AlertType.ERROR);
            return;
        }

        if (!compteExists(compteDestinataireRIB)) {
            displayAlert("Alerte", "Le compte destinataire n'existe pas.", Alert.AlertType.ERROR);
            return;
        }

        if (!checkSoldeSufficient(compteEmetteurRIB, montantTransaction)) {
            displayAlert("Alerte", "Solde insuffisant.", Alert.AlertType.ERROR);
            return;
        }

        if (performTransaction(compteEmetteurRIB, compteDestinataireRIB, montantTransaction)) {
            displayAlert("Alerte", "Transaction effectuée avec succès.", Alert.AlertType.INFORMATION);
            // Close the popup
            ((Stage) compteEmetteur.getScene().getWindow()).close();
        } else {
            displayAlert("Alerte", "Erreur lors de la transaction.", Alert.AlertType.ERROR);
        }
    }

    private boolean compteExists(String rib) {
        String query = "SELECT COUNT(*) FROM compte WHERE rib = ?";
        try (PreparedStatement preparedStatement = cnx.getCnx().prepareStatement(query)) {
            preparedStatement.setString(1, rib);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean checkSoldeSufficient(String rib, double montantTransaction) {
        double solde = getSoldeForAccount(rib);
        return solde >= montantTransaction;
    }

    private double getSoldeForAccount(String rib) {
        String query = "SELECT solde FROM compte WHERE rib = ?";
        try (PreparedStatement preparedStatement = cnx.getCnx().prepareStatement(query)) {
            preparedStatement.setString(1, rib);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("solde");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    private boolean performTransaction(String compteEmetteurRIB, String compteDestinataireRIB, double montantTransaction) {
        String insertTransaction = "INSERT INTO transaction (date, statut, typetransaction_id, compte_id, destinataire_compte_id_id, montant_transaction, commission) " +
                "VALUES (?, ?, ?, (SELECT id FROM compte WHERE rib = ?), (SELECT id FROM compte WHERE rib = ?), ?, ?)";

        LocalDate date = LocalDate.now();
        Commission = getCommissionTransactionType(getTypeTransactionId("virement"));
        try (PreparedStatement insertTransactionStatement = MyConnection.getInstance().getCnx().prepareStatement(insertTransaction)) {

            // Add a new line to the transaction table
            insertTransactionStatement.setString(1, date.toString());
            insertTransactionStatement.setString(2, "En attente");
            insertTransactionStatement.setInt(3, getTypeTransactionId("virement"));
            insertTransactionStatement.setString(4, compteEmetteurRIB);
            insertTransactionStatement.setString(5, compteDestinataireRIB);
            insertTransactionStatement.setDouble(6, montantTransaction);
            insertTransactionStatement.setDouble(7, Commission);
            int insertedTransactionRows = insertTransactionStatement.executeUpdate();

            return insertedTransactionRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private int getTypeTransactionId(String type) {
        String query = "SELECT id FROM type_transaction WHERE libelle = ?";
        try (PreparedStatement preparedStatement = cnx.getCnx().prepareStatement(query)) {
            preparedStatement.setString(1, type);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    private Double getCommissionTransactionType(int id) {
        String query = "SELECT Commission FROM type_transaction WHERE id = ?";
        try (PreparedStatement preparedStatement = cnx.getCnx().prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("Commission");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (double) -1;
    }

    private void displayAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.show();
    }
}
