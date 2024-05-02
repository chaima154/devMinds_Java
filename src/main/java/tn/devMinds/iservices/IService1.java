package tn.devMinds.iservices;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IService1<T> {
    void insertOne(T t) throws SQLException;

    void updateOne(T t) throws SQLException;

    boolean deleteOne(T t);

    ArrayList<T> selectAll() throws SQLException;
}
