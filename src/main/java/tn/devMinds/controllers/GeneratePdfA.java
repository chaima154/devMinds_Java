package tn.devMinds.controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
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
        doc.add(paragraph);

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
        doc.close();
    }
}
