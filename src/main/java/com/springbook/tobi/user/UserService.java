package com.springbook.tobi.user;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

public class UserService {
    UserDao userDao;
    private PlatformTransactionManager transactionManager;
    private MailSender mailSender;

    public void setMailSender(final MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public static final int MIN_LOGOUT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void upgradeLevels() {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            List<User> users = userDao.getAll();
            for(User user : users) {
                if(canUpgradeLevel(user)) {
                    upgradeLevel(user);
                }
            }
            transactionManager.commit(status);
        } catch(RuntimeException e) {
            transactionManager.rollback(status);
            throw e;
        }

    }

    private boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();
        switch(currentLevel) {
            case BASIC: return (user.getLogin() >= MIN_LOGOUT_FOR_SILVER);
            case SILVER: return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
            case GOLD: return false;
            default: throw new IllegalArgumentException("Unsupported level: " + currentLevel);
        }
    }

    protected void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
        sendUpgradeEmail(user);
    }

    private void sendUpgradeEmail(User user) {
        this.mailSender.send("사용자의 등급이 업그레이드 되었습니다. from " + user.getLevel() + " to " + user.getLevel().nextLevel());
    }

    public void add(User user) {
        if(user.getLevel() == null) user.setLevel(Level.BASIC);
        userDao.add(user);
    }

    static class TestUserService extends UserService {
        private String id;

        static class TestUserServiceException extends RuntimeException {}

        protected TestUserService(String id) {
            this.id = id;
        }

        @Override
        protected void upgradeLevel(User user) {
            if(user.getId().equals(id)) throw new TestUserServiceException();
            super.upgradeLevel(user);
        }

    }
}
