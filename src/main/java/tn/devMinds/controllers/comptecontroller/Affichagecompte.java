package tn.devMinds.controllers.comptecontroller;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import com.sun.jdi.IntegerValue;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import tn.devMinds.controllers.SideBarre_adminController;
import tn.devMinds.iservices.CompteService;
import tn.devMinds.entities.Compte;
import tn.devMinds.tools.MyConnection;
import javafx.scene.chart.PieChart;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

//import static tn.devMinds.models.Compte.isValidSolde;

public class Affichagecompte  {


    @FXML
    private PieChart pieChart;
    @FXML
    private String type="";
    @FXML
    private TableColumn<Compte,Integer> ID_Compte;
    @FXML
    private final String[] choix= {"courant","prive"};
    @FXML
    private Label valid_RIB;

    @FXML
    private Label valid_agence;

    @FXML
    private Label valid_solde;


    @FXML
    private TextField tfagence;

    @FXML
    private TextField tfrib;
    @FXML
    private TextField idfield;

    @FXML
    private TextField searchField;

    @FXML
    private TextField tfsolde;

    @FXML
    private ChoiceBox<String> cbtypecompte;
    @FXML
    private TableColumn<Compte, String> Agence;
    @FXML
    private TableColumn<Compte, String> Rib;

    @FXML
    private TableColumn<Compte, Integer> Solde;

    @FXML
    private TableColumn<Compte, String> TypeCompte;

    @FXML
    private Button addbtnCompte;

    private SideBarre_adminController sidebarController;


    @FXML
    private Button deletebtnCompte;

    @FXML
    private Button updatebtnCompte;

    @FXML
    void goAcceuil(MouseEvent event) {

    }


    @FXML
    void goRoles(MouseEvent event) {

    }



    @FXML
    void goUsers(MouseEvent event) {

    }

    @FXML
    private TableView<Compte> Compte_tableview;

    ObservableList<Compte>initialData() throws SQLException
    {
        CompteService ps=new CompteService();
        return FXCollections.observableArrayList(getcompte());
    }


