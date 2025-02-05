package com.web.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.api.models.Config;

public interface ConfigRepository extends JpaRepository<Config, Integer>{
    
}
