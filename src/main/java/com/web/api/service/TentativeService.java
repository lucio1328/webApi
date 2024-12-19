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

    public void loginFailed(String email) {
        String key = "login:attempts:" + email;
        Integer attempts = redisTemplate.opsForValue().get(key);

        if (attempts == null) {
            redisTemplate.opsForValue().set(key, 1);
        }
        else {
            redisTemplate.opsForValue().increment(key);

            if (attempts + 1 >= maxAttempts) {
                handleBlock(email);
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
