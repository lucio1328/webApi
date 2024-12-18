package com.web.webapi.repository;

import com.web.webapi.models.TokenUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenUserRepository extends JpaRepository<TokenUserEntity, Integer> {
}
