package tn.devMinds.test;

import tn.devMinds.models.Card;
import tn.devMinds.services.CardCrud;

import java.sql.SQLException;
import java.util.ArrayList;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws SQLException {
        CardCrud ps=new CardCrud();
        //ps.addEntity2(p);
        ArrayList<Card> data =ps.getAll();
        int i=0;
        for (Card card : data) {
            i++;
            System.out.println("Card number :"+i);
            System.out.println("Card ID: " + card.getId());
            System.out.println("Compte ID: " + card.getCompte().getId());
            System.out.println("Type Carte: " + card.getTypeCarte().getTypeCarte());
            // Print other details as needed
            System.out.println(); // Add an empty line for better readability
        }

        //System.out.println(ps.getAll());
    }
}