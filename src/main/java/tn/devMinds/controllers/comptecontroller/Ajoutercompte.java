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
import tn.devMinds.controllers.ClientMenuController;
import tn.devMinds.controllers.LoginController;
import tn.devMinds.entities.Compte;
import tn.devMinds.iservices.CompteService;

import tn.devMinds.tools.MyConnection;
import javafx.scene.chart.PieChart;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

//import static tn.devMinds.models.Compte.isValidSolde;

public class Ajoutercompte {

    @FXML
    private TextField Crib;

    @FXML
    private TableColumn<Compte, String> rib;
    @FXML
    private TableColumn<Compte, String> Agence1;

    @FXML
    private TextField Cagence;

    @FXML
    private TableView<Compte> Compte_tableview1;

    @FXML
    private TextField Csolde;

    @FXML
    private ChoiceBox<String> Ctypecompte;
    @FXML
    private final String[] choix= {"courant","prive"};

    @FXML
    private TableColumn<Compte, Integer> Solde1;

    @FXML
    private TableColumn<Compte, String> Typecompte1;

    @FXML
    private Button btn1add;

    private Preferences preferences;
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
    void showlist(ActionEvent event) {

    }

    ObservableList<Compte>initialData() throws SQLException
    {
        CompteService ps=new CompteService();
        return FXCollections.observableArrayList(getcompte());
    }

    public void initialize() {
        CompteService cs=new CompteService();

        Crib.setText(cs.generateUniqueNumero(11));
      //ID_Compte.setCellValueFactory(new PropertyValueFactory<Compte,Integer>("id"));
        Typecompte1.setCellValueFactory(new PropertyValueFactory<Compte,String>("typecompte"));
        Solde1.setCellValueFactory(new PropertyValueFactory<Compte,Integer>("solde"));
        Agence1.setCellValueFactory(new PropertyValueFactory<Compte,String>("agence"));
        rib.setCellValueFactory(new PropertyValueFactory<Compte,String>("rib"));
        Ctypecompte.getItems().addAll(choix);

        try {
            Compte_tableview1.setItems(initialData());
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
@FXML
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
    public void addcompte1(javafx.event.ActionEvent actionEvent) throws SQLException {
        String agence=Cagence.getText();
        String typecompte=Ctypecompte.getValue();
        String soldeText = Csolde.getText();
        int id_client;

// Check if agence or solde fields are empty
        if (agence.isEmpty() || soldeText.isEmpty()) {
            showAlertDialog("Agence et/ou Solde ne peut pas être vide!");
            return;
        }
        id_client= getClientIdFromPreferences();
        Integer solde = Integer.valueOf(soldeText);
      String rib=Crib.getText();
        Compte c=new Compte(0,solde,typecompte,agence,rib,id_client);
        CompteService cs = new CompteService();
        cs.add(c);

        initialize();
        Ctypecompte.getItems().clear();
        Ctypecompte.getItems().addAll(choix);
    }


    private int getClientIdFromPreferences() {
        preferences = Preferences.userRoot().node(LoginController.class.getName());
        String savedValue = preferences.get("Id_Client", "0");
        System.out.println(savedValue);
        return Integer.parseInt(savedValue); // Default value "0" if not found
    }
    private void showAlertDialog(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Champs invalides");
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void clientMenuController(ClientMenuController clientMenuController) {
    }
}
