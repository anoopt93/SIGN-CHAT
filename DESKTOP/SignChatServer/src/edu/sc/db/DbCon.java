/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sc.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author pvr
 */
public class DbCon {

    public Connection connection = null;
    private Statement statement = null;
    private final String className = "com.mysql.jdbc.Driver";
    private final String url = "jdbc:mysql://localhost/";
    private final String user = "root";
    private final String password = "root";

    public DbCon() {
        try {

            Class.forName(className);
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection Created...");
            statement = connection.createStatement();
            System.out.println("Statement Created...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int putData(String sql) {
        int i = 0;
        try {
            System.out.println(sql);
            i = statement.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    public ResultSet getData(String sql) {
        ResultSet resultSet = null;
        try {
            System.out.println(sql);
            resultSet = statement.executeQuery(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static void main(String[] args) {

    }

}
