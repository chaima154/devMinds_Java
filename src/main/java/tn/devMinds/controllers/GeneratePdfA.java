package tn.devMinds.controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import tn.devMinds.controllers.assurance.AssuranceListController;
import tn.devMinds.entities.Assurence;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class GeneratePdfA {

    public static void generateContract(AssuranceListController controller, String filePath) throws DocumentException, FileNotFoundException {
        var doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream(filePath));
        doc.open();

        var bold = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        var paragraph = new Paragraph("Liste des Assurances\n\n", bold);
        paragraph.setAlignment(Element.ALIGN_CENTER); // Center align the paragraph
        doc.add(paragraph);

        // Add logo
        try {
            Image logo = Image.getInstance("C:\\Users\\Chaima\\IdeaProjects\\banque\\src\\main\\resources\\banque\\images\\logo.png");
            logo.setAlignment(Element.ALIGN_CENTER); // Center align the logo
            logo.scaleAbsolute(100, 100); // Set the size of the logo
            doc.add(logo);
        } catch (Exception e) {
            System.err.println("Error adding logo: " + e.getMessage());
        }


        // Create a table to display assurance information
        var table = new PdfPTable(4);
        table.addCell("Nom");
        table.addCell("Description");
        table.addCell("Prime");
        table.addCell("Franchise");

        for (Assurence assurance : controller.getAllList()) {
            table.addCell(assurance.getNom());
            table.addCell(assurance.getDescription());
            table.addCell(String.valueOf(assurance.getPrime()));
            table.addCell(String.valueOf(assurance.getFranchise()));
        }

        doc.add(table);
        // Add signature
        try {
            Image signature = Image.getInstance("C:\\Users\\Chaima\\IdeaProjects\\banque\\src\\main\\resources\\banque\\images\\signature.png");
            signature.scaleAbsolute(200, 50); // Set the size of the signature
            doc.add(signature);
        } catch (Exception e) {
            System.err.println("Error adding signature: " + e.getMessage());
        }
        doc.close();
    }
}
