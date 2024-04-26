package tn.devMinds.iservices;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IService<T> {
    String add(T t) throws SQLException;

    boolean delete(T t);

    String update(T t, int id);

    ArrayList<T> getAllData() throws SQLException;

}