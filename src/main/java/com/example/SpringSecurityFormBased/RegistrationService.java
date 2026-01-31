package com.example.SpringSecurityFormBased;

import com.example.SpringSecurityFormBased.users.AppUsersDetailsService;
import com.example.SpringSecurityFormBased.users.Users;
import com.example.SpringSecurityFormBased.users.UsersAndRole;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private final EmailValidator emailValidator;
    private final AppUsersDetailsService appUsersDetailsService;
    public RegistrationService(EmailValidator emailValidator, AppUsersDetailsService appUsersDetailsService) {
        this.emailValidator = emailValidator;
        this.appUsersDetailsService = appUsersDetailsService;
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
        return token;
    }
}