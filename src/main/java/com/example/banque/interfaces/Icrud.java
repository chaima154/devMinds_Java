package com.example.banque.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Icrud <T> {
    ArrayList<T> getAll() throws SQLException;



    boolean add(T t) throws SQLException;
    boolean delete(T t) throws SQLException;
    boolean delete(int id) throws SQLException;
    boolean update(T t) throws SQLException;}
