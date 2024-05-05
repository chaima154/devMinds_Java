package tn.devMinds.controllers.admin.credit;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.devMinds.controllers.admin.tranche.AdminIndexTrancheController;
import tn.devMinds.models.Credit;
import tn.devMinds.sercices.CreditCrud;
import tn.devMinds.sercices.TrancheCrud;
import tn.devMinds.tools.MyConnection;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AdminIndexCreditController implements Initializable {
    @FXML
    public AnchorPane main_form;
    @FXML
    public TextField creditSearchBar;
    @FXML
    public TextField compteId;
    @FXML
    public TextField montantCredit;
    @FXML
    public TextField duree;
    @FXML
    public TextField tauxInteret;
    @FXML
    public DatePicker dateObtention;
    @FXML
    public ChoiceBox <String>  typeCredit;
    @FXML
    public ChoiceBox <String>  categorieProfessionelle;
    @FXML
    public ChoiceBox <String>  secteurActivite;
    @FXML
    public TextField salaire;
    @FXML
    public ChoiceBox <String>  typeSecteur;
    @FXML
    public ChoiceBox <String>  statutCredit;
    @FXML
    public ImageView cinImage;
    @FXML
    public Button cin_Btn;
    @FXML
    public Button add_btn;
    @FXML
    public Button update_btn;
    @FXML
    public Button delete_btn;
    @FXML
    public TableView <Credit> creditTableView;
    @FXML
    public TableColumn <Credit, String> creditTableView_Id;
    @FXML
    public TableColumn <Credit, String> creditTableView_CompteId;
    @FXML
    public TableColumn <Credit, String> creditTableView_MontantCredit;
    @FXML
    public TableColumn <Credit, String> creditTableView_Duree;
    @FXML
    public TableColumn <Credit, String> creditTableView_TauxInteret;
    @FXML
    public TableColumn <Credit, String> creditTableView_DateObtention;
    @FXML
    public TableColumn <Credit, String> creditTableView_StatutCredit;
    @FXML
    public TableColumn <Credit, String> creditTableView_TypeCredit;
    public static final String ACCOUNT_SID = "ACc3bc97fbd7d7fdc0e57c7a5e6e6be5f4";
    public static final String AUTH_TOKEN = "283c8bb0a5cce6f84d26a7ab0504ad76";
    private Image image;
    ObservableList<Credit> credits;
    private final CreditCrud creditCrud = new CreditCrud();
    private final TrancheCrud trancheCrud = new TrancheCrud();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showCredits();
        initializeChoiceBoxes();
    }

    private void initializeChoiceBoxes() {

        ObservableList<String> statutOptions = FXCollections.observableArrayList("En Attente", "Approuvé", "Réfusé", "Remboursé");
        ObservableList<String> typeOptions = FXCollections.observableArrayList("Crédit à la consommation ", "Crédit automobile", "Crédit immobilier", "Prêts étudiants", "Prêts commerciaux");
        ObservableList<String> categorieOptions = FXCollections.observableArrayList("Salarié", "Retraité", "Professionnel Libéral");
        ObservableList<String> secteurOptions = FXCollections.observableArrayList("Privé", "Public");
        ObservableList<String> activiteOptions = FXCollections.observableArrayList("Hotels et restaurants", "Transport", "Activités immobilières", "Administration publiques", "Educations", "Santé", "Construction", "Industrie", "Services");

        statutCredit.setItems(statutOptions);
        typeCredit.setItems(typeOptions);
        categorieProfessionelle.setItems(categorieOptions);
        typeSecteur.setItems(secteurOptions);
        secteurActivite.setItems(activiteOptions);
    }

    public void addCreditSelect() {
        Credit credit = creditTableView.getSelectionModel().getSelectedItem();
        int num = creditTableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        compteId.setText(String.valueOf(credit.getCompteId()));
        montantCredit.setText(String.valueOf(credit.getMontantCredit()));
        duree.setText(String.valueOf(credit.getDuree()));
        tauxInteret.setText(String.valueOf(credit.getTauxInteret()));
        dateObtention.setValue(credit.getDateObtention());
        typeCredit.setValue(credit.getTypeCredit());
        statutCredit.setValue(credit.getStatutCredit());
        categorieProfessionelle.setValue(String.valueOf(credit.getCategorieProfessionelle()));
        secteurActivite.setValue(String.valueOf(credit.getSecteurActivite()));
        salaire.setText(String.valueOf(credit.getSalaire()));
        typeSecteur.setValue(String.valueOf(credit.getTypeCredit()));

        getData.path = Path.of(credit.getDocumentcin());

        String uri = "file:" + credit.getDocumentcin();

        image = new Image(uri, 236, 127, false, true);
        cinImage.setImage(image);

    }

    public void addCreditReset() {
        compteId.setText("");
        montantCredit.setText("");
        duree.setText("");
        tauxInteret.setText("");
        dateObtention.setValue(null);
        typeCredit.getSelectionModel().clearSelection();
        statutCredit.getSelectionModel().clearSelection();
        categorieProfessionelle.getSelectionModel().clearSelection();
        secteurActivite.getSelectionModel().clearSelection();
        salaire.setText("");
        typeSecteur.getSelectionModel().clearSelection();
        cinImage.setImage(null);
    }

    public void addClientInsertImage() {

        FileChooser open = new FileChooser();
        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {
            getData.path = file.toPath();

            image = new Image(file.toURI().toString(), 236, 127, false, true);
            cinImage.setImage(image);
        }
    }

    public String addClientUploadImage() {
        Credit credit = creditTableView.getSelectionModel().getSelectedItem();
        if (getData.path != null) {
            String cinName = "Cin_" + credit.getId() + ".png";

            File cinDirectory = new File("C:/Users/nacer/Downloads/Cour/S2/Projet/java/new/devMinds_Java/src/main/resources/banque/images/Cin_Copies");
            if (!cinDirectory.exists()) {
                cinDirectory.mkdirs();
            }

            File cinCopy = new File(cinDirectory, cinName);

            System.out.println("successfully copied cin" + getData.path);

            try {
                Files.copy(getData.path, cinCopy.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Load the copy as an image
                Image image = new Image(cinCopy.toURI().toString(), 236, 127, false, true);

                // Set the image view with the copy
                cinImage.setImage(image);

                System.out.println("successfully copied cin" + cinCopy.getAbsolutePath());
                return (cinCopy.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace(System.out);
            }
        }return null;
    }

    public void showCredits(){
        credits = creditCrud.show();
        creditTableView_Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        creditTableView_CompteId.setCellValueFactory(new PropertyValueFactory<>("compteId"));
        creditTableView_MontantCredit.setCellValueFactory(new PropertyValueFactory<>("montantCredit"));
        creditTableView_Duree.setCellValueFactory(new PropertyValueFactory<>("duree"));
        creditTableView_TauxInteret.setCellValueFactory(new PropertyValueFactory<>("tauxInteret"));
        creditTableView_DateObtention.setCellValueFactory(new PropertyValueFactory<>("dateObtention"));
        creditTableView_StatutCredit.setCellValueFactory(new PropertyValueFactory<>("statutCredit"));
        creditTableView_TypeCredit.setCellValueFactory(new PropertyValueFactory<>("typeCredit"));

        creditTableView.setItems(credits);

        FilteredList<Credit> filter = new FilteredList<>(credits, e -> true);

        creditSearchBar.textProperty().addListener((Observable, oldValue, newValue) -> filter.setPredicate(predicateCredit -> {

            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String searchKey = newValue.toLowerCase();

            if (String.valueOf(predicateCredit.getId()).contains(searchKey)) {
                return true;
            } else if (String.valueOf(predicateCredit.getCompteId()).contains(searchKey)) {
                return true;
            } else if (String.valueOf(predicateCredit.getMontantCredit()).contains(searchKey)) {
                return true;
            } else if (String.valueOf(predicateCredit.getDuree()).contains(searchKey)) {
                return true;
            } else if (String.valueOf(predicateCredit.getTauxInteret()).contains(searchKey)) {
                return true;
            } else if (predicateCredit.getDateObtention().toString().contains(searchKey)) {
                return true;
            } else if (predicateCredit.getStatutCredit().toLowerCase().contains(searchKey)) {
                return true;
            } else if (predicateCredit.getTypeCredit().toLowerCase().contains(searchKey)){
                return true;
            }else return false;
        }));

        SortedList<Credit> sortList = new SortedList<>(filter);
        creditTableView.setItems(sortList);

        sortList.comparatorProperty().bind(creditTableView.comparatorProperty());
        creditTableView.setItems(sortList);

    }

    //////////////////////////////////////////////////ADD_CREDIT///////////////////////////////////////////////
    @FXML
    private void handleAddCredit() {
        if(handleSave()){
            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message d'information");
            alert.setHeaderText(null);
            alert.setContentText("Ajouté Avec Succès");
            alert.showAndWait();
            showCredits();
            addCreditReset();
        }
    }

    private boolean handleSave() {
        if (isInputValid()) {
            int Compteid = Integer.parseInt(compteId.getText());
            double MontantCredit = Double.parseDouble(montantCredit.getText());
            int Duree = Integer.parseInt(duree.getText());
            double TauxInteret = Double.parseDouble(tauxInteret.getText());
            LocalDate DateObtention = dateObtention.getValue();
            double MontantRestant = Double.parseDouble(montantCredit.getText());
            String StatutCredit = statutCredit.getValue();
            String TypeCredit = typeCredit.getValue();
            double Salaire = Double.parseDouble(salaire.getText());
            String CategorieProfessionelle = categorieProfessionelle.getValue();
            String TypeSecteur = typeSecteur.getValue();
            String SecteurActivite = secteurActivite.getValue();
            String uri = addClientUploadImage();
            uri = uri.replace("\\", "\\\\");
            String DocumentCin = uri;
            Credit credit = new Credit(MontantCredit, Duree, TauxInteret, DateObtention, MontantRestant, StatutCredit, TypeCredit, DocumentCin, Salaire, CategorieProfessionelle, TypeSecteur, SecteurActivite, null, Compteid);
            creditCrud.add(credit);
            return true;
        }else return false;
    }


    /////////////////////////////////////////////////////DELETE_CREDIT///////////////////////////////////////////////
    public void handleDelete() {
        Credit credit = creditTableView.getSelectionModel().getSelectedItem();
        if (credit == null) {
            return;
        }
        Alert alert;
        if (compteId.getText().isEmpty()
                || montantCredit.getText().isEmpty()
                || duree.getText().isEmpty()
                || tauxInteret.getText().isEmpty()
                || dateObtention.getValue() == null
                || typeCredit.getSelectionModel().getSelectedItem() == null
                || statutCredit.getSelectionModel().getSelectedItem() == null
                || categorieProfessionelle.getSelectionModel().getSelectedItem() == null
                || secteurActivite.getSelectionModel().getSelectedItem() == null
                || typeCredit.getSelectionModel().getSelectedItem() == null
                || typeSecteur.getSelectionModel().getSelectedItem() == null
        ) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message d'erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs vides");
            alert.showAndWait();
        } else {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Suppression de Crédit");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer ce crédit ?");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        creditCrud.delete(credit.getId());
                        showCredits();
                        addCreditReset();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }

    /////////////////////////////////////////////////////EDIT_CREDIT//////////////////////////////////////////////////////////////

    public void handleEdit (){
        Credit credit = creditTableView.getSelectionModel().getSelectedItem();
        if (credit == null) {
            return;
        }
        Alert alert;
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Edition de Crédit");
        alert.setContentText("Êtes-vous sûr de vouloir modifier les données ce crédit ?");
        // Display the confirmation dialog
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    handleUpdate(credit);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    void handleUpdate(Credit credit) throws SQLException {
        if (isInputValid()) {
            credit.setCompteId(Integer.parseInt(compteId.getText()));
            credit.setMontantCredit(Double.parseDouble(montantCredit.getText()));
            credit.setDuree(Integer.parseInt(duree.getText()));
            credit.setTauxInteret(Double.parseDouble(tauxInteret.getText()));
            credit.setDateObtention(dateObtention.getValue());
            credit.setStatutCredit(statutCredit.getValue());
            credit.setTypeCredit(typeCredit.getValue());
            credit.setSalaire(Double.parseDouble(salaire.getText()));
            credit.setCategorieProfessionelle(categorieProfessionelle.getValue());
            credit.setTypeSecteur(typeSecteur.getValue());
            credit.setSecteurActivite(secteurActivite.getValue());
            String uri = addClientUploadImage();
            uri = uri.replace("\\", "\\\\");
            credit.setDocumentcin(uri);
            creditCrud.update(credit);
            showCredits();
            addCreditReset();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        ///////////////////////////ASSURER NON VIDE//////////////////////////////
        //CompteId vide
        if (compteId.getText() == null || compteId.getText().isEmpty()) {
            errorMessage += "ID de compte est vide!\n";
        }else{
            String requete = "SELECT COUNT(*) FROM compte WHERE id = ?";
            try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete)){
                pst.setString(1, compteId.getText());
                try (ResultSet resultSet = pst.executeQuery()) {
                    resultSet.next();
                    int count = resultSet.getInt(1);
                    if (count == 0) {
                        errorMessage += "ID de compte n'existe pas dans la base de données!\n";
                    }
                }
            } catch (SQLException e) {
                errorMessage += "Erreur lors de la vérification de l'ID de compte dans la base de données!\n";
                e.printStackTrace(System.out); // Handle the exception appropriately in your application
            }

        }

        //montantCredit vide
        if (montantCredit.getText() == null || montantCredit.getText().isEmpty()) {
            errorMessage += "Montant de crédit est vide!\n";
        } else {
            try {
                double value = Double.parseDouble(montantCredit.getText());
                // Check if montant is below a certain number
                if (value < 1000) {
                    errorMessage += "Montant de crédit est inférieur à " + 1000 + "!\n";
                }
                // Check if montant is above a certain number
                if (value > 10000000) {
                    errorMessage += "Montant de crédit est supérieur à " + 10000000 + "!\n";
                }
            } catch (NumberFormatException ex) {
                errorMessage += "Montant invalide!\n";
            }
        }


        //duree vide
        if (duree.getText() == null || duree.getText().isEmpty()) {
            errorMessage += "Durée est vide!\n";
        } else {
            try {
                double value = Integer.parseInt(duree.getText());
                // Check if durée is below a certain number
                if (value < 3) {
                    errorMessage += "Durée de crédit est inférieur à " + 3 + "!\n";
                }
                // Check if durée is above a certain number
                if (value > 300) {
                    errorMessage += "Durée de crédit est supérieur à " + 300 + "!\n";
                }
            } catch (NumberFormatException ex) {
                errorMessage += "Durée invalide!\n";
            }
        }

        //tauxInteret vide
        if (tauxInteret.getText() == null || tauxInteret.getText().isEmpty()) {
            errorMessage += "Taux d'intérêt est vide!\n";
        } else {
            try {
                double value = Double.parseDouble(tauxInteret.getText());
                // Check if Taux d'intérêt is below a certain number
                if (value < 5) {
                    errorMessage += "Taux d'intérêt de crédit est inférieur à " + 5 + "!\n";
                }
                // Check if Taux d'intérêt is above a certain number
                if (value > 15) {
                    errorMessage += "Taux d'intérêt de crédit est supérieur à " + 15 + "!\n";
                }
            } catch (NumberFormatException ex) {
                errorMessage += "Taux d'intérêt invalide!\n";
            }
        }

        //dateObtention vide
        if (dateObtention.getValue() == null) {
            errorMessage += "Veuillez sélectionner une date d'obtention!\n";
        }else if (dateObtention.getValue().isBefore(LocalDate.now())) {
            errorMessage += "Date ne peut pas être dans le passé!\n";
        }

        //statutCredit vide
        if (statutCredit.getValue() == null) {
            errorMessage += "Veuillez sélectionner un statut de crédit!\n";
        }

        //typeCredit vide
        if (typeCredit.getValue() == null) {
            errorMessage += "Veuillez sélectionner un type de crédit!\n";
        }

        //categorieProfessionelle vide
        if (categorieProfessionelle.getValue() == null) {
            errorMessage += "Veuillez sélectionner une catégorie professionnelle!\n";
        }

        //salaire vide
        if (salaire.getText() == null || salaire.getText().isEmpty()) {
            errorMessage += "Salaire est vide!\n";
        } else {
            try {
                double value = Double.parseDouble(salaire.getText());
                // Check if Salaire is below a certain number
                if (value < 700) {
                    errorMessage += "Salaire est inférieur à " + 700 + "!\n";
                }
                // Check if Salaire is above a certain number
                if (value > 100000) {
                    errorMessage += "Salaire est supérieur à " + 100000 + "!\n";
                }
            } catch (NumberFormatException ex) {
                errorMessage += "Salaire invalide!\n";
            }
        }

        //typeSecteur vide
        if (typeSecteur.getValue() == null) {
            errorMessage += "Veuillez sélectionner un type de secteur!\n";
        }

        //secteurActivite vide
        if (secteurActivite.getValue() == null) {
            errorMessage += "Veuillez sélectionner une activité de secteur!\n";
        }

        //documentCin vide
        if (addClientUploadImage() == null){
            errorMessage += "Veuillez choisir une image de CIN!\n";
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

    /////////////////////////////////////////////////////TRANCHES_INTERFACE//////////////////////////////////////////////////////////////
    @FXML
    private void handleTranche (){
        Credit credit = creditTableView.getSelectionModel().getSelectedItem();
        if(credit.getStatutCredit().equals("Approuvé") && trancheCrud.readById(credit.getId()).isEmpty()){
            trancheCrud.create(credit);
            sendSMS(credit);
        }
        if (credit == null) {
            return;
        }
        if (trancheCrud.readById(credit.getId()).isEmpty()) {
            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message d'information");
            alert.setHeaderText(null);
            alert.setContentText("Ce Crédit n'est pas approuvé");
            alert.showAndWait();
        }else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/admin/tranche/tranche.fxml"));
                Parent root = loader.load();
                AdminIndexTrancheController controller = loader.getController();
                controller.setAdminIndexCreditController(this, credit);
                controller.showTranches(credit);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(root));
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace(System.out);
            }
        }

    }

    void sendSMS(Credit credit) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("+21654025328"),
                        new com.twilio.type.PhoneNumber("+15054665064"), "Félicitation !!! Votre Crédit #" + credit.getId() + " de type " + credit.getTypeCredit() + " a été approuvé. Veuillez consulter l'application pour accéder l'emploi des tranches.")
                .create();
        System.out.println(message.getSid());
    }

}
