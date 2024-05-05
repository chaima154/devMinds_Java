package tn.devMinds.controllers.client.tranche;

import com.codingerror.model.AddressDetails;
import com.codingerror.model.HeaderDetails;
import com.codingerror.model.Product;
import com.codingerror.model.ProductTableHeader;
import com.codingerror.service.CodingErrorPdfInvoiceCreator;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.color.Color;


import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import tn.devMinds.models.Credit;
import tn.devMinds.models.Tranche;
import tn.devMinds.sercices.TrancheCrud;
import tn.devMinds.views.ClientTrancheCellFactory;

import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientIndexTrancheController implements Initializable{
    @FXML
    public ListView<Tranche> tranche_listview;
    public TextField trancheSearchBar;
    public Button export_btn;

    private final TrancheCrud trancheCrud = new TrancheCrud();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        export_btn.setOnAction(e -> {
            try {
                generatePdf();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

    }

    public void showTranches(Credit credit) {
        tranche_listview.getItems().clear();
        ObservableList<Tranche> tranches = trancheCrud.readById(credit.getId());
        tranche_listview.setCellFactory(param -> new ClientTrancheCellFactory());

        tranche_listview.setItems(tranches);

        FilteredList<Tranche> filter = new FilteredList<>(tranches, e -> true);

        trancheSearchBar.textProperty().addListener((Observable, oldValue, newValue) -> filter.setPredicate(predicateTranche -> {

            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String searchKey = newValue.toLowerCase();

            if (String.valueOf(predicateTranche.getId()).contains(searchKey)) {
                return true;
            } else if (String.valueOf(predicateTranche.getMontantPaiement()).contains(searchKey)) {
                return true;
            } else if (predicateTranche.getDateEcheance().toString().contains(searchKey)) {
                return true;
            } else if (predicateTranche.getStatutPaiement().toLowerCase().contains(searchKey)) {
                return true;
            }else return false;
        }));

        SortedList<Tranche> sortList = new SortedList<>(filter);
        tranche_listview.setItems(sortList);

    }
    @FXML
    void generatePdf()throws FileNotFoundException {
        String path="C:/Users/nacer/Downloads/Credit.pdf";
        LocalDate ld= LocalDate.now();
        String pdfName= ld+".pdf";
        CodingErrorPdfInvoiceCreator cepdf=new CodingErrorPdfInvoiceCreator(pdfName);
        cepdf.createDocument();

        //Create Header start
        HeaderDetails header=new HeaderDetails();
        header.setInvoiceTitle("£FRANK").setInvoiceNo("RK35623").setInvoiceDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).build();
        cepdf.createHeader(header);
        //Header End

        //Create Address start
        AddressDetails addressDetails=new AddressDetails();
        addressDetails
                .setBillingCompany("Coding Error")
                .setBillingName("Bhaskar")
                .setBillingAddress("Bangluru,karnataka,india\n djdj\ndsjdsk")
                .setBillingEmail("codingerror303@gmail.com")
                .setShippingName("Customer Name \n")
                .setShippingAddress("Banglore Name sdss\n swjs\n")
                .build();

        cepdf.createAddress(addressDetails);
        //Address end

        //Product Start
        ProductTableHeader productTableHeader=new ProductTableHeader();
        cepdf.createTableHeader(productTableHeader);
        List<Product> productList=cepdf.getDummyProductList();
        productList=cepdf.modifyProductList(productList);
        cepdf.createProduct(productList);
        //Product End

        //Term and Condition Start
        List<String> TncList=new ArrayList<>();
        TncList.add("1. The Seller shall not be liable to the Buyer directly or indirectly for any loss or damage suffered by the Buyer.");
        TncList.add("2. The Seller warrants the product for one (1) year from the date of shipment");
        String imagePath="src/main/resources/banque/images/logo.png";
        cepdf.createTnc(TncList,false,imagePath);
        // Term and condition end
        System.out.println("pdf generated");
    }

}
