package tn.devMinds.services;
import tn.devMinds.iservices.IService;
import tn.devMinds.iservices.IService2;
import tn.devMinds.models.Compte;
import tn.devMinds.models.TypeCard;
import tn.devMinds.tools.MyConnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.Statement;
public class TypeCardCrud implements IService2<TypeCard> {

    @Override
    public ArrayList<TypeCard> getAll() throws SQLException {
        ArrayList<TypeCard> data =new ArrayList<>();
        String requet="SELECT * FROM type_carte";
        try{
            Statement st= MyConnection.getConnection().createStatement();
            ResultSet rs=st.executeQuery(requet);
            while (rs.next())
            {
                TypeCard tc=new TypeCard();
                tc.setId(rs.getInt("id"));
                System.out.println(rs.getInt("id"));
                tc.setTypeCarte(rs.getString("type_carte"));
                System.out.println(rs.getString("type_carte"));
                tc.setDescriptionCarte(rs.getString("description_carte"));

                tc.setFrais(rs.getFloat("frais"));
                tc.setStatusTypeCarte(rs.getString("status_type_carte"));
                data.add(tc);
            }}
                   catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }
            return data;
    }

    @Override
    public boolean add(TypeCard typeCard) throws SQLException {
        String requete = "INSERT INTO type_carte (type_carte,description_carte,frais,status_type_carte)" +
                "VALUES ('" + typeCard.getTypeCarte()+ "','" + typeCard.getDescriptionCarte() +"','" +typeCard.getFrais()+"','" +typeCard.getStatusTypeCarte()+"')";
        try
                (Statement st = MyConnection.getConnection().createStatement()){
            int rowsAffected = st.executeUpdate(requete);
            if (rowsAffected > 0) {
                System.out.println("Type Carte ajoutée avec succès");
                return true;
            } else {
                System.out.println("Échec de l'ajout de la type du carte");
                return false;
            }
        }
    }

    @Override
    public boolean delete(TypeCard typeCard) throws SQLException {
        String requete = "DELETE FROM type_carte WHERE id=?";
        try (PreparedStatement pst = MyConnection.getConnection().prepareStatement(requete)) {
            pst.setInt(1, typeCard.getId());
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Type Carte with ID " + typeCard.getId() + " deleted successfully");
                return true;
            } else {
                System.out.println("No Type carte found with ID: " + typeCard.getId());
                return false;
            }
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String requete = "DELETE FROM type_carte WHERE id=?";
        try (PreparedStatement pst = MyConnection.getConnection().prepareStatement(requete)) {
            pst.setInt(1, id);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Type Carte with ID " + id + " deleted successfully");
                return true;
            } else {
                System.out.println("No Type carte found with ID: " + id);
                return false;
            }
        }
    }

    @Override
    public boolean update(TypeCard typeCard) throws SQLException {
        String requete="UPDATE `type_carte` SET `type_carte`=?,`description_carte`=?," +
                "`frais`=?,`status_type_carte`=?" +
                " WHERE id=?";
        try (PreparedStatement pst = MyConnection.getConnection().prepareStatement(requete)){
            pst.setString(1, typeCard.getTypeCarte());
            pst.setString(2,typeCard.getDescriptionCarte());
            pst.setFloat(3,typeCard.getFrais());
            pst.setString(4,typeCard.getStatusTypeCarte());
            pst.setInt(5,typeCard.getId());
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Type Carte with ID " + typeCard.getId() + " updated successfully");
                return true;
            } else {
                System.out.println("No Type carte found with ID: " + typeCard.getId());
                return false;
            }
        }
    }

    @Override
    public ArrayList<TypeCard> getAllNormlaCard() throws SQLException {
        ArrayList<TypeCard> data =new ArrayList<>();
        String requet="SELECT * FROM type_carte WHERE type_carte != 'carte prépayée'";
        try{
            Statement st= MyConnection.getConnection().createStatement();
            ResultSet rs=st.executeQuery(requet);
            while (rs.next())
            {
                TypeCard tc=new TypeCard();
                tc.setId(rs.getInt("id"));
                tc.setTypeCarte(rs.getString("type_carte"));
                tc.setStatusTypeCarte(rs.getString("status_type_carte"));

                tc.setFrais(rs.getFloat("frais"));
                tc.setDescriptionCarte(rs.getString("description_carte"));

                data.add(tc);
            }}
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return data;

    }

    @Override
    public ArrayList<TypeCard> getAllPrepaedCard() throws SQLException {
        return null;
    }

    @Override
    public Compte getCompteById(int id) {
        return null;
    }

    @Override
    public TypeCard getTypeCarteById(int id) {
        return null;
    }

    @Override
    public boolean updateStat(int id, String stat) throws SQLException {
        return false;
    }




    public boolean containstypeValue(String value) {
        String query = "SELECT COUNT(*) FROM type_carte WHERE type_carte = ?";
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


}