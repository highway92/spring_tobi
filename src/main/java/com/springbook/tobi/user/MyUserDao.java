package com.springbook.tobi.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyUserDao extends UserDao {

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection c = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/tobi", "postgres", "postgres"
        );
        return c;
    }
}
