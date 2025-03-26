package com.springbook.tobi.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {
    @Bean
    public UserDao userDao() {
        return new UserDao(countingConnectionMaker());
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new SimpleConnectionMaker();
    }

    @Bean
    public ConnectionMaker countingConnectionMaker() {
        ConnectionMaker connectionMaker = new SimpleConnectionMaker();
        return new CountingConnectionMaker(connectionMaker);
    }
}
