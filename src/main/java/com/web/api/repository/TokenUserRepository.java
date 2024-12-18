package com.web.api.repository;

import com.web.api.models.TokenUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenUserRepository extends JpaRepository<TokenUserEntity, Integer> {
}
