package tn.devMinds.controllers.assurance;

import com.itextpdf.text.DocumentException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.devMinds.controllers.GeneratePdf;
import tn.devMinds.controllers.GeneratePdfA;
import tn.devMinds.controllers.SideBarre_adminController;
import tn.devMinds.entities.Assurence;
import tn.devMinds.iservices.AssuranceService;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AssuranceListController implements Initializable {
    public SideBarre_adminController backendHome;
    @FXML
    public BorderPane borderPane;

    @FXML
    private TableView<Assurence> table;
    @FXML
    private TableColumn<Assurence, String> nomColumn;
    @FXML
    private TableColumn<Assurence, String> descriptionColumn;
    @FXML
    private TableColumn<Assurence, String> primeColumn;
    @FXML
    private TableColumn<Assurence, String> franchiseColumn;
    @FXML
    private TableColumn<Assurence, Void> actionColumn;

    @FXML
    private Button ajout;
    @FXML
    private SideBarre_adminController sidebarController;

    public AssuranceListController() throws SQLException {
    }

    public void setSidebarController(SideBarre_adminController sidebarController) {
        this.sidebarController = sidebarController;
    }

    @FXML
    private TextField searchTerm;

    private FilteredList<Assurence> filteredData;

    private AssuranceService assuranceService = new AssuranceService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showList(getAllList());
        setupActionColumn();

        // Create a filtered list and bind it to the table
        filteredData = new FilteredList<>(table.getItems(), p -> true);

        // Bind the search term text field to the filtering logic
        searchTerm.textProperty().addListener((observable, oldValue, newValue) -> {
            String filter = newValue.toLowerCase().trim();

            if (filter.isEmpty()) {
                filteredData.setPredicate(null); // Show all data when the filter is empty
            } else {
                // Filter based on the search term
                filteredData.setPredicate(assurance -> {
                    // You can adjust this condition based on which columns you want to filter
                    return assurance.getNom().toLowerCase().contains(filter) ||
                            assurance.getDescription().toLowerCase().contains(filter);
                });
            }
        });

        // Bind the filtered data to the table
        table.setItems(filteredData);
    }


    public ObservableList<Assurence> getAllList() {
        return FXCollections.observableArrayList(assuranceService.getAllData());
    }

    @FXML
    void ajout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/ASSURANCE/AjoutAssurance.fxml"));
        Parent root = loader.load();
        AjoutAssuranceController ajoutAssuranceController = loader.getController(); // Get the controller instance
        ajoutAssuranceController.setAssuranceListController(this); // Set the AssuranceListController
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }



    private void setupActionColumn() {
        actionColumn.setCellFactory(param -> new TableCell<Assurence, Void>() {
            private final Button deleteBtn = new Button("Delete");
            private final Button updateBtn = new Button("Update");
            private final HBox hbox = new HBox(5, updateBtn, deleteBtn);

            {
                deleteBtn.setOnAction(event -> {
                    Assurence assurance = getTableView().getItems().get(getIndex());
                    assuranceService.delete(assurance);
                    showList(getAllList());
                });

                updateBtn.setOnAction(event -> {
                    Assurence assurance = getTableView().getItems().get(getIndex());
                    openUpdateFXML(assurance);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : hbox);
            }
        });
    }

    private void openUpdateFXML(Assurence assurance) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/banque/ASSURANCE/UpdateAssurance.fxml"));
            Parent root = loader.load();
            UpdateAssuranceController controller = loader.getController();
            controller.initializeData(assurance);
            controller.setAssuranceListController(this); // Set the AssuranceListController
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void delete(ActionEvent event) {
        Assurence assurance = table.getSelectionModel().getSelectedItem();
        if (assurance != null) {
            if (assuranceService.delete(assurance)) {
                showList(getAllList());
            } else {
                System.err.println("Deletion failed.");
            }
        } else {
            System.err.println("No item selected for deletion.");
        }
    }

    public void showList(ObservableList<Assurence> observableList) {
        nomColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getNom()));
        descriptionColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDescription()));
        primeColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPrime()).asString());
        franchiseColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getFranchise()).asString());
        table.setItems(observableList);
    }

    @FXML
    public void imprimerPDF(ActionEvent event) {
        try {
            // Generate the PDF
            String filePath = "assurance_list.pdf";

            GeneratePdfA.generateContract(this, filePath);


            // Open the generated PDF file
            File pdfFile = new File(filePath);
            if (pdfFile.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(pdfFile);
                } else {
                    showAlert("Desktop is not supported on this platform.");
                }
            } else {
                showAlert("PDF file not found.");
            }
        } catch (DocumentException | FileNotFoundException e) {
            showAlert("Error generating PDF: " + e.getMessage());
        } catch (IOException e) {
            showAlert("Error opening PDF: " + e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
