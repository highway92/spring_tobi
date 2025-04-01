package com.springbook.tobi.user;


import java.util.List;

public interface MailSender {
    void send(String message);
    void send(List<String> messages);
}
