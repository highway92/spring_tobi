package com.springbook.tobi.user;

import java.util.Objects;

public class User {
    String id;
    String name;
    String password;
    Level level;
    int login;
    int recommend;

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public int getLogin() {
        return login;
    }
    public void setLogin(int login) {
        this.login = login;
    }
    public int getRecommend() {
        return recommend;
    }
    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public User(String id, String name, String password, Level level, int login, int recommend) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.level = level;
        this.login = login;
        this.recommend = recommend;
    }
    public User() {};

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password);
    }

    public void upgradeLevel() {
        Level nextLevel = level.nextLevel();
        if(nextLevel == null) {
            throw new IllegalStateException(this.level + "은 업그레이드가 불가합니다.");
        } else {
            this.level = nextLevel;
        }
    }
}
