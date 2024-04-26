package tn.devMinds.controllers.GestionCard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.devMinds.models.TypeCard;
import tn.devMinds.services.TypeCardCrud;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
public class AddTypeCardAdmin implements Initializable {
    @FXML
    private TableColumn<TypeCard, String> descriptionCol;
    @FXML
    private TableColumn<TypeCard, Double> fraiscol;
    @FXML
    private TableColumn<TypeCard, String> statuscol;
    @FXML
    private TableView<TypeCard> tableview;
    @FXML
    private TableColumn<TypeCard, String> typeCol;
    @FXML
    private TextField tfdescription;
    @FXML
    private TextField tffrais;
    @FXML
    private TextField tftype;
    @FXML
    private final String[] choix= {"active","inactive"};
    @FXML
    private String statuts="";
    @FXML
    private ChoiceBox<String> cbstatus;
    @FXML
    private Button addbtn;
    @FXML
    void Addbtn(ActionEvent event) {
        TypeCard tc=new TypeCard(tftype.getText(),tfdescription.getText(),Float.valueOf(tffrais.getText()),statuts);
    TypeCardCrud tcc=new TypeCardCrud();
        try {
            tcc.add(tc);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    ObservableList<TypeCard> initialData() throws SQLException
    {
        TypeCardCrud ps=new TypeCardCrud();
        return FXCollections.observableArrayList(ps.getAll());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("statusTypeCarte"));
        statuscol.setCellValueFactory(new PropertyValueFactory<>("descriptionCarte"));
       typeCol.setCellValueFactory(new PropertyValueFactory<>("typeCarte"));
       fraiscol.setCellValueFactory(new PropertyValueFactory<>("frais"));
        try {
            tableview.setItems(initialData());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        cbstatus.getItems().addAll(choix);
        cbstatus.setOnAction(this::getselectedelement);
    }


    private void getselectedelement(javafx.event.ActionEvent actionEvent)
    {
        statuts=cbstatus.getValue();
        System.out.println(statuts);
    }
}
