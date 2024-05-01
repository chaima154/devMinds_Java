package tn.devMinds.iservices;

import java.sql.SQLException;
import java.util.List;

public interface IService <T>{
    void add(T t);

    void update(T t) throws SQLException;

    void delete(int id) throws SQLException;

    List<T> show();

    List<T> readById(int id);
}
