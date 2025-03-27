package com.springbook.tobi.user;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    @Test
    public void addAndGet()  throws SQLException {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);


        UserDao userDao = (UserDao) context.getBean("userDao");
        userDao.deleteAll();
        assertEquals(userDao.getCount(), 0);

        User user = new User();
        user.setId("gyumee");
        user.setName("박성철");
        user.setPassword("springno1");
        userDao.add(user);
        assertEquals(userDao.getCount(), 1);
        User user2 = userDao.get(user.getId());
        assertEquals(user, user2);

    }

}
