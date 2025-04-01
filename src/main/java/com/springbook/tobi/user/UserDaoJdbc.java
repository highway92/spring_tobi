package com.springbook.tobi.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserDaoJdbc implements UserDao {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<User> userMapper = new RowMapper<User>() {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            user.setLevel(Level.valueOf(rs.getInt("level")));
            user.setLogin(rs.getInt("login"));
            user.setRecommend(rs.getInt("recommend"));
            user.setEmail(rs.getString("email"));
            return user;
        }
    };

    public UserDaoJdbc(){};


    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public void add(User user) {
        this.jdbcTemplate.update("insert into users(id,name,password,level,login,recommend,email) values(?,?,?,?,?,?,?)", user.getId(), user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(),user.getEmail());
    }

    public User get(String id) {
        return this.jdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE id = ?",
                new Object[]{id}, this.userMapper
        );
    }


    public void deleteAll() {
        this.jdbcTemplate.update("delete from users");
    }

    public int getCount() {
        return this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query(
                "SELECT * FROM users ORDER BY id", this.userMapper
        );
    }

    public void update(User user) {
        this.jdbcTemplate.update(
                "update users set name = ?, password = ?, level = ?, login = ?, "+
                        "recommend = ?, email = ? where id = ?", user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(),user.getEmail(), user.getId()
        );
    }

}
