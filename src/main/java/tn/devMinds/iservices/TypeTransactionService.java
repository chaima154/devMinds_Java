package tn.devMinds.iservices;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.devMinds.entities.TypeTransaction;
import tn.devMinds.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TypeTransactionService implements IService<TypeTransaction> {
    private Connection cnx ;
    public TypeTransactionService() {
        cnx= MyConnection.getInstance().getCnx();
    }

    @Override
    public boolean add(TypeTransaction typeTransaction) {
        String requete = "INSERT INTO type_transaction(libelle) VALUES (?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, typeTransaction.getLibelle());
            int rowsAffected = pst.executeUpdate();
            System.out.println("Le type de transaction a été ajouté");
            return rowsAffected > 0; // Renvoie true si au moins une ligne a été affectée
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false; // En cas d'erreur, renvoie false
        }
    }


    @Override
    public void delete(TypeTransaction typeTransaction) {

    }


    /* @Override
     public boolean delete(TypeTransaction typeTransaction) {

         String req = "UPDATE type_transaction  "

                 + " SET libe='SUPPRIMER' "
                 + "WHERE productID='" + typeTransaction.getId() + "';";
         Statement st;
         int er = 0;
         try {
             st = cnx.createStatement();
             er= st.executeUpdate(req);
             System.out.println("Suppression affectuée");
         } catch (SQLException e) {

             e.printStackTrace();
         }


         return er == -1;
     }*/
    @Override
    public void update(TypeTransaction typeTransactionController, int id) {

    }

    @Override
    public ArrayList<TypeTransaction> getAllData() {


        List<TypeTransaction> data = new ArrayList();
        String requete = "SELECT *FROM type_transaction";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs= st.executeQuery(requete);
            while(rs.next()){
                TypeTransaction typeTransaction =new TypeTransaction();
                typeTransaction.setId(rs.getInt(1));
                typeTransaction.setLibelle(rs.getString("libelle"));

                data.add(typeTransaction);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return (ArrayList<TypeTransaction>) data;
    }

    // Cette méthode retourne tous les noms des types de transaction pour peupler le ComboBox
    public ObservableList<String> getAllTypeTransactionNames() {
        ObservableList<String> types = FXCollections.observableArrayList();
        String query = "SELECT libelle FROM type_transaction";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                String libelle = rs.getString("libelle");
                types.add(libelle);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des types de transactions: " + e.getMessage());
        }
        return types;
    }



   /* public List<TypeTransaction> searchTypeTransaction(String searchTerm) {
        List<TypeTransaction> typeTransactions = new ArrayList<>();
        String req = "SELECT * FROM type_transaction WHERE libelle LIKE ? ";

        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setString(1, "%" + searchTerm + "%");


            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                TypeTransaction typeTransaction = new TypeTransaction();
                typeTransaction.setId(rs.getInt("typetransactionID"));
                typeTransaction.setLibelle(rs.getString("typetransactionlibelle"));


                typeTransactions.add(typeTransaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typeTransactions;
    }*/

}

