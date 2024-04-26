package tn.devMinds.iservices;

import java.sql.SQLException;
import java.util.List;

public interface IService <T>{
    void addCredit(T credit);

    boolean updateCredit(T credit) throws SQLException;

    void deleteCredit(int id) throws SQLException;

    List<T> showCredit();
}
