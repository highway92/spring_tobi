package com.springbook.tobi.user;

public class DaoFactory {
    public UserDao userDao() {
        ConnectionMaker connectionMaker = new SimpleConnectionMaker();
        return new UserDao(connectionMaker);
    }
}
