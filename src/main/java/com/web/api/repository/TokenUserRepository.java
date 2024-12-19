package com.web.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.api.models.TokenUserEntity;

public interface TokenUserRepository extends JpaRepository<TokenUserEntity, Integer> {
}
