package com.web.api.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "utilisateur", schema = "public", catalog = "api")
public class UtilisateurEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_utilisateur", nullable = false)
    private int idUtilisateur;
    @Basic
    @Column(name = "nom", nullable = false, length = 50)
    private String nom;
    @Basic
    @Column(name = "email", nullable = false, length = 50)
    private String email;
    @Basic
    @Column(name = "mot_de_passe", nullable = false, length = 255)
    private String motDePasse;

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UtilisateurEntity that = (UtilisateurEntity) o;
        return idUtilisateur == that.idUtilisateur && Objects.equals(nom, that.nom) && Objects.equals(email, that.email) && Objects.equals(motDePasse, that.motDePasse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUtilisateur, nom, email, motDePasse);
    }
}
