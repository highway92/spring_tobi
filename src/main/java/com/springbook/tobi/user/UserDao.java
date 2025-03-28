package com.springbook.tobi.user;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.*;
public class UserDao {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    public UserDao(){};

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }


    public void add(User user) throws SQLException {
        this.jdbcTemplate.update("insert into users(id,name,password) values(?,?,?)", user.getId(), user.getName(), user.getPassword());
    }

    public User get(String id) throws SQLException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("select * from users where id=?");
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        User user = null;
        if(rs.next()) {
            user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }
        rs.close();
        ps.close();
        c.close();
        if(user == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return user;
    }


    public void deleteAll() throws SQLException {
//        this.jdbcTemplate.update(
//                new PreparedStatementCreator() {
//                    public PreparedStatement createPreparedStatement(Connection c) throws SQLException {
//                        return c.prepareStatement("delete from users");
//                    }
//                }
//        );
        this.jdbcTemplate.update("delete from users");
    }

    public Integer getCount() throws SQLException {
        Connection c = dataSource.getConnection();
        PreparedStatement ps = c.prepareStatement("select count(*) from users");
        ResultSet rs = ps.executeQuery();
        rs.next();
        Integer result = rs.getInt(1);
        rs.close();
        ps.close();
        c.close();
        return result;
    }

}
