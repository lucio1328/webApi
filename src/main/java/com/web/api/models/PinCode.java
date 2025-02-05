package com.web.api.models;

import java.time.Instant;

public class PinCode {
    private final String code;
    private final Instant createdAt;

    public PinCode(String code) {
        this.code = code;
        this.createdAt = Instant.now();
    }

    public String getCode() {
        return code;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public boolean isValid(long durationInSeconds) {
        Instant now = Instant.now();
        return now.isBefore(createdAt.plusSeconds(durationInSeconds));
    }
}
