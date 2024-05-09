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

    private Double commission;

    @FXML
    void initialize() {
        // Add listener to compteEmetteur TextField
        compteEmetteur.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                updateSoldeEmetteur(MyConnection.getConnection(), newValue);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void updateSoldeEmetteur(Connection connection, String rib) {
        double solde = 0.0;
        try {
            solde = getSoldeForAccount(connection, rib);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        soldeEmetteurLabel.setText("Solde émetteur: " + solde);
    }


    @FXML
    void AddTransaction(ActionEvent event) throws SQLException {
        String compteEmetteurRIB = compteEmetteur.getText().trim();
        String compteDestinataireRIB = compteDestinataire.getText().trim();
        String montantString = montant.getText().trim();

        // Input validation
        if (compteEmetteurRIB.isEmpty() || compteDestinataireRIB.isEmpty() || montantString.isEmpty()) {
            displayAlert("Alerte", "Veuillez remplir tous les champs.", Alert.AlertType.ERROR);
            return;
        }

        if (!isNumeric(montantString)) {
            displayAlert("Alerte", "Le montant doit être un nombre.", Alert.AlertType.ERROR);
            return;
        }

        double montantTransaction = Double.parseDouble(montantString);

        if (montantTransaction <= 0) {
            displayAlert("Alerte", "Le montant doit être supérieur à zéro.", Alert.AlertType.ERROR);
            return;
        }

        try (Connection connection = MyConnection.getConnection()) {
            if (!compteExists(connection, compteEmetteurRIB)) {
                displayAlert("Alerte", "Le compte émetteur n'existe pas.", Alert.AlertType.ERROR);
                return;
            }

            if (!compteExists(connection, compteDestinataireRIB)) {
                displayAlert("Alerte", "Le compte destinataire n'existe pas.", Alert.AlertType.ERROR);
                return;
            }

            if (!checkSoldeSufficient(connection, compteEmetteurRIB, montantTransaction)) {
                displayAlert("Alerte", "Solde insuffisant.", Alert.AlertType.ERROR);
                return;
            }

            if (performTransaction(connection, compteEmetteurRIB, compteDestinataireRIB, montantTransaction)) {
                displayAlert("Alerte", "Transaction effectuée avec succès.", Alert.AlertType.INFORMATION);
                // Close the popup
                ((Stage) compteEmetteur.getScene().getWindow()).close();
            } else {
                displayAlert("Alerte", "Erreur lors de la transaction.", Alert.AlertType.ERROR);
            }
        }
    }

    private boolean compteExists(Connection connection, String rib) throws SQLException {
        String query = "SELECT COUNT(*) FROM compte WHERE rib = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, rib);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
        return false;
    }

    private boolean checkSoldeSufficient(Connection connection, String rib, double montantTransaction) throws SQLException {
        double solde = getSoldeForAccount(connection, rib);
        return solde >= montantTransaction;
    }

    private double getSoldeForAccount(Connection connection, String rib) throws SQLException {
        String query = "SELECT solde FROM compte WHERE rib = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, rib);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("solde");
            }
        }
        return 0.0;
    }

    private boolean performTransaction(Connection connection, String compteEmetteurRIB, String compteDestinataireRIB, double montantTransaction) throws SQLException {
        String insertTransaction = "INSERT INTO transaction (date, statut, typetransaction_id, compte_id, destinataire_compte_id_id, montant_transaction, commission) " +
                "VALUES (?, ?, ?, (SELECT id FROM compte WHERE rib = ?), (SELECT id FROM compte WHERE rib = ?), ?, ?)";

        LocalDate date = LocalDate.now();
        commission = getCommissionTransactionType(connection, getTypeTransactionId(connection, "virement"));
        try (PreparedStatement insertTransactionStatement = connection.prepareStatement(insertTransaction)) {

            // Add a new line to the transaction table
            insertTransactionStatement.setString(1, date.toString());
            insertTransactionStatement.setString(2, "En attente");
            insertTransactionStatement.setInt(3, getTypeTransactionId(connection, "virement"));
            insertTransactionStatement.setString(4, compteEmetteurRIB);
            insertTransactionStatement.setString(5, compteDestinataireRIB);
            insertTransactionStatement.setDouble(6, montantTransaction);
            insertTransactionStatement.setDouble(7, commission);
            int insertedTransactionRows = insertTransactionStatement.executeUpdate();

            return insertedTransactionRows > 0;

        }
    }

    private int getTypeTransactionId(Connection connection, String type) throws SQLException {
        String query = "SELECT id FROM type_transaction WHERE libelle = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, type);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        }
        return -1;
    }

    private Double getCommissionTransactionType(Connection connection, int id) throws SQLException {
        String query = "SELECT Commission FROM type_transaction WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("Commission");
            }
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

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}
