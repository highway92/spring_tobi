package com.springbook.tobi.user;

import java.util.List;

public class SimpleMailSender implements MailSender {
    public void send(String message) {
        System.out.println("SimpleMailSender sending " + message);
    }

    public void send(List<String> message) {
        System.out.println("SimpleMailSender sending " + message);
    }
}
