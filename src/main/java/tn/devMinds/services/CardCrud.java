package tn.devMinds.services;
import tn.devMinds.iservices.IService;
import tn.devMinds.iservices.IService2;
import tn.devMinds.models.Card;
import tn.devMinds.models.Compte;
import tn.devMinds.models.TypeCard;
import tn.devMinds.tools.MyConnection;
import java.util.Random;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import java.sql.ResultSet;
import java.sql.Statement;

public class CardCrud implements IService2<Card> {
    @Override
    public ArrayList<Card> getAll() throws SQLException {
        ArrayList<Card> data =new ArrayList<>();
        String requet="SELECT * FROM carte";
        try{
            Statement st= MyConnection.getConnection().createStatement();
            ResultSet rs=st.executeQuery(requet);
            while (rs.next())
            {
                Card carte=new Card();
                carte.setId(rs.getInt("id"));
                carte.setCompte(getCompteById(rs.getInt("compte_id")));
                carte.setTypeCarte(getTypeCarteById(rs.getInt("type_carte_id")));
                carte.setNumero(rs.getString("numero"));
                carte.setDateExpiration(rs.getDate("date_expiration").toLocalDate());
                carte.setCsv(rs.getString("csv"));
                carte.setMdp(rs.getString("mdp"));
                carte.setStatutCarte(rs.getString("statut_carte"));
                carte.setSolde(rs.getDouble("solde"));
                data.add(carte);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return data;
    }


    @Override
    public boolean add(Card card) {
        String requete = "INSERT INTO carte (compte_id, type_carte_id, numero, date_expiration, csv, mdp, statut_carte, solde)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = MyConnection.getConnection().prepareStatement(requete)) {
            pst.setInt(1, card.getCompte().getId());
            pst.setInt(2, card.getTypeCarte().getId());
            System.out.println(card.getTypeCarte().getId());
            pst.setString(3, card.getNumero());
            pst.setDate(4, java.sql.Date.valueOf(card.getDateExpiration()));
            pst.setString(5, card.getCsv());
            pst.setString(6, card.getMdp());
            pst.setString(7, card.getStatutCarte());
            pst.setDouble(8, card.getSolde());
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Carte ajoutée avec succès");
                return true;
            } else {
                System.out.println("Échec de l'ajout de la carte");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la carte : " + e.getMessage());
            return false;
        }
    }
    @Override
    public boolean delete(Card card) throws SQLException {
        String requete = "DELETE FROM carte WHERE id=?";
        try (PreparedStatement pst = MyConnection.getConnection().prepareStatement(requete)) {
            pst.setInt(1, card.getId());
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Carte with ID " + card.getId() + " deleted successfully");
                return true;
            } else {
                System.out.println("No carte found with ID: " + card.getId());
                return false;
            }
        }
    }
    @Override
    public boolean delete(int id) throws SQLException {
        String requete="DELETE FROM carte WHERE id=?";
        try (PreparedStatement pst = MyConnection.getConnection().prepareStatement(requete)) {
            pst.setInt(1, id);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Carte with ID " + id + " deleted successfully");
                return true;
            } else {
                System.out.println("No carte found with ID: " + id);
                return false;
            }
        }
    }

    @Override
    public boolean update(Card card) throws SQLException {
        String requete="UPDATE `carte` SET `compte_id`=?,`numero`=?," +
                "`date_expiration`=?,`csv`=?,`mdp`=?,`statut_carte`=?,`solde`=?" +
                " WHERE id=?";
        try (PreparedStatement pst = MyConnection.getConnection().prepareStatement(requete)){
            pst.setInt(1, card.getCompte().getId());
            pst.setString(2,card.getNumero());
            pst.setDate(3,java.sql.Date.valueOf(card.getDateExpiration()));
            pst.setString(4,card.getCsv());
            pst.setString(5,card.getMdp());
            pst.setString(6,card.getStatutCarte());
            pst.setDouble(7,card.getSolde());
            pst.setInt(8,card.getId());
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Carte with ID " + card.getId() + " updated successfully");
                return true;
            } else {
                System.out.println("No carte found with ID: " + card.getId());
                return false;
            }
        }
    }


    @Override
    public Compte getCompteById(int id)
    {
        Compte c=new Compte();
        String requet="SELECT * FROM compte where id="+id ;
        try {
            Statement st = MyConnection.getConnection().createStatement();
            ResultSet rs=st.executeQuery(requet);
            while (rs.next())
            {
            c.setId(rs.getInt("id"));
            c.setSolde(rs.getFloat("solde"));
            c.setRib(rs.getString("rib"));
        }}
        catch (SQLException e) {
            System.out.println("+++"+e.getMessage()+"+++");
        }
        return c;
    }


    @Override
    public TypeCard getTypeCarteById(int id) {
        TypeCard tc=new TypeCard();
        String requet="SELECT * FROM type_carte where id="+id;
        try {
            Statement st = MyConnection.getConnection().createStatement();
            ResultSet rs=st.executeQuery(requet);
            while (rs.next())
            {
            tc.setId(rs.getInt(1));
            tc.setTypeCarte(rs.getString(2));
            tc.setDescriptionCarte(rs.getString(3));
            tc.setFrais(rs.getFloat(4));
            tc.setStatusTypeCarte(rs.getString(5));
        }}
        catch (SQLException e) {
            System.out.println("+++"+e.getMessage()+"+++");
        }
        return tc;
    }

    @Override
    public boolean updateStat(int id, String stat) throws SQLException {
        String requete="UPDATE `carte` SET `statut_carte`=?" +
                " WHERE id=?";
        try (PreparedStatement pst = MyConnection.getConnection().prepareStatement(requete)){
            if(stat.equals("active"))
            { pst.setString(1,"inactive");}
            else if(stat.equals("inactive"))
            { pst.setString(1,"active");}
           else if(stat.equals("Lost")){pst.setString(1,"Lost");}

            pst.setInt(2,id);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Statut du Carte with ID " + id + " updated successfully");
                return true;
            } else {
                System.out.println("No carte found with ID: " + id);
                return false;
            }
        }
    }

    @Override
    public ArrayList<Card> getAllNormlaCard() throws SQLException {
        ArrayList<Card> data =new ArrayList<>();
        String requet= "SELECT c.* FROM carte c JOIN type_carte tc ON c.type_carte_id= tc.id WHERE tc.type_carte != 'carte prépayée' AND c.statut_carte != 'Waiting' AND c.statut_carte != 'Accepted' AND c.statut_carte != 'Refused'";
        try{
            Statement st= MyConnection.getConnection().createStatement();
            ResultSet rs=st.executeQuery(requet);
            while (rs.next())
            {
                Card carte=new Card();
                carte.setId(rs.getInt("id"));
                carte.setCompte(getCompteById(rs.getInt("compte_id")));
                carte.setTypeCarte(getTypeCarteById(rs.getInt("type_carte_id")));
                carte.setNumero(rs.getString("numero"));
                carte.setDateExpiration(rs.getDate("date_expiration").toLocalDate());
                carte.setCsv(rs.getString("csv"));
                carte.setMdp(rs.getString("mdp"));
                carte.setStatutCarte(rs.getString("statut_carte"));

                data.add(carte);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return data;
    }
    @Override
    public ArrayList<Card> getAllPrepaedCard() throws SQLException {
        ArrayList<Card> data =new ArrayList<>();
        String requet= "SELECT c.* FROM carte c JOIN type_carte tc ON c.type_carte_id= tc.id WHERE tc.type_carte = 'carte prépayée' AND c.statut_carte != 'Waiting' AND c.statut_carte !='Accepted' AND c.statut_carte !='Refused'";
            try{
            Statement st= MyConnection.getConnection().createStatement();
            ResultSet rs=st.executeQuery(requet);
            while (rs.next())
            {
                Card carte=new Card();
                carte.setId(rs.getInt("id"));
                carte.setCompte(getCompteById(rs.getInt("compte_id")));
                carte.setTypeCarte(getTypeCarteById(rs.getInt("type_carte_id")));
                carte.setNumero(rs.getString("numero"));
                carte.setDateExpiration(rs.getDate("date_expiration").toLocalDate());
                carte.setCsv(rs.getString("csv"));
                carte.setMdp(rs.getString("mdp"));
                carte.setStatutCarte(rs.getString("statut_carte"));

                data.add(carte);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return data;
    }



    public  String generateUniqueNumero(int x) {
        String randomNumero = generateRandomNumberString(x);
        boolean numeroExists = checkIfNumeroExists(randomNumero);
        while (numeroExists) {
            randomNumero = generateRandomNumberString(x);
            numeroExists = checkIfNumeroExists(randomNumero);
        }
        return randomNumero;
    }
    public  String generateRandomNumberString(int length) {
        String characters = "0123456789";
        Random random = new Random();
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            randomString.append(characters.charAt(random.nextInt(characters.length())));
        }
        return randomString.toString();
    }
        public  boolean checkIfNumeroExists(String numero) {
            String request = "SELECT numero FROM carte WHERE numero=?";
            try {
                PreparedStatement preparedStatement = MyConnection.getConnection().prepareStatement(request);
                preparedStatement.setString(1, numero);
                ResultSet rs = preparedStatement.executeQuery();
                return rs.next(); // Returns true if ResultSet contains any rows (numero exists), false otherwise
            } catch (SQLException e) {
                System.out.println("+++" + e.getMessage() + "+++");
                return false; // Return false in case of exception
            }
        }



    public boolean containstypeValue(String value) {
        String query = "SELECT COUNT(*) FROM type_carte WHERE numero = ?";
        try (PreparedStatement statement = MyConnection.getConnection().prepareStatement(query)) {
            statement.setString(1, value);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0; // If count > 0, the value exists in the table
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public ArrayList<Card> getAllNormlaCardByCompteid(int id) throws SQLException {
        ArrayList<Card> data =new ArrayList<>();
        String requet = "SELECT c.* FROM carte c JOIN type_carte tc ON c.type_carte_id = tc.id WHERE tc.type_carte != 'carte prépayée' AND c.statut_carte != 'Waiting' AND c.statut_carte != 'Accepted' AND c.statut_carte != 'Refused' AND c.compte_id = ?";
        try(
            PreparedStatement statement = MyConnection.getConnection().prepareStatement(requet)) {
                statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
                Card carte=new Card();
                carte.setId(rs.getInt("id"));
                carte.setCompte(getCompteById(rs.getInt("compte_id")));
                carte.setTypeCarte(getTypeCarteById(rs.getInt("type_carte_id")));
                carte.setNumero(rs.getString("numero"));
                carte.setDateExpiration(rs.getDate("date_expiration").toLocalDate());
                carte.setCsv(rs.getString("csv"));
                carte.setMdp(rs.getString("mdp"));
                carte.setStatutCarte(rs.getString("statut_carte"));

                data.add(carte);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return data;
    }

    public ArrayList<Card> getAllPrepaedCardById(int id) throws SQLException {
        ArrayList<Card> data = new ArrayList<>();
        String requet = "SELECT c.* FROM carte c JOIN type_carte tc ON c.type_carte_id = tc.id WHERE tc.type_carte = 'carte prépayée' AND c.statut_carte != 'Waiting' AND c.compte_id = ? AND c.statut_carte != 'Accepted' AND c.statut_carte != 'Refused'";
        try (PreparedStatement statement = MyConnection.getConnection().prepareStatement(requet)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Card carte = new Card();
                carte.setId(rs.getInt("id"));
                carte.setCompte(getCompteById(rs.getInt("compte_id")));
                carte.setTypeCarte(getTypeCarteById(rs.getInt("type_carte_id")));
                carte.setNumero(rs.getString("numero"));
                carte.setDateExpiration(rs.getDate("date_expiration").toLocalDate());
                carte.setCsv(rs.getString("csv"));
                carte.setMdp(rs.getString("mdp"));
                carte.setStatutCarte(rs.getString("statut_carte"));
                carte.setSolde(rs.getDouble("solde"));
                data.add(carte);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }



    public boolean updatepassword(int id,String mdp) throws SQLException {
        String requete="UPDATE `carte` SET `mdp`=?" +
                " WHERE id=?";
        try (PreparedStatement pst = MyConnection.getConnection().prepareStatement(requete)){

        pst.setString(1,mdp);
            pst.setInt(2,id);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("mdp du Carte with ID " + id + " updated successfully");
                return true;
            } else {
                System.out.println("No carte found with ID: " + id);
                return false;
            }
        }
    }




    public boolean updateSolde(int id,Double solde) throws SQLException {
        String requete="UPDATE `carte` SET `solde`=`solde`+?" +
                " WHERE id=?";
        try (PreparedStatement pst = MyConnection.getConnection().prepareStatement(requete)){

            pst.setDouble(1,+solde);
            pst.setInt(2,id);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("solde du Carte with ID " + id + " updated successfully");
                return true;
            } else {
                System.out.println("No carte found with ID: " + id);
                return false;
            }
        }
    }



    public boolean updateSoldeCompte(int id,Double solde) throws SQLException {
        String requete="UPDATE `compte` SET `solde`=`solde`-?" +
                " WHERE id=?";
        try (PreparedStatement pst = MyConnection.getConnection().prepareStatement(requete)){

            pst.setDouble(1,+solde);
            pst.setInt(2,id);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("solde du compte with ID " + id + " updated successfully");
                return true;
            } else {
                System.out.println("No compte found with ID: " + id);
                return false;
            }
        }
    }
    public boolean updateSoldeCompteplus(int id,Double solde) throws SQLException {
        String requete="UPDATE `compte` SET `solde`=`solde`+?" +
                " WHERE id=?";
        try (PreparedStatement pst = MyConnection.getConnection().prepareStatement(requete)){

            pst.setDouble(1,+solde);
            pst.setInt(2,id);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("solde du compte with ID " + id + " updated successfully");
                return true;
            } else {
                System.out.println("No compte found with ID: " + id);
                return false;
            }
        }
    }





    public boolean containstypeValueWaiting(int id) {
        String query = "SELECT COUNT(*) FROM carte WHERE statut_carte = ? AND compte_id=?";
        try (PreparedStatement statement = MyConnection.getConnection().prepareStatement(query)) {
            statement.setString(1, "Waiting");
            statement.setInt(2, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0; // If count > 0, the value exists in the table
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }



    public int containsWaitingcardprpaedRefused(int id) {
        int x=0;
        String query = "SELECT COUNT(*) AS card_count FROM carte c JOIN type_carte tc ON c.type_carte_id = tc.id WHERE c.compte_id =? AND tc.type_carte = 'carte prépayée'And c.statut_carte='Refused'";
        try (PreparedStatement statement = MyConnection.getConnection().prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("card_count");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
    public int containsWaitingcardprpaedAccepted(int id) {
        int x=0;
        String query = "SELECT COUNT(*) AS card_count FROM carte c JOIN type_carte tc ON c.type_carte_id = tc.id WHERE c.compte_id =? AND tc.type_carte = 'carte prépayée'And c.statut_carte='Accepted'";
        try (PreparedStatement statement = MyConnection.getConnection().prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
             return resultSet.getInt("card_count");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
    public int containsWaitingcardprpaed(int id) {
        int x=0;
        String query = "SELECT COUNT(*) AS card_count FROM carte c JOIN type_carte tc ON c.type_carte_id = tc.id WHERE c.compte_id =? AND tc.type_carte = 'carte prépayée'And c.statut_carte='Waiting'";
        try (PreparedStatement statement = MyConnection.getConnection().prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
             return resultSet.getInt("card_count");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }


    public int containsWaitingnormalcardAccepted(int id) {
        int x=0;
        String query = "SELECT COUNT(*) AS card_count FROM carte c JOIN type_carte tc ON c.type_carte_id = tc.id WHERE c.compte_id =? AND tc.type_carte != 'carte prépayée'And c.statut_carte='Accepted'";
        try (PreparedStatement statement = MyConnection.getConnection().prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("card_count");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
    public int containsWaitingnormalcardRefused(int id) {
        int x=0;
        String query = "SELECT COUNT(*) AS card_count FROM carte c JOIN type_carte tc ON c.type_carte_id = tc.id WHERE c.compte_id =? AND tc.type_carte != 'carte prépayée'And c.statut_carte='Refused'";
        try (PreparedStatement statement = MyConnection.getConnection().prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("card_count");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }


    public int containstypeCardpreapaed() {
        String query = "SELECT id FROM type_carte WHERE type_carte = ?";
        try (PreparedStatement statement = MyConnection.getConnection().prepareStatement(query)) {
            statement.setString(1, "carte prépayée");
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
    public boolean containstypeValueNormalWaiting(int id) {
        String query = "SELECT COUNT(*) AS card_count FROM carte c JOIN type_carte tc ON c.type_carte_id = tc.id WHERE c.compte_id =? AND tc.type_carte != 'carte prépayée' AND c.statut_carte = ?";
        try (PreparedStatement statement = MyConnection.getConnection().prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setString(2, "Waiting");
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0; // If count > 0, the value exists in the table
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
