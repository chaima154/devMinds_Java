package tn.devMinds.iservices;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IService<T> {
        boolean add(T t) throws SQLException;
        boolean delete(T t);
        boolean update(T t, int id);

        ArrayList<T> getAllData() throws SQLException;

}