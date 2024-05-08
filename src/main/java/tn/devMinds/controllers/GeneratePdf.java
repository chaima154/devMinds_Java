package tn.devMinds.controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import tn.devMinds.entities.Demande;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GeneratePdf {

    public static void generateContract(Demande demande, String filePath) throws DocumentException, FileNotFoundException {
        var doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream(filePath));
        doc.open();

        var bold = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        var paragraph = new Paragraph("Copy de contrat d'assurance\n\n", bold);

        paragraph.setAlignment(Element.ALIGN_CENTER); // Center align the paragraph
        doc.add(paragraph);

        // Add logo
        try {
            Image logo = Image.getInstance("C:\\Users\\jaoua\\Documents\\GitHub\\devMinds_Java\\src\\main\\resources\\banque\\images\\logo.png");
            logo.setAlignment(Element.ALIGN_CENTER); // Center align the logo
            logo.scaleAbsolute(100, 100); // Set the size of the logo
            doc.add(logo);
        } catch (Exception e) {
            System.err.println("Error adding logo: " + e.getMessage());
        }


        // Create a table to display demande information
        var table = new PdfPTable(2);
        table.addCell("Nom du client:");
        table.addCell(demande.getNomClient());
        table.addCell("Date de naissance du client:");
        table.addCell(new SimpleDateFormat("dd/MM/yyyy").format(demande.getDateNaissanceClient()));
        table.addCell("Adresse du client:");
        table.addCell(demande.getAdresseClient());
        table.addCell("Date de début du contrat:");
        table.addCell(new SimpleDateFormat("dd/MM/yyyy").format(demande.getDateDebutContrat()));
        table.addCell("Durée du contrat:");
        table.addCell(new SimpleDateFormat("dd/MM/yyyy").format(demande.getDureeContrat()));
        table.addCell("Montant de couverture:");
        table.addCell(String.valueOf(demande.getMontantCouverture()));
        table.addCell("Mode de paiement:");
        table.addCell(demande.getModePaiement());

        doc.add(table);
        // Add signature
        try {
            Image signature = Image.getInstance("C:\\Users\\jaoua\\Documents\\GitHub\\devMinds_Java\\src\\main\\resources\\banque\\images\\signature.png");
            signature.scaleAbsolute(200, 50); // Set the size of the signature
            doc.add(signature);
        } catch (Exception e) {
            System.err.println("Error adding signature: " + e.getMessage());
        }
        doc.close();
    }

    public static void main(String[] args) throws DocumentException, FileNotFoundException {
        // Sample demande object
        Demande demande = new Demande();
        demande.setNomClient("John Doe");
        demande.setDateNaissanceClient(new Date());
        demande.setAdresseClient("123 Street, City");
        demande.setDateDebutContrat(new Date());
        demande.setDureeContrat(new Date());
        demande.setMontantCouverture(1000.0);
        demande.setModePaiement("monthly");

        // Generate the contract PDF
        generateContract(demande, "Contract.pdf");
    }
}
