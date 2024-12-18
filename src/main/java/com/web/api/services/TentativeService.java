package com.web.api.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TentativeService {

    private final int maxAttempts = 3;
    private final Map<String, Integer> attemptsCache = new ConcurrentHashMap<>();

    public void loginSucceeded(String email) {
        attemptsCache.remove(email);
    }

    public void loginFailed(String email) {
        attemptsCache.put(email, attemptsCache.getOrDefault(email, 0) + 1);
    }

    public boolean isBlocked(String email) {
        return attemptsCache.getOrDefault(email, 0) >= maxAttempts;
    }
}
