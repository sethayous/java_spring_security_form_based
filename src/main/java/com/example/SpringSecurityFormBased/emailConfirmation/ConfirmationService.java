package com.example.SpringSecurityFormBased.emailConfirmation;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConfirmationService {
    private final ConfirmationRepository confirmationRepository;
    public ConfirmationService(ConfirmationRepository confirmationRepository) {
        this.confirmationRepository = confirmationRepository;
    }

    public void saveConfirmationToken(ConfirmationToken token){
        confirmationRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token){
        return confirmationRepository.findByToken(token);
    }

    public int setConfirmedAt(String token){
        return confirmationRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
}