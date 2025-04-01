package com.springbook.tobi.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import static com.springbook.tobi.user.UserService.MIN_LOGOUT_FOR_SILVER;
import static com.springbook.tobi.user.UserService.MIN_RECOMMEND_FOR_GOLD;
import static com.springbook.tobi.user.UserService.TestUserService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/applicationContext.xml")
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private MailSender mailSender;

    List<User> users;

    @Test
    public void bean() {
        assertNotNull(userService);
    }

    @BeforeEach
    public void setUp() {
        users = Arrays.asList(
                new User("bumjin", "박범진", "p1", Level.BASIC,MIN_LOGOUT_FOR_SILVER - 1,0,"qwer@google.com"),
                new User("joytouch", "강명성", "p2", Level.BASIC,MIN_LOGOUT_FOR_SILVER,0,"qwer2@google.com"),
                new User("erwins", "신승한", "p3", Level.SILVER,60,MIN_RECOMMEND_FOR_GOLD - 1,"qwer3@google.com"),
                new User("madnite1", "이상호", "p4", Level.SILVER,60,MIN_RECOMMEND_FOR_GOLD,"qwer4@google.com"),
                new User("green", "오민규", "p5", Level.GOLD,100,Integer.MAX_VALUE,"qwer5@google.com")
        );
    }

    @Test
    public void upgradeLevels() {
        userDao.deleteAll();
        for(User user : users) {
            userDao.add(user);
        }
        userService.upgradeLevels();
        checkLevel(users.get(0), false);
        checkLevel(users.get(1), true);
        checkLevel(users.get(2), false);
        checkLevel(users.get(3), true);
        checkLevel(users.get(4), false);

    }

    private void checkLevel(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());
        if(upgraded) {
            assertEquals(userUpdate.getLevel(), user.getLevel().nextLevel());
        } else {
            assertEquals(userUpdate.getLevel(), user.getLevel());
        }
    }

    @Test
    public void add() {
        userDao.deleteAll();

        User userWithLevel = users.get(4);
        User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        assertEquals(userWithLevelRead.getLevel(), userWithLevel.getLevel());
        assertEquals(userWithoutLevelRead.getLevel(), Level.BASIC);
    }

    @Test
    public void upgradeAllOrNothing() {
        UserService testUserService = new TestUserService(users.get(3).getId());
        testUserService.setUserDao(this.userDao);
        testUserService.setTransactionManager(this.transactionManager);
        testUserService.setMailSender(this.mailSender);
        userDao.deleteAll();
        for(User user : users) userDao.add(user);

        try {
            testUserService.upgradeLevels();
        } catch (TestUserService.TestUserServiceException e) {}
        checkLevel(users.get(1), false);
    }
}
