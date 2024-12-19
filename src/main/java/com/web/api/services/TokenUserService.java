package com.web.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.api.models.TokenUserEntity;
import com.web.api.repository.TokenUserRepository;

@Service
public class TokenUserService {

    @Autowired
    TokenUserRepository tokenUserRepository;

    public TokenUserEntity saveToken(TokenUserEntity token) {
        // Recherche de l'entité existante
        Optional<TokenUserEntity> tokenUserOpt = this.tokenUserRepository.findByIdUtilisateur(token.getIdUtilisateur());
    
        TokenUserEntity tokenUser;
    
        // Si l'entité existe, on met à jour les champs et on persiste
        if (tokenUserOpt.isPresent()) {
            tokenUser = tokenUserOpt.get();
            tokenUser.setDateCreation(token.getDateCreation());
            tokenUser.setToken(token.getToken());
            return tokenUserRepository.save(tokenUser);
        } else {
            // Sinon, on crée une nouvelle entité et on la persiste
            System.out.println("token date creation = " + token.getDateCreation());
            System.out.println("token id_utilisateur = " + token.getIdUtilisateur());
            System.out.println("token token = " + token.getToken());
            tokenUser = tokenUserRepository.save(token);
        }

        return null;
    }
}