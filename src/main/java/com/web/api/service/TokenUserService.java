package com.web.api.service;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.api.models.Config;
import com.web.api.models.TokenUserEntity;
import com.web.api.repository.ConfigRepository;
import com.web.api.repository.TokenUserRepository;

@Service
public class TokenUserService {

    @Autowired
    TokenUserRepository tokenUserRepository;

    @Autowired
    ConfigRepository configRepository;

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
    
    // fonction a appeller a chaque requete
    public boolean verifierExpirationToken(int idUtilisateur) {
        // recuperer token ce cet utilisateur
        Optional<TokenUserEntity> tokenUserEntityOpt = this.tokenUserRepository.findByIdUtilisateur(idUtilisateur);

        // recuperer la duree de session
        Config config = this.configRepository.findById(1).get();

        if (tokenUserEntityOpt.isPresent()) {
            TokenUserEntity token = tokenUserEntityOpt.get();
            Timestamp creationToken = token.getDateCreation();

            Timestamp now = new Timestamp(System.currentTimeMillis());

            long intervalInMinutes = ((now.getTime() - creationToken.getTime()) / 1000) / 60;

            return intervalInMinutes > config.getDuree();

        }
        return false;
    }


    
    
}