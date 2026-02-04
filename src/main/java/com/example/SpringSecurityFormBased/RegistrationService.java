package com.example.SpringSecurityFormBased;

import com.example.SpringSecurityFormBased.emailConfirmation.ConfirmationService;
import com.example.SpringSecurityFormBased.emailConfirmation.ConfirmationToken;
import com.example.SpringSecurityFormBased.mail.EmailSender;
import com.example.SpringSecurityFormBased.users.AppUsersDetailsService;
import com.example.SpringSecurityFormBased.users.Users;
import com.example.SpringSecurityFormBased.users.UsersAndRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class RegistrationService {
    private final EmailValidator emailValidator;
    private final AppUsersDetailsService appUsersDetailsService;
    private final ConfirmationService confirmationService;
    private final EmailSender emailSender;
    public RegistrationService(EmailValidator emailValidator, AppUsersDetailsService appUsersDetailsService, ConfirmationService confirmationService, EmailSender emailSender) {
        this.emailValidator = emailValidator;
        this.appUsersDetailsService = appUsersDetailsService;
        this.confirmationService = confirmationService;
        this.emailSender = emailSender;
    }

    public String EmailRegister(RegistrationRequest registrationRequest){
        boolean isValidEmail = emailValidator.test(registrationRequest.getEmail());
        if(!isValidEmail){
            throw new IllegalStateException("email not valid");
        }
        String token = appUsersDetailsService.SignUp(
                new Users(
                        registrationRequest.getFirstName(),
                        registrationRequest.getLastName(),
                        registrationRequest.getEmail(),
                        registrationRequest.getPassword(),
                        UsersAndRole.USER)
        );
        String activateLink = "http://localhost:8080/registration/confirm?token=" + token;
        emailSender.sendMail(registrationRequest.getEmail(), registrationRequest.getFirstName() + " " + registrationRequest.getLastName(), activateLink);
        return token;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationService.getToken(token).orElseThrow(()->new IllegalStateException(token + " is not found."));
        if(confirmationToken.getConfirmedAt() != null){
            throw new IllegalStateException("Email is already confirmed.");
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if(expiredAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException(token + " is expired.");
        }
        confirmationService.setConfirmedAt(token);
        appUsersDetailsService.enableUserByEmail(confirmationToken.getUsers().getEmail());
        return "Confirmed";
    }
}