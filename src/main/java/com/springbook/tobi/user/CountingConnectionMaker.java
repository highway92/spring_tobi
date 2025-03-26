package com.springbook.tobi.user;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements ConnectionMaker {
    int count = 0;
    private ConnectionMaker connectionMaker;

    public CountingConnectionMaker() {};

    public CountingConnectionMaker(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public Connection makeConnection() throws SQLException, ClassNotFoundException {
        this.count++;
        return connectionMaker.makeConnection();
    }

    public int getCount() {
        return count;
    }

    public void setConnectionMaker(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }
}
