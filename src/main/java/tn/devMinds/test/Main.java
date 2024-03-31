package tn.devMinds.test;

import tn.devMinds.models.Card;
import tn.devMinds.models.Compte;
import tn.devMinds.models.TypeCard;
import tn.devMinds.services.CardCrud;
import tn.devMinds.services.TypeCardCrud;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws SQLException {
//       Compte co=new Compte();
//       co.setId(2);
//       TypeCard tc=new TypeCard();
//       tc.setId(2);
//     Card c=new Card("00000000", LocalDate.of(2002, 4, 24), 404, "2002", "Active",tc, co, 5.500);
//c.setId(105);
//TypeCard type_carte= new TypeCard("one_for_all","easy to handle in every situation", 3.500F,"active");

        CardCrud ps=new CardCrud();
        TypeCardCrud tc=new TypeCardCrud();

//        ps.update(c);
//add :)
        //        ps.add(c);
        // tc.add(type_carte);
        //supprimer
        //ps.delete(25);
        ArrayList<Card> data =ps.getAll();
        int i=0;
        for (Card card : data) {
            i++;
            System.out.println("Card number :"+i);
            System.out.println("Card ID: " + card.getId());
            System.out.println("Card Num: " + card.getNumero());
            System.out.println("Compte ID: " + card.getCompte().getId());
            System.out.println("Compte RIB: " + card.getCompte().getRib());
            System.out.println("Type Carte: " + card.getTypeCarte().getTypeCarte());
            // Print other details as needed
            System.out.println(); // Add an empty line for better readability
        }
        ArrayList<TypeCard> list=tc.getAll();
        int j=0;
        for (TypeCard tcard : list) {
            j++;
            System.out.println(" number :"+j);
            System.out.println("Type Carte ID: " + tcard.getId());
            System.out.println("nom du Type : " + tcard.getTypeCarte());
            System.out.println("Description Type Carte: " + tcard.getDescriptionCarte());
            // Print other details as needed
            System.out.println(); // Add an empty line for better readability
        }
        //System.out.println(ps.getAll());
    }
}