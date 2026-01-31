package com.example.SpringSecurityFormBased.users;

import com.example.SpringSecurityFormBased.security.PasswordConfig;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUsersDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;
    private final PasswordConfig passwordConfig;
    public AppUsersDetailsService(UsersRepository usersRepository, PasswordConfig passwordConfig) {
        this.usersRepository = usersRepository;
        this.passwordConfig = passwordConfig;
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
        String encodePassword = passwordConfig.passwordEncoder().encode(users.getPassword());
        users.setPassword(encodePassword);
        usersRepository.save(users);
        return "User is added";
    }
}