package com.web.api.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "pre_inscription", schema = "public", catalog = "api")
public class PreInscriptionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_pre_inscription", nullable = false)
    private int idPreInscription;
    @Basic
    @Column(name = "nom", nullable = false, length = 50)
    private String nom;
    @Basic
    @Column(name = "email", nullable = false, length = 50)
    private String email;
    @Basic
    @Column(name = "mot_de_passe", nullable = false, length = 255)
    private String motDePasse;
    @Basic
    @Column(name = "id_statut", nullable = false)
    private int idStatut;

    public int getIdPreInscription() {
        return idPreInscription;
    }

    public void setIdPreInscription(int idPreInscription) {
        this.idPreInscription = idPreInscription;
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

    public int getIdStatut() {
        return idStatut;
    }

    public void setIdStatut(int idStatut) {
        this.idStatut = idStatut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreInscriptionEntity that = (PreInscriptionEntity) o;
        return idPreInscription == that.idPreInscription && idStatut == that.idStatut && Objects.equals(nom, that.nom) && Objects.equals(email, that.email) && Objects.equals(motDePasse, that.motDePasse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPreInscription, nom, email, motDePasse, idStatut);
    }
}
