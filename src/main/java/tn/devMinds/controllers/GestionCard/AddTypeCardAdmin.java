package tn.devMinds.controllers.GestionCard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import tn.devMinds.models.Card;
import tn.devMinds.models.TypeCard;
import tn.devMinds.services.CardCrud;
import tn.devMinds.services.TypeCardCrud;

import java.awt.event.MouseEvent;
import java.lang.reflect.Type;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
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
    private final String[] choix = {"active", "inactive"};
    @FXML
    private String statuts = "";
    @FXML
    private ChoiceBox<String> cbstatus;
    @FXML
    private Button addbtn;
    @FXML
    public void Addbtn(javafx.event.ActionEvent event) {
        TypeCardCrud ps = new TypeCardCrud();
        if (tftype.getText().isEmpty() || tffrais.getText().isEmpty() || tfdescription.getText().isEmpty() || Objects.equals(cbstatus.getValue(), null)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Remplir tous les données");
            alert.showAndWait();
        }

        else{
            if(ps.containstypeValue(tftype.getText()))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("type déja existe");
                alert.showAndWait();
            }
            else {
                if(Double.valueOf(tffrais.getText())<=-1)
                {                Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("frais non valide");
                    alert.showAndWait();}
                else{
        TypeCard tc = new TypeCard(tftype.getText(), tfdescription.getText(), Float.valueOf(tffrais.getText()), statuts);
        TypeCardCrud tcc = new TypeCardCrud();
        try {
            tcc.add(tc);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("le type a ete bien Ajouter ");
            alert.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }}
        }}
    }

    ObservableList<TypeCard> initialData() throws SQLException {
        TypeCardCrud ps = new TypeCardCrud();
        return FXCollections.observableArrayList(ps.getAll());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("descriptionCarte"));
        statuscol.setCellValueFactory(new PropertyValueFactory<>("statusTypeCarte"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("typeCarte"));
        fraiscol.setCellValueFactory(new PropertyValueFactory<>("frais"));
        try {
            tableview.setItems(initialData());
            addButtonToTablemaincard();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        cbstatus.getItems().addAll(choix);
        cbstatus.setOnAction(this::getselectedelement);

    }

    private void getselectedelement(javafx.event.ActionEvent actionEvent) {
        statuts = cbstatus.getValue();
        System.out.println(statuts);
    }


private void reload()
{descriptionCol.setCellValueFactory(new PropertyValueFactory<>("statusTypeCarte"));
    statuscol.setCellValueFactory(new PropertyValueFactory<>("descriptionCarte"));
    typeCol.setCellValueFactory(new PropertyValueFactory<>("typeCarte"));
    fraiscol.setCellValueFactory(new PropertyValueFactory<>("frais"));
    try {
        tableview.setItems(initialData());

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }}
    private void addButtonToTablemaincard() {
        TableColumn<TypeCard, Void> colBtn = new TableColumn<>("Button Column");
        Callback<TableColumn<TypeCard, Void>, TableCell<TypeCard, Void>> cellFactory = new Callback<TableColumn<TypeCard, Void>, TableCell<TypeCard, Void>>() {
            @Override
            public TableCell<TypeCard, Void> call(final TableColumn<TypeCard, Void> param) {
                final TableCell<TypeCard, Void> cell = new TableCell<TypeCard, Void>() {
                    private final Button btnSupprimer = new Button("Supprimer");
                    private final Button btnEdit = new Button("Modifier statuts");

                    {
                        btnSupprimer.setOnAction((ActionEvent event) -> {
                            TypeCard data = getTableView().getItems().get(getIndex());
                            TypeCardCrud tcc = new TypeCardCrud();
                            try {
                                if (tcc.delete(data.getId())) {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setContentText("Type Carte supprimée");
                                    alert.showAndWait();
                                    reload();
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setContentText("Vérifiez les données");
                                    alert.showAndWait();
                                }
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println("selectedData: " + data);
                        });
                        btnEdit.setOnAction((ActionEvent event) -> {
                            TypeCard datam = getTableView().getItems().get(getIndex());
                            TypeCardCrud cc = new TypeCardCrud();
                            filltomodify(datam);
                           try {
                                if (cc.updateStat(datam.getId(),datam.getStatusTypeCarte())) {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setContentText("Carte modifiee");
                                   alert.showAndWait();
                                    //reload();
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setContentText("Vérifiez les données");
                                    alert.showAndWait();
                                }
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println("selectedData: " + datam);

                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox buttons = new HBox(btnSupprimer, btnEdit);
                            buttons.setSpacing(5);
                            setGraphic(buttons);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        tableview.getColumns().add(colBtn);
    }
private void filltomodify(TypeCard tc)
{
    tfdescription.setText(tc.getDescriptionCarte());
    tffrais.setText(String.valueOf(tc.getFrais()));
    tftype.setText(tc.getTypeCarte());
    cbstatus.setValue(tc.getStatusTypeCarte());
}
}
