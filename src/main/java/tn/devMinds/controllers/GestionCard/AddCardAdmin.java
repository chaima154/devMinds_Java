package tn.devMinds.controllers.GestionCard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.devMinds.models.Card;
import tn.devMinds.models.Compte;
import tn.devMinds.models.TypeCard;
import tn.devMinds.services.CardCrud;
import tn.devMinds.tools.MyConnection;

import javax.script.Bindings;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
public class AddCardAdmin implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button btncancel;
    @FXML
    private Button btnsubmit;
    @FXML
    private ComboBox<String> cbcompte;
    @FXML
    private ChoiceBox<String> cbstatutcarte;
    @FXML
    private ComboBox<String> cbtypecarte;
    @FXML
    private DatePicker dpdate;
    @FXML
    private TextField tfcsv;
    @FXML
    private TextField tfmdp;
    @FXML
    private TextField tfnumero;
    @FXML
    private final String[] choix= {"active","inactive"};
    @FXML
    private ArrayList<Integer> tableCompteId = new ArrayList<>();
    @FXML
    private ArrayList<Integer> tableTypeCarteId = new ArrayList<>();
    @FXML
    private int x=0;
    @FXML
    private int y=0;
    @FXML
    private String statuts="";
    @FXML
    public void savecard(ActionEvent event) throws SQLException {
        Compte compte = new Compte(x);
        System.out.println("that"+y);
        TypeCard tc=new TypeCard(y);
        Card c=new Card(tfnumero.getText(),dpdate.getValue(),tfcsv.getText(), tfmdp.getText(),statuts,tc,compte,0.0 );
        CardCrud cc=new CardCrud();
        if ( Objects.equals(cbstatutcarte.getValue(), null)|| Objects.equals(cbtypecarte.getValue(), null) || Objects.equals(cbcompte.getValue(), null)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Remplir tous les données");
            alert.showAndWait();
        }

        else {

            if(cc.containstypeValue(tfnumero.getText()))
            {   Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("numero déja existe");
                alert.showAndWait();}
            else{
        if(cc.add(c))
        {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Carte ajouter");
        alert.showAndWait();
        }
        else {Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Verifier les donneés");
            alert.showAndWait();}
        FXMLLoader loader =new FXMLLoader(getClass().getResource("/banque/sidebarre_Admin.fxml"));
        try{
            Parent root = loader.load();
            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            // Set the scene to the stage
            stage.setScene(scene);
            stage.show();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }}}
    }
    @FXML
    public void comboCompte() {
        String requet = "SELECT id,rib FROM compte";
        try {
            Statement st = MyConnection.getConnection().createStatement();
            ResultSet rs = st.executeQuery(requet);
            ObservableList<String> listDataCompte = FXCollections.observableArrayList();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String rib = rs.getString("rib");
                listDataCompte.add(rib);
                tableCompteId.add(id);
            }
            // Set the items and default value for the ComboBox
            cbcompte.setItems(listDataCompte);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    @FXML
    public void comboTypeCarte() {
        String requet = "SELECT id,type_carte FROM type_carte";
        try {
            Statement st = MyConnection.getConnection().createStatement();
            ResultSet rs = st.executeQuery(requet);
            ObservableList<String> listDataTypeCarte = FXCollections.observableArrayList();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String typecarte= rs.getString("type_carte");
                listDataTypeCarte.add(typecarte );
                tableTypeCarteId.add(id);
            }
            cbtypecarte.setItems(listDataTypeCarte);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
   @FXML
    void getDate1() {
        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusYears(2);
        dpdate.setValue(futureDate);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboCompte();
        cbcompte.setOnAction(this::gettableidcompte);
        comboTypeCarte();
        cbtypecarte.setOnAction(this::gettableidtypecarte);
        //setE ventTypeCarte();
        cbstatutcarte.getItems().addAll(choix);
        cbstatutcarte.setOnAction(this::getselectedelement);
       getDate1();
        CardCrud cc= new CardCrud();
        tfnumero.setText((cc.generateUniqueNumero(8)));
        tfcsv.setText(cc.generateRandomNumberString(3));
        tfmdp.setText(cc.generateRandomNumberString(4));
        }
    private void gettableidcompte(ActionEvent actionEvent) {
        int res=cbcompte.getSelectionModel().getSelectedIndex();
          x=tableCompteId.get(res);
        System.out.println(res);
    }
    private void gettableidtypecarte(ActionEvent actionEvent) {
        int res=cbtypecarte.getSelectionModel().getSelectedIndex();
         y=tableTypeCarteId.get(res);
        System.out.println(res);
    }
    private void getselectedelement(ActionEvent actionEvent)
    {
        statuts=cbstatutcarte.getValue();
        System.out.println(statuts);
    }
    @FXML
    public void retour(ActionEvent event) {
        FXMLLoader loader =new FXMLLoader(getClass().getResource("/banque/sidebarre_Admin.fxml"));
        try{
            Parent root = loader.load();
            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            // Set the scene to the stage
            stage.setScene(scene);
            stage.show();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}