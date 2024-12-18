package com.web.api.services;

import com.web.api.models.UtilisateurEntity;
import com.web.api.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public List<UtilisateurEntity> get_all() {
        return utilisateurRepository.findAll();
    }

    public UtilisateurEntity get_by_id(Integer id_utilisateur) {
        return utilisateurRepository.findById(id_utilisateur).orElse(new UtilisateurEntity());
    }

    public void save(UtilisateurEntity utilisateurEntity) {
        utilisateurRepository.save(utilisateurEntity);
    }

    public void delete_by_id(Integer id_utilisateur) {
        utilisateurRepository.deleteById(id_utilisateur);
    }

}
