package com.springbook.tobi.user;

import java.sql.SQLException;

public class UserTest {
    public UserTest() throws ClassNotFoundException, SQLException {
        UserDao dao = new MyUserDao();

        User user = new User();
        user.setId("whiteship");
        user.setName("백기선");
        user.setPassword("married");

        dao.add(user);

        System.out.println(user.getId() + "등록성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());
        System.out.println(user2.getId() + "조회성공");
    }
}
