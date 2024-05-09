package tn.devMinds.iservices;
import tn.devMinds.models.Compte;
import tn.devMinds.models.TypeCard;

import java.sql.SQLException;
import java.util.ArrayList;
public interface IService2<T>{

    ArrayList<T> getAll() throws SQLException;
    boolean add(T t) throws SQLException;
    boolean delete(T t) throws SQLException;
    boolean delete(int id) throws SQLException;
    boolean update(T t) throws SQLException;
    //T getById(int id);
    ArrayList<T> getAllNormlaCard() throws SQLException;
    ArrayList<T> getAllPrepaedCard() throws SQLException;
    Compte getCompteById(int id);
    TypeCard getTypeCarteById(int id);
    boolean updateStat(int id,String stat) throws SQLException;

}
