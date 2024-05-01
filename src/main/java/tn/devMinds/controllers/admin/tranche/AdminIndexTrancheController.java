package tn.devMinds.controllers.admin.tranche;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import tn.devMinds.controllers.admin.credit.AdminIndexCreditController;
import tn.devMinds.models.Credit;
import tn.devMinds.models.Tranche;
import tn.devMinds.sercices.TrancheCrud;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AdminIndexTrancheController implements Initializable {
    @FXML
    public Text titleTranche;
    @FXML
    public TextField trancheSearchBar;
    @FXML
    public TextField montanPayer;
    @FXML
    public DatePicker dateEchenace;
    @FXML
    public ChoiceBox <String> statutPaiment;
    @FXML
    public Button add_btn;
    @FXML
    public Button update_btn;
    @FXML
    public Button delete_btn;
    @FXML
    public Button clear_btn;
    @FXML
    public TableView <Tranche> trancheTableView;
    @FXML
    public TableColumn <Tranche, String> trancheTableView_Id;
    @FXML
    public TableColumn <Tranche, String> trancheTableView_MontantPayer;
    @FXML
    public TableColumn <Tranche, String> trancheTableView_DateEchenace;
    @FXML
    public TableColumn <Tranche, String> trancheTableView_statutPaiment;
    ObservableList<Tranche> tranches;

    private final TrancheCrud trancheCrud = new TrancheCrud();
    private Credit credit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeChoiceBoxes();
    }

    public void setAdminIndexCreditController(AdminIndexCreditController adminIndexCreditController, Credit credit) {
        this.credit=credit;
    }

    private void initializeChoiceBoxes() {

        ObservableList<String> statutOptions = FXCollections.observableArrayList("Payée", "Non Payée");

        statutPaiment.setItems(statutOptions);
    }

    public void showTranches(Credit credit){
        tranches = FXCollections.observableList(trancheCrud.readById(credit.getId()));
        trancheTableView_Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        trancheTableView_MontantPayer.setCellValueFactory(new PropertyValueFactory<>("montantPaiement"));
        trancheTableView_DateEchenace.setCellValueFactory(new PropertyValueFactory<>("dateEcheance"));
        trancheTableView_statutPaiment.setCellValueFactory(new PropertyValueFactory<>("statutPaiement"));

        trancheTableView.setItems(tranches);

        FilteredList<Tranche> filter = new FilteredList<>(tranches, e -> true);

        trancheSearchBar.textProperty().addListener((Observable, oldValue, newValue) -> {

            filter.setPredicate(predicateTranche -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if (String.valueOf(predicateTranche.getId()).contains(searchKey)) {
                    System.out.println("id True");
                    return true;
                } else if (String.valueOf(predicateTranche.getMontantPaiement()).contains(searchKey)) {
                    System.out.println("getMontantPaiement True");
                    return true;
                } else if (predicateTranche.getDateEcheance().toString().contains(searchKey)) {
                    System.out.println("getDateEcheance True");
                    return true;
                } else if (predicateTranche.getStatutPaiement().toLowerCase().contains(searchKey)) {
                    System.out.println("getStatutPaiement True");
                    return true;
                }else return false;
            });
        });

        SortedList<Tranche> sortList = new SortedList<>(filter);
        trancheTableView.setItems(sortList);

        sortList.comparatorProperty().bind(trancheTableView.comparatorProperty());
        System.out.println(sortList);
        trancheTableView.setItems(sortList);

    }

    public void addTrancheReset() {
        montanPayer.setText("");
        dateEchenace.setValue(null);
        statutPaiment.getSelectionModel().clearSelection();

    }

    public void addTrancheSelect() {
        Tranche tranche = trancheTableView.getSelectionModel().getSelectedItem();
        int num = trancheTableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        montanPayer.setText(String.valueOf(tranche.getMontantPaiement()));
        statutPaiment.setValue(tranche.getStatutPaiement());
        dateEchenace.setValue(tranche.getDateEcheance());
    }

    @FXML
    private void handleAdd() {
        if(handleSave()){
            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message d'information");
            alert.setHeaderText(null);
            alert.setContentText("Ajouté Avec Succès");
            alert.showAndWait();
            showTranches(credit);
            addTrancheReset();
        }
    }

    private boolean handleSave() {
        if (isInputValid()) {
            int CreditId = credit.getId();
            LocalDate DateEcheance = dateEchenace.getValue();
            double MontantPaiement = Double.parseDouble(montanPayer.getText());
            String StatutPaiment = statutPaiment.getValue();

            Tranche tranche = new Tranche(CreditId, DateEcheance, MontantPaiement, StatutPaiment);
            trancheCrud.add(tranche);
            return true;
        }else return false;
    }


    private boolean isInputValid() {
        String errorMessage = "";

        ///////////////////////////ASSURER NON VIDE/////////////////////////////

        //montanPayer vide
        if (montanPayer.getText() == null || montanPayer.getText().isEmpty()) {
            errorMessage += "Montant a payer est vide!\n";
        } else {
            try {
                double value = Double.parseDouble(montanPayer.getText());
                // Check if montant is above a certain number
                if (value > 1000) {
                    errorMessage += "Montant a payer est supérieur à " + 1000 + "!\n";
                }
            } catch (NumberFormatException ex) {
                errorMessage += "Montant invalide!\n";
            }
        }

        //dateEchenace vide
        if (dateEchenace.getValue() == null) {
            errorMessage += "Veuillez sélectionner une date d'échéance!\n";
        }else if (dateEchenace.getValue().isBefore(LocalDate.now())) {
            errorMessage += "Date ne peut pas être dans le passé!\n";
        }

        //statutPaiment vide
        if (statutPaiment.getValue() == null) {
            errorMessage += "Veuillez sélectionner un statut de crédit!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            showAlertDialog(errorMessage);
            return false;
        }
    }

    private void showAlertDialog(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Champs invalides");
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void handleEdit (){
        Tranche tranche = trancheTableView.getSelectionModel().getSelectedItem();
        if (tranche == null) {
            return;
        }
        Alert alert;
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Edition de tranche");
        alert.setContentText("Êtes-vous sûr de vouloir modifier les données cette tranche ?");
        // Display the confirmation dialog
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    handleUpdate(tranche);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    void handleUpdate(Tranche tranche) throws SQLException {
        if (isInputValid()) {
            tranche.setMontantPaiement(Double.parseDouble(montanPayer.getText()));
            tranche.setDateEcheance(dateEchenace.getValue());
            tranche.setStatutPaiement(statutPaiment.getValue());

            trancheCrud.update(tranche);
            showTranches(credit);
            addTrancheReset();
        }
    }

    public void handleDelete() {
        Tranche tranche = trancheTableView.getSelectionModel().getSelectedItem();
        if (tranche == null) {
            return;
        }
        Alert alert;
        if (dateEchenace.getValue() == null
                || statutPaiment.getValue() == null
                || montanPayer.getText().isEmpty()
        ) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message d'erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs vides");
            alert.showAndWait();
        } else {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Suppression de tranche");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer cette tranche ?");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        trancheCrud.delete(tranche.getId());
                        showTranches(credit);
                        addTrancheReset();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }


}
