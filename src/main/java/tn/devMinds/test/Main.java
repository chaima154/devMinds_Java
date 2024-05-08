package tn.devMinds.test;

import tn.devMinds.entities.TypeTransaction;
import tn.devMinds.iservices.TypeTransactionService;

import java.sql.SQLException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws SQLException {
        //MyConnection mc = new MyConnection();
        TypeTransaction typetransaction = new TypeTransaction();
        TypeTransactionService tt = new TypeTransactionService();
        tt.add(typetransaction);
        System.out.println(tt.getAllData());
    }
}