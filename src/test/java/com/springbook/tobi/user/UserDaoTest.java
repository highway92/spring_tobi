package com.springbook.tobi.user;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    @Test
    public void addAndGet()  throws SQLException {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);


        UserDao userDao = (UserDao) context.getBean("userDao");
        userDao.deleteAll();
        assertEquals(userDao.getCount(), 0);

        User user1 = new User("a","a","a");
        userDao.add(user1);
        User user2 = new User("b","b","b");
        userDao.add(user2);
        assertEquals(userDao.getCount(), 2);
        User getUser1 = userDao.get("a");
        assertEquals(getUser1, user1);

        User getUser2 = userDao.get("b");
        assertEquals(getUser2, user2);
    }

    @Test
    public void getCountTest() throws SQLException {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao userDao = (UserDao) context.getBean("userDao");
        User user1 = new User("a","a","a");
        User user2 = new User("b","b","b");
        User user3 = new User("c","c","c");
        userDao.deleteAll();
        assertEquals(userDao.getCount(), 0);
        userDao.add(user1);
        assertEquals(userDao.getCount(), 1);
        userDao.add(user2);
        assertEquals(userDao.getCount(), 2);
        userDao.add(user3);
        assertEquals(userDao.getCount(), 3);
    }

}
