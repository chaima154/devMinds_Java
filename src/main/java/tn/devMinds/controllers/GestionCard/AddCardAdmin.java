package tn.devMinds.controllers.GestionCard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import tn.devMinds.services.CardCrud;
import tn.devMinds.tools.MyConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
public class AddCardAdmin implements Initializable {
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
    private String[] choix= {"active","inactive"};
    @FXML
    public void savecard(ActionEvent event) {
    }
    @FXML
    public void comboCompte() {
        String requet = "SELECT rib FROM compte";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requet);
            ObservableList<String> listDataCompte = FXCollections.observableArrayList();
            while (rs.next()) {
                String item = rs.getString("rib");
                System.out.println(item);
                listDataCompte.add(item);
            }
            cbcompte.setItems(listDataCompte);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    @FXML
    public void comboTypeCarte() {
        String requet = "SELECT type_carte FROM type_carte";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requet);
            ObservableList<String> listDataTypeCarte = FXCollections.observableArrayList();
            while (rs.next()) {
                String item = rs.getString("type_carte");
                //System.out.println(item);
                listDataTypeCarte.add(item);
            }
            cbtypecarte.setItems(listDataTypeCarte);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    @FXML
    void getDate1() {
        // Get today's date
        LocalDate today = LocalDate.now();
// Add 2 years to today's date
        LocalDate futureDate = today.plusYears(2);
// Set the DatePicker value to the future date
        dpdate.setValue(futureDate);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboCompte();
        comboTypeCarte();
        cbstatutcarte.getItems().addAll(choix);
        getDate1();
        CardCrud cc= new CardCrud();
        tfnumero.setText((cc.generateUniqueNumero(8)));
        tfcsv.setText(cc.generateRandomNumberString(3));
        tfmdp.setText(cc.generateRandomNumberString(4));
        }
}