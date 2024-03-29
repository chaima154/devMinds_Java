package tn.devMinds.services;
import tn.devMinds.iservices.IService;
import tn.devMinds.models.Card;
import tn.devMinds.models.Compte;
import tn.devMinds.models.TypeCard;
import tn.devMinds.tools.MyConnection;

import java.sql.SQLException;
import java.util.ArrayList;

import java.sql.ResultSet;
import java.sql.Statement;

public class CardCrud implements  IService<Card>{
    @Override
    public ArrayList<Card> getAll() throws SQLException {
        ArrayList<Card> data =new ArrayList<>();
        String requet="SELECT * FROM carte";
        try{
            Statement st= MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs=st.executeQuery(requet);
            while (rs.next())
            {
                Card carte=new Card();
                carte.setId(rs.getInt(1));
                carte.setCompte(getCompteById(rs.getInt(2)));
                carte.setTypeCarte(getTypeCarteById(rs.getInt(3)));
                carte.setNumero(rs.getString(4));
                carte.setDateExpiration(rs.getDate(5).toLocalDate());
                carte.setCsv(rs.getInt(6));
                carte.setMdp(rs.getString(7));
                carte.setStatutCarte(rs.getString(8));
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
    public boolean add(Card card) throws SQLException {
        String requete = "INSERT INTO carte (compte_id,type_carte_id,numero,date_expiration,csv,mdp,statut_carte,solde)" +
                "VALUES ('" + card.getCompte().getId() + "','" + card.getTypeCarte() +"','" +card.getNumero()+"','" +card.getDateExpiration()+"','"+card.getCsv()+"','"+card.getMdp()+"','"+ card.getStatutCarte()+"','"+card.getSolde()+"')";
        try
                (Statement st = MyConnection.getInstance().getCnx().createStatement()){
           int rowsAffected = st.executeUpdate(requete);
            if (rowsAffected > 0) {
                System.out.println("Carte ajoutée avec succès");
                return true;
            } else {
                System.out.println("Échec de l'ajout de la carte");
                return false;
            }
        }
    }
    @Override
    public boolean delete(Card card) throws SQLException {

        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Card card) throws SQLException {
        return false;
    }


    @Override
    public Compte getCompteById(int id)
    {
        Compte c=new Compte();
        String requet="SELECT * FROM compte where id=id";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs=st.executeQuery(requet);
            while (rs.next())
            {
            c.setId(rs.getInt(1));
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
        String requet="SELECT * FROM type_carte where id=id";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
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
}
