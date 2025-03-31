package com.springbook.tobi.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/applicationContext.xml")
class UserDaoJdbcTest {

    @Autowired
    private UserDao userDaoJdbc;

    private User user1;
    private User user2;
    private User user3;



    @BeforeEach
    public void setUp() {
        this.user1 = new User("gyumee","박성청","springno1",Level.BASIC,1,0);
        this.user2 = new User("leegw700","이길원","springno2",Level.SILVER,55,10);
        this.user3 = new User("bumjin","박범진","springno3",Level.GOLD, 100, 40);
    }

    @Test
    public void addAndGet()  {
        userDaoJdbc.deleteAll();
        assertEquals(userDaoJdbc.getCount(), 0);
        userDaoJdbc.add(user1);
        userDaoJdbc.add(user2);
        assertEquals(userDaoJdbc.getCount(), 2);
        User getUser1 = userDaoJdbc.get("gyumee");
        assertEquals(getUser1, user1);
        User getUser2 = userDaoJdbc.get("leegw700");
        assertEquals(getUser2, user2);
    }

    @Test
    public void getCountTest() {
        userDaoJdbc.deleteAll();
        assertEquals(userDaoJdbc.getCount(), 0);
        userDaoJdbc.add(user1);
        assertEquals(userDaoJdbc.getCount(), 1);
        userDaoJdbc.add(user2);
        assertEquals(userDaoJdbc.getCount(), 2);
        userDaoJdbc.add(user3);
        assertEquals(userDaoJdbc.getCount(), 3);
    }

    @Test
    public void getUserFailure() {
        userDaoJdbc.deleteAll();
        assertEquals(userDaoJdbc.getCount(), 0);
        assertThrows(EmptyResultDataAccessException.class, () -> {
            userDaoJdbc.get("a");
        });
    }

    @Test
    public void getAll() {
        userDaoJdbc.deleteAll();

        List<User> user0 = userDaoJdbc.getAll();
        assertEquals(user0.size(), 0);

        userDaoJdbc.add(user1);
        List<User> user1 = userDaoJdbc.getAll();
        assertEquals(user1.size(), 1);

        userDaoJdbc.add(user2);
        List<User> user2 = userDaoJdbc.getAll();
        assertEquals(user2.size(), 2);


        userDaoJdbc.add(user3);
        List<User> user3 = userDaoJdbc.getAll();
        assertEquals(user3.size(), 3);

    }

    @Test
    public void update() {
        userDaoJdbc.deleteAll();
        userDaoJdbc.add(user1);
        userDaoJdbc.add(user2);

        user1.setName("오민규");
        user1.setPassword("springno6");
        user1.setLevel(Level.GOLD);
        user1.setLogin(1000);
        user1.setRecommend(999);
        userDaoJdbc.update(user1);

        User user1update = userDaoJdbc.get(user1.getId());
        assertEquals(user1update.getName(), "오민규");
        assertEquals(user1update.getPassword(), "springno6");
        assertEquals(user1update.getLevel(), Level.GOLD);
        assertEquals(user1update.getRecommend(), 999);
        assertEquals(user1update.getLogin(), 1000);
        assertEquals(user2, userDaoJdbc.get(user2.getId()));
    }
}
