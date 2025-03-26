package com.springbook.tobi;

import com.springbook.tobi.user.UserTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class TobiApplication {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserTest userTest = new UserTest();
        SpringApplication.run(TobiApplication.class, args);
    }

}
