package tn.devMinds.test;

import tn.devMinds.models.Credit;
import tn.devMinds.sercices.CreditCrud;
import tn.devMinds.tools.MyConnection;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws SQLException {
        CreditCrud pcd = new CreditCrud();
        Credit c1 =  new Credit(22222, 22, 22, LocalDate.of(2222, 2, 22), 22, "En Attente", "Crédit à la consommation", "azeaze",2222222, "Privé", "Administration publiques",null );
        pcd.addCredit(c1);
        pcd.updateCredit(c1);
        System.out.println(pcd.showCredit());
    }
}