package tn.devMinds.services;
import tn.devMinds.iservices.IService;
import tn.devMinds.models.Compte;
import tn.devMinds.models.TypeCard;
import tn.devMinds.tools.MyConnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.Statement;
public class TypeCardCrud implements  IService<TypeCard>{

    @Override
    public ArrayList<TypeCard> getAll() throws SQLException {
        ArrayList<TypeCard> data =new ArrayList<>();
        String requet="SELECT * FROM type_carte";
        try{
            Statement st= MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs=st.executeQuery(requet);
            while (rs.next())
            {
                TypeCard tc=new TypeCard();
                tc.setId(rs.getInt(1));
                tc.setTypeCarte(rs.getString(2));
                tc.setDescriptionCarte(rs.getString(3));
                tc.setFrais(rs.getFloat(4));
                tc.setStatusTypeCarte(rs.getString(5));
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
                (Statement st = MyConnection.getInstance().getCnx().createStatement()){
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
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete)) {
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
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete)) {
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
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete)){
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
        return null;
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
}
