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

public class AjoutVersementController {
    @FXML
    private TextField compteDestinataire;
    @FXML
    private TextField montant;
    private MyConnection cnx;

    public AjoutVersementController() {
        cnx = MyConnection.getInstance();
    }

    @FXML
    void AddTransaction(ActionEvent event) {
        String compteDestinataireRIB = compteDestinataire.getText();
        double montantTransaction = Double.parseDouble(montant.getText());

        Connection connection = cnx.getCnx();

        if (!compteExists(compteDestinataireRIB)) {
            displayAlert("Alerte", "Le compte destinataire n'existe pas.", Alert.AlertType.ERROR);
            return;
        }

        if (performTransaction(compteDestinataireRIB, montantTransaction)) {
            displayAlert("Alerte", "Transaction effectuée avec succès.", Alert.AlertType.INFORMATION);
            // Close the popup
            ((Stage) compteDestinataire.getScene().getWindow()).close();
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

    private boolean performTransaction(String compteDestinataireRIB, double montantTransaction) {
        String updateCompteDestinataire = "UPDATE compte SET solde = solde + ? WHERE rib = ?";
        String insertTransaction = "INSERT INTO transaction (date, statut, typetransaction_id, compte_id, destinataire_compte_id_id, montant_transaction) " +
                "VALUES (?, ?, ?, (SELECT id FROM compte WHERE rib = ?), (SELECT id FROM compte WHERE rib = ?), ?)";
        LocalDate date = LocalDate.now();
        try (PreparedStatement updateCompteDestinataireStatement = cnx.getCnx().prepareStatement(updateCompteDestinataire);
             PreparedStatement insertTransactionStatement = cnx.getCnx().prepareStatement(insertTransaction)) {

            cnx.getCnx().setAutoCommit(false);

            // Add to the receiving account
            updateCompteDestinataireStatement.setDouble(1, montantTransaction);
            updateCompteDestinataireStatement.setString(2, compteDestinataireRIB);
            int updatedCompteDestinataireRows = updateCompteDestinataireStatement.executeUpdate();

            // Get the typetransaction_id for versement
            int typetransaction_id = getTypeTransactionId("versement");
            if (typetransaction_id == -1) {
                displayAlert("Alerte", "Type de transaction non trouvé.", Alert.AlertType.ERROR);
                cnx.getCnx().rollback();
                return false;
            }

            // Add a new line to the transaction table
            insertTransactionStatement.setString(1, date.toString());
            insertTransactionStatement.setString(2, "En attente");
            insertTransactionStatement.setInt(3, typetransaction_id);
            insertTransactionStatement.setString(4, compteDestinataireRIB);
            insertTransactionStatement.setString(5, compteDestinataireRIB);
            insertTransactionStatement.setDouble(6, montantTransaction);

            int insertedTransactionRows = insertTransactionStatement.executeUpdate();

            if (updatedCompteDestinataireRows > 0 && insertedTransactionRows > 0) {
                cnx.getCnx().commit();
                return true;
            } else {
                cnx.getCnx().rollback();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (cnx.getCnx() != null) {
                    cnx.getCnx().rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
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

    private void displayAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.show();
    }
}
