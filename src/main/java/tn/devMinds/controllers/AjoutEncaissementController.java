package tn.devMinds.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.devMinds.tools.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AjoutEncaissementController {

    @FXML
    private TextField compteDestinataire;

    @FXML
    private TextField numerocheque;

    @FXML
    private TextField montant;

    private MyConnection cnx;

    public AjoutEncaissementController() throws SQLException {
        cnx = (MyConnection) MyConnection.getConnection();
    }

    @FXML
    void AddTransaction(ActionEvent event) throws SQLException {
        String compteDestinataireRIB = compteDestinataire.getText();
        String numcheque = numerocheque.getText();
        double montantTransaction = Double.parseDouble(montant.getText());

        Connection connection = cnx.getConnection();

        if (!compteExists(compteDestinataireRIB)) {
            displayAlert("Alerte", "Le compte destinataire n'existe pas.", Alert.AlertType.ERROR);
            return;
        }

        if (performTransaction(compteDestinataireRIB, numcheque, montantTransaction)) {
            displayAlert("Alerte", "Transaction effectuée avec succès.", Alert.AlertType.INFORMATION);
            // Close the popup
            ((Stage) compteDestinataire.getScene().getWindow()).close();
        } else {
            displayAlert("Alerte", "Erreur lors de la transaction.", Alert.AlertType.ERROR);
        }
    }

    private boolean compteExists(String rib) {
        String query = "SELECT COUNT(*) FROM compte WHERE rib = ?";
        try (PreparedStatement preparedStatement = cnx.getConnection().prepareStatement(query)) {
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

    private boolean performTransaction(String compteDestinataireRIB, String numcheque, double montantTransaction) {
        String updateCompteDestinataire = "UPDATE compte SET solde = solde + ? WHERE rib = ?";
        String insertTransaction = "INSERT INTO transaction (date, statut, typetransaction_id, compte_id, destinataire_compte_id_id, montant_transaction, numcheque) " +
                "VALUES (?, ?, ?, (SELECT id FROM compte WHERE rib = ?), (SELECT id FROM compte WHERE rib = ?), ?, ?)";
        LocalDate date = LocalDate.now();
        try (PreparedStatement updateCompteDestinataireStatement = cnx.getConnection().prepareStatement(updateCompteDestinataire);
             PreparedStatement insertTransactionStatement = cnx.getConnection().prepareStatement(insertTransaction)) {

            cnx.getConnection().setAutoCommit(false);

            // Add to the receiving account
            updateCompteDestinataireStatement.setDouble(1, montantTransaction);
            updateCompteDestinataireStatement.setString(2, compteDestinataireRIB);
            int updatedCompteDestinataireRows = updateCompteDestinataireStatement.executeUpdate();

            // Get the typetransaction_id for versement
            int typetransaction_id = getTypeTransactionId("Encaissementdecheque");
            if (typetransaction_id == -1) {
                displayAlert("Alerte", "Type de transaction non trouvé.", Alert.AlertType.ERROR);
                cnx.getConnection().rollback();
                return false;
            }

            // Add a new line to the transaction table
            insertTransactionStatement.setString(1, date.toString());
            insertTransactionStatement.setString(2, "En attente");
            insertTransactionStatement.setInt(3, typetransaction_id);
            insertTransactionStatement.setString(4, compteDestinataireRIB);
            insertTransactionStatement.setString(5, compteDestinataireRIB);
            insertTransactionStatement.setDouble(6, montantTransaction);
            insertTransactionStatement.setString(7, numcheque);

            int insertedTransactionRows = insertTransactionStatement.executeUpdate();

            if (updatedCompteDestinataireRows > 0 && insertedTransactionRows > 0) {
                cnx.getConnection().commit();
                return true;
            } else {
                cnx.getConnection().rollback();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (cnx.getConnection() != null) {
                    cnx.getConnection().rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }

    private int getTypeTransactionId(String type) {
        String query = "SELECT id FROM type_transaction WHERE libelle = ?";
        try (PreparedStatement preparedStatement = cnx.getConnection().prepareStatement(query)) {
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

    private void displayAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.show();
    }
}
