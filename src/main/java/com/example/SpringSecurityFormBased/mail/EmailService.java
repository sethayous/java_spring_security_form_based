package com.example.SpringSecurityFormBased.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;

@Service
public class EmailService implements EmailSender{
    private final JavaMailSender mailSender;
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @Async
    public void sendMail(String to, String emailContent, String siteUrl) {
        try{
            String subjectName = "RAC IT TEAM";
            siteUrl = "<h3><a href=\"" + siteUrl + "\">Activate Now</a> </h3>";
            emailContent = "<p>Dear " + emailContent + ",</p>" + "<p>Thank you for registering. Please click on the below link to activate your account.</p>" + siteUrl + "<p> This link will expire 1 hour</p>";
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(to);
            helper.setSubject("Please verify your registration");
            helper.setFrom("yous.setha007@gmail.com", subjectName);
            helper.setText(emailContent, true);
            mailSender.send(mimeMessage);
        }catch (MessagingException me){
            System.out.println((me.getMessage()));
        }catch (UnsupportedEncodingException ex){
            java.util.logging.Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}