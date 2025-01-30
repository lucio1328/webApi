package com.web.api.service;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.api.models.ValidationConnectionEntity;
import com.web.api.repository.ValidationConnectionRepository;

@Service
public class PinService {

    @Autowired
    private ValidationConnectionRepository validationConnectionRepository;

    public void verifierPin(int pin, int idUtilisateur) throws Exception {
        ValidationConnectionEntity validationConnectionEntity = this.validationConnectionRepository
                .findTopByPinAndIdUtilisateur(String.valueOf(pin), idUtilisateur);
        System.out.println("verification PIN");
        if (validationConnectionEntity != null) {
            ValidationConnectionEntity v = validationConnectionEntity;

            // Obtenir le timestamp actuel
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

            // Obtenir le timestamp existant
            Timestamp existingTimestamp = v.getDaty();

            // Calculer l'intervalle en secondes
            long intervalInSeconds = (currentTimestamp.getTime() - existingTimestamp.getTime()) / 1000;

            System.out.println("INTERVALE EN SECONDES : " + intervalInSeconds);

            // Si la condition est respectée, écraser
            if (intervalInSeconds > 90) {
                throw new Exception("Le code PIN n'est plus valide, veuillez vous reconnecter");

            } else {
                // verifier ici si c'est correcte maintenant
                v.setDaty(currentTimestamp);
                v.setIdStatut(2);
                this.validationConnectionRepository.save(v);
            }
        } else {
            throw new Exception("Le code PIN que vous avez saisi n'est pas correcte");
        }
    }

}
