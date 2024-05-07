package tn.devMinds.controllers.client.tranche;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.DashedBorder;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import tn.devMinds.models.Credit;
import tn.devMinds.models.Tranche;
import tn.devMinds.sercices.TrancheCrud;


import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class tranchesPdf {
    public static void makeTranchesPdf(Credit credit) throws IOException {

        String path = "C:/Users/nacer/Downloads/Credit.pdf";
        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        Document document = new Document(pdfDocument);
        float threecol = 190f;
        float twocol = 285f;
        float twocol150 = twocol + 150f;
        float twocolumnWidth[] = {twocol150, twocol};
        float threeColumnWidth[] = {threecol, threecol, threecol};
        float fullwidth[] = {threecol * 3};
        Paragraph onesp = new Paragraph("\n");

        Table table = new Table(twocolumnWidth);
        table.addCell(new Cell().add("£FRANK").setFontSize(20f).setBorder(Border.NO_BORDER).setBold());
        Table nestedtabe = new Table(new float[]{twocol / 2, twocol / 2});
        Cell date = getHeaderTextCellValue((LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
        nestedtabe.addCell(getHeaderTextCell("N° de facture:"));
        nestedtabe.addCell(getHeaderTextCellValue("RK356748"));
        nestedtabe.addCell(getHeaderTextCell("Date de facture:"));
        nestedtabe.addCell(date);

        table.addCell(new Cell().add(nestedtabe).setBorder(Border.NO_BORDER));

        Border gb = new SolidBorder(new DeviceRgb(0x25, 0x3F, 0x25), 2f);
        Table divider = new Table(fullwidth);
        divider.setBorder(gb);
        document.add(table);
        document.add(onesp);
        document.add(divider);
        document.add(onesp);

        Table twoColTable = new Table(twocolumnWidth);
        twoColTable.addCell(getCreditPersonalCell("Informations Crédit:"));
        document.add(twoColTable.setMarginBottom(12f));

        Table twoColTable2 = new Table(twocolumnWidth);
        twoColTable2.addCell(getCell10fLeft("Compte Id", true));
        twoColTable2.addCell(getCell10fLeft("Credit Id", true));
        twoColTable2.addCell(getCell10fLeft("#" + credit.getCompteId(), false));
        twoColTable2.addCell(getCell10fLeft("#" + credit.getId(), false));

        document.add(twoColTable2);

        Table twoColTable3 = new Table(twocolumnWidth);
        twoColTable3.addCell(getCell10fLeft("Montant Credit", true));
        twoColTable3.addCell(getCell10fLeft("Date d'obtention", true));
        twoColTable3.addCell(getCell10fLeft(credit.getMontantCredit() + " DT", false));
        twoColTable3.addCell(getCell10fLeft(String.valueOf(credit.getDateObtention()), false));

        document.add(twoColTable3);


        Table twoColTable4 = new Table(twocolumnWidth);
        twoColTable4.addCell(getCell10fLeft("Durée", true));
        twoColTable4.addCell(getCell10fLeft("Taux d'intérêt", true));
        twoColTable4.addCell(getCell10fLeft(credit.getDuree() + " mois", false));
        twoColTable4.addCell(getCell10fLeft(credit.getTauxInteret() + "%", false));

        document.add(twoColTable4);

        float oneColoumnwidth[] = {twocol150};

        Table oneColTable = new Table(oneColoumnwidth);
        oneColTable.addCell(getCell10fLeft("Type de Crédit", true));
        oneColTable.addCell(getCell10fLeft(credit.getTypeCredit(), false));

        document.add(oneColTable.setMarginBottom(10f));

        Table tableDevider2 = new Table(fullwidth);
        Border dgb = new DashedBorder(Color.GRAY, 0.5f);
        document.add(tableDevider2.setBorder(dgb));

        Paragraph tranchesPara = new Paragraph("Tranches");
        document.add(tranchesPara.setBold());

        Table threeColTable1 = new Table(threeColumnWidth);
        threeColTable1.setBackgroundColor(new DeviceRgb(0x13, 0x2A, 0x13));

        threeColTable1.addCell(new Cell().add("Montant à Payer").setBold().setFontColor(Color.WHITE).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT).setMarginLeft(10f));
        threeColTable1.addCell(new Cell().add("Date Echeance").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        threeColTable1.addCell(new Cell().add("Date Echeance").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER).setMarginRight(15f));
        document.add(threeColTable1);

        Table threeColTable2 = new Table(threeColumnWidth);

        List <Tranche> tranches = TrancheCrud.readTranches(credit.getId());

        for(Tranche tranche: tranches){
            threeColTable2.addCell(new Cell().add(String.valueOf(tranche.getMontantPaiement())).setBorder(Border.NO_BORDER)).setTextAlignment(TextAlignment.LEFT).setMarginLeft(10f);
            threeColTable2.addCell(new Cell().add(String.valueOf(tranche.getDateEcheance())).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
            threeColTable2.addCell(new Cell().add(tranche.getStatutPaiement()).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)).setMarginRight(10f);
        }

        document.add(threeColTable2);
        document.add(tableDevider2);
        document.add(new Paragraph("\n"));
        document.add(divider.setBorder(new SolidBorder(Color.GRAY, 1)).setMarginBottom(15f));

        document.close();
        File file = new File(path);

        if (file.exists()) {
            Desktop.getDesktop().open(file);
        } else {
            System.out.println("The file does not exist: " + path);
        }

    }

    static Cell getHeaderTextCell(String textValue) {
        return new Cell().add(textValue).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT);
    }

    static Cell getHeaderTextCellValue(String textValue) {
        return new Cell().add(textValue).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }

    static Cell getCreditPersonalCell(String textValue) {
        return new Cell().add(textValue).setFontSize(12f).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }

    static Cell getCell10fLeft(String textValue, boolean isBold) {
        Cell myCell = new Cell().add(textValue).setFontSize(10f).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
        return isBold ?myCell.setBold() : myCell;
    }

}
