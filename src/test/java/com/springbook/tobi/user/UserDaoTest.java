package com.springbook.tobi.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/applicationContext.xml")
class UserDaoTest {
    @Autowired
    private ApplicationContext applicationContext;
    private UserDao userDao;
    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    public void setUp() {
        this.userDao = (UserDao) applicationContext.getBean("userDao");
        this.user1 = new User("a","a","a");
        this.user2 = new User("b","b","b");
        this.user3 = new User("c","c","c");
    }

    @Test
    public void addAndGet()  throws SQLException {
        userDao.deleteAll();
        assertEquals(userDao.getCount(), 0);
        userDao.add(user1);
        userDao.add(user2);
        assertEquals(userDao.getCount(), 2);
        User getUser1 = userDao.get("a");
        assertEquals(getUser1, user1);
        User getUser2 = userDao.get("b");
        assertEquals(getUser2, user2);
    }

    @Test
    public void getCountTest() throws SQLException {
        userDao.deleteAll();
        assertEquals(userDao.getCount(), 0);
        userDao.add(user1);
        assertEquals(userDao.getCount(), 1);
        userDao.add(user2);
        assertEquals(userDao.getCount(), 2);
        userDao.add(user3);
        assertEquals(userDao.getCount(), 3);
    }

    @Test
    public void getUserFailure() throws SQLException {
        userDao.deleteAll();
        assertEquals(userDao.getCount(), 0);
        assertThrows(EmptyResultDataAccessException.class, () -> {
            userDao.get("a");
        });
    }
}
