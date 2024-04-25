package tn.devMinds.iservices;
import java.sql.SQLException;
import java.util.ArrayList;
public interface IService<T>{

        ArrayList<T> getAll() throws SQLException;
        String add(T t) throws SQLException;
        boolean delete(T t) throws SQLException;
        String delete(int id) throws SQLException;
        String update(T t, int id) throws SQLException;
}

