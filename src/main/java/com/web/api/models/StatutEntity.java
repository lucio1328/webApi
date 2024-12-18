package com.web.api.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "statut", schema = "public", catalog = "api")
public class StatutEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_statut", nullable = false)
    private int idStatut;
    @Basic
    @Column(name = "libelle", nullable = false, length = 50)
    private String libelle;

    public int getIdStatut() {
        return idStatut;
    }

    public void setIdStatut(int idStatut) {
        this.idStatut = idStatut;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatutEntity that = (StatutEntity) o;
        return idStatut == that.idStatut && Objects.equals(libelle, that.libelle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idStatut, libelle);
    }
}
