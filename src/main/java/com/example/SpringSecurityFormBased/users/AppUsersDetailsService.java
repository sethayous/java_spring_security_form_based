package com.example.SpringSecurityFormBased.users;

import com.example.SpringSecurityFormBased.emailConfirmation.ConfirmationService;
import com.example.SpringSecurityFormBased.emailConfirmation.ConfirmationToken;
import com.example.SpringSecurityFormBased.security.PasswordConfig;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AppUsersDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationService confirmationService;
    public AppUsersDetailsService(UsersRepository usersRepository, PasswordEncoder passwordEncoder, ConfirmationService confirmationService) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.confirmationService = confirmationService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersRepository.findByEmail(username);
        if(users == null){
            throw new UsernameNotFoundException(username + " is not found");
        }
        return new AppUsersDetails(users);
    }

    public String SignUp(Users users){
        Users users1 = usersRepository.findByEmail(users.getEmail());
        if(users1 != null){
            throw new IllegalStateException(users.getEmail() + " is already exists");
        }
        String encodePassword = passwordEncoder.encode(users.getPassword());
        users.setPassword(encodePassword);
        usersRepository.save(users);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), users
        );
        confirmationService.saveConfirmationToken(confirmationToken);
        return token;
    }

    public int enableUserByEmail(String email){
        return usersRepository.enableUser(email);
    }
}