    public void initialize() {
CompteService cs=new CompteService();

tfrib.setText(cs.generateUniqueNumero(11));
        ID_Compte.setCellValueFactory(new PropertyValueFactory<Compte,Integer>("id"));
        TypeCompte.setCellValueFactory(new PropertyValueFactory<Compte,String>("typecompte"));
        Solde.setCellValueFactory(new PropertyValueFactory<Compte,Integer>("solde"));
        Agence.setCellValueFactory(new PropertyValueFactory<Compte,String>("agence"));
        Rib.setCellValueFactory(new PropertyValueFactory<Compte,String>("rib"));
        cbtypecompte.getItems().addAll(choix);

        try {
            Compte_tableview.setItems(initialData());
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            populatePieChart();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        search();
    }
    private void populatePieChart() throws SQLException {
        CompteService cs = new CompteService();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        ArrayList<Compte> comptes = getcompte(); // Call the read method to get all accounts

        // Count the occurrences of each typecompte
        int courantCount = 0;
        int priveCount = 0;
        for (Compte compte : comptes) {
            if ("courant".equals(compte.getTypecompte())) {
                courantCount++;
            } else if ("prive".equals(compte.getTypecompte())) {
                priveCount++;
            }
        }

        // Add the data to the pie chart
        pieChartData.add(new PieChart.Data("Courant", courantCount));
        pieChartData.add(new PieChart.Data("Prive", priveCount));

        // Bind labels to data
        pieChartData.forEach(data -> {
            data.nameProperty().bind(
                    Bindings.concat(data.getName(), " : ", (int)data.getPieValue())
            );
        });

        // Set the data to the pie chart
        pieChart.setData(pieChartData);
    }


    public ArrayList<Compte> getcompte () throws SQLException {

       ArrayList<Compte> comptes =new ArrayList<>();
       String requet="SELECT * FROM Compte";
       try{
           Statement st= MyConnection.getInstance().getCnx().createStatement();
           ResultSet rs=st.executeQuery(requet);

               while (rs.next()) {
                   Compte c = new Compte();
                   c.setId(rs.getInt("id"));
                   c.setAgence(rs.getString("agence"));
                   c.setTypecompte(rs.getString("typecompte"));
                   c.setSolde(rs.getInt("solde"));
                   c.setRib(rs.getString("rib"));
                   comptes.add(c);
               }

           return comptes;
   } catch (SQLException e) {
           throw new RuntimeException(e);
       }


}
@FXML
   public void addcompte(javafx.event.ActionEvent actionEvent) throws SQLException {
String agence=tfagence.getText();
String typecompte=cbtypecompte.getValue();
    String soldeText = tfsolde.getText();

// Check if agence or solde fields are empty
    if (agence.isEmpty() || soldeText.isEmpty()) {
        showAlertDialog("Agence et/ou Solde ne peut pas être vide!");
        return;
    }
    Integer solde = Integer.valueOf(soldeText);
String rib=tfrib.getText();
Compte c=new Compte(0,solde,typecompte,agence,rib);
CompteService cs = new CompteService();
cs.add(c);

    initialize();
    cbtypecompte.getItems().clear();
    cbtypecompte.getItems().addAll(choix);
    }
    @FXML
    public void updatecompte(javafx.event.ActionEvent actionEvent) throws SQLException {
        int id=Integer.parseInt(idfield.getText());
        String agence=tfagence.getText();
        String typecompte=cbtypecompte.getValue();;
        int solde= Integer.valueOf(tfsolde.getText());
        String rib=tfrib.getText();


        Compte c=new Compte(id,solde,typecompte,agence,rib);
        System.out.println(c);
        CompteService cs = new CompteService();
        cs.update(c);
        initialize();
        cbtypecompte.getItems().clear();
        cbtypecompte.getItems().addAll(choix);
    }



    @FXML
    public void deletecompte(javafx.event.ActionEvent actionEvent) {
        Compte selectedCompte = Compte_tableview.getSelectionModel().getSelectedItem();

        if (selectedCompte != null) {
            CompteService cs = new CompteService();

            try {
                // Attempt to delete the compte
                boolean success = cs.delete(selectedCompte);

                if (success) {
                    System.out.println("Compte deleted successfully");
                    Compte_tableview.refresh();
                } else {
                    System.out.println("Failed to delete compte");
                    // Handle the case where deletion failed
                }
            } catch (SQLException e) {
                // Handle SQLException
                e.printStackTrace(); // or log the exception
            }
        } else {
            System.out.println("No compte selected for deletion");
            // Show a message to the user to select a compte for deletion
        }
        initialize();
        cbtypecompte.getItems().clear();
        cbtypecompte.getItems().addAll(choix);
    }

    @FXML
    public void clear(ActionEvent actionEvent) {
    }
    @FXML
    void getData(MouseEvent event) {
      Compte compte = Compte_tableview.getSelectionModel().getSelectedItem();
        System.out.println(compte.getId());
        idfield.setText(String.valueOf(compte.getId()));
        cbtypecompte.setValue(compte.getTypecompte());
        tfsolde.setText(String.valueOf(compte.getSolde()));
        tfagence.setText(compte.getAgence());
        tfrib.setText(compte.getRib());
        addbtnCompte.setDisable(true);
    }

    private void getselectedelement(ActionEvent actionEvent)
    {
        type=cbtypecompte.getValue();
        System.out.println(type);
    }


    private void search() {
        ObservableList<Compte> data = Compte_tableview.getItems();
        FilteredList<Compte> filteredData = new FilteredList<>(data, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(compte -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(compte.getTypecompte()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches typecompte
                } else if (compte.getAgence().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches agence
                } else if (String.valueOf(compte.getRib()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches rib
                }
                // Add other fields to search if needed...

                return false; // Does not match any filter
            });
        });

        // Wrap the filtered list in a SortedList
        SortedList<Compte> sortedData = new SortedList<>(filteredData);

        // Bind the SortedList comparator to the TableView comparator
        sortedData.comparatorProperty().bind((ObservableValue<? extends Comparator<? super Compte>>) Compte_tableview.comparatorProperty());

        // Add sorted (and filtered) data to the table
        Compte_tableview.setItems(sortedData);
    }
    private void showAlertDialog(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Champs invalides");
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setSidebarController(SideBarre_adminController sideBarreAdminController) {
        this.sidebarController = sidebarController;
    }

}
