package com.web.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class TentativeService {

    private final RedisTemplate<String, Integer> redisTemplate;
    private final EmailSender emailService;

    @Value("${APP_SECURITY_MAX_ATTEMPTS:3}")
    private int maxAttempts;

    public TentativeService(RedisTemplate<String, Integer> redisTemplate, EmailSender emailService) {
        this.redisTemplate = redisTemplate;
        this.emailService = emailService;
    }

    public void loginSucceeded(String email) {
        String key = "login:attempts:" + email;
        redisTemplate.opsForValue().set(key, 0);
    }
    public void reinitialiser(String email) {
        String key = "login:attempts:" + email;
        redisTemplate.opsForValue().set(key, 0);
    }

    public void loginFailed(String email) {
        String key = "login:attempts:" + email;
        Integer attempts = redisTemplate.opsForValue().get(key);

        System.out.println("Valeur a incrementer = " + attempts);

        if (attempts == null) {
            redisTemplate.opsForValue().set(key, 1);
            System.out.println("Valeur de la clee = " + redisTemplate.opsForValue().get(key));
        } else {
            System.out.println("Valeur de la clee maintenant= " + redisTemplate.opsForValue().get(key));
            redisTemplate.opsForValue().set(key, attempts + 1);

            if (attempts + 1 >= maxAttempts) {
                System.out.println("HANDLE amzay");
                // handleBlock(email);
            }
        }
    }

    public boolean isBlocked(String email) {
        String key = "login:attempts:" + email;
        Integer attempts = redisTemplate.opsForValue().get(key);
        return attempts != null && attempts >= maxAttempts;
    }

    private void handleBlock(String email) {
        sendBlockEmail(email);
    }

    private void sendBlockEmail(String email) {
        emailService.envoyer_email_reinitialiser_tentative(email,
                "Votre compte a été temporairement bloqué après plusieurs tentatives échouées. Veuillez réinitialiser votre mot de passe ou contacter le support.");
    }

    

    
}
