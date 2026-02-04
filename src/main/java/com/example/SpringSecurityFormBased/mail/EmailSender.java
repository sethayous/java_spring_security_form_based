package com.example.SpringSecurityFormBased.mail;

public interface EmailSender {
    void sendMail(String to, String emailContent, String siteUrl);
}