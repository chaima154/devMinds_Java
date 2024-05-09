package tn.devMinds.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.devMinds.tools.MyConnection;

import java.io.IOException;
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

    private String selectedTypeTransaction = "Versement";

    private Connection cnx;

    public AjoutVersementController() {}

    public AjoutVersementController(Connection connection, String selectedTypeTransaction) {
        this.cnx = connection;
        this.selectedTypeTransaction = selectedTypeTransaction;
    }

    @FXML
    void addTransaction(ActionEvent event) {
        String compteDestinataireRIB = compteDestinataire.getText();
        double montantTransaction = Double.parseDouble(montant.getText());

        try {
            cnx = MyConnection.getConnection();

            if (!compteExists(compteDestinataireRIB)) {
                displayAlert("Alerte", "Le compte destinataire n'existe pas.", Alert.AlertType.ERROR);
                return;
            }

            if (performTransaction(compteDestinataireRIB, montantTransaction)) {
                displayAlert("Alerte", "Transaction effectuée avec succès.", Alert.AlertType.INFORMATION);
                openTransactionPage();
                // Close the popup
                ((Stage) compteDestinataire.getScene().getWindow()).close();
            } else {
                displayAlert("Alerte", "Erreur lors de la transaction.", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean compteExists(String rib) throws SQLException {
        String query = "SELECT COUNT(*) FROM compte WHERE rib = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setString(1, rib);
            preparedStatement.execute();
            try (var resultSet = preparedStatement.getResultSet()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
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
    private boolean performTransaction(String compteDestinataireRIB, double montantTransaction) throws SQLException {
        String updateCompteDestinataire = "UPDATE compte SET solde = solde + ? WHERE rib = ?";
        String insertTransaction = "INSERT INTO transaction (date, statut, typetransaction_id, compte_id, destinataire_compte_id_id, montant_transaction, commission) " +
                "VALUES (?, ?, ?, (SELECT id FROM compte WHERE rib = ?), (SELECT id FROM compte WHERE rib = ?), ?, ?)";

        LocalDate date = LocalDate.now();

        double commission = getCommissionTransactionType(cnx,getTypeTransactionId("versement"));

        try (PreparedStatement updateCompteDestinataireStatement = cnx.prepareStatement(updateCompteDestinataire);
             PreparedStatement insertTransactionStatement = cnx.prepareStatement(insertTransaction)) {

            cnx.setAutoCommit(false);

            // Add to the receiving account
            updateCompteDestinataireStatement.setDouble(1, montantTransaction);
            updateCompteDestinataireStatement.setString(2, compteDestinataireRIB);
            int updatedCompteDestinataireRows = updateCompteDestinataireStatement.executeUpdate();

            // Get the typetransaction_id for versement
            int typetransaction_id = getTypeTransactionId("versement");
            if (typetransaction_id == -1) {
                displayAlert("Alerte", "Type de transaction non trouvé.", Alert.AlertType.ERROR);
                cnx.rollback();
                return false;
            }

            // Add a new line to the transaction table
            insertTransactionStatement.setString(1, date.toString());
            insertTransactionStatement.setString(2, "En attente");
            insertTransactionStatement.setInt(3, typetransaction_id);
            insertTransactionStatement.setString(4, compteDestinataireRIB);
            insertTransactionStatement.setString(5, compteDestinataireRIB);
            insertTransactionStatement.setDouble(6, montantTransaction);
            insertTransactionStatement.setDouble(7, commission);

            int insertedTransactionRows = insertTransactionStatement.executeUpdate();

            if (updatedCompteDestinataireRows > 0 && insertedTransactionRows > 0) {
                cnx.commit();
                return true;
            } else {
                cnx.rollback();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (cnx != null) {
                    cnx.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }


    private int getTypeTransactionId(String type) {
        String query = "SELECT id FROM type_transaction WHERE libelle = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setString(1, type);
            preparedStatement.execute();
            try (var resultSet = preparedStatement.getResultSet()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
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

    private void openTransactionPage() {
        if (selectedTypeTransaction != null) {
            String pagePath = "/banque/Ajout" + selectedTypeTransaction + ".fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(pagePath));
            try {
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
