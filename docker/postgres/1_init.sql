CREATE TABLE utilisateur(
   Id_utilisateur SERIAL,
   nom VARCHAR(50)  NOT NULL,
   email VARCHAR(50)  NOT NULL,
   mot_de_passe VARCHAR(255)  NOT NULL,
   PRIMARY KEY(Id_utilisateur)
);

CREATE TABLE statut(
   Id_statut SERIAL,
   libelle VARCHAR(50)  NOT NULL,
   PRIMARY KEY(Id_statut),
   UNIQUE(libelle)
);

CREATE TABLE validation_connection(
   Id_validation_connection SERIAL,
   pin VARCHAR(50)  NOT NULL,
   daty TIMESTAMP NOT NULL,
   Id_statut INTEGER NOT NULL,
   Id_utilisateur INTEGER NOT NULL,
   PRIMARY KEY(Id_validation_connection),
   FOREIGN KEY(Id_statut) REFERENCES statut(Id_statut),
   FOREIGN KEY(Id_utilisateur) REFERENCES utilisateur(Id_utilisateur) on delete cascade
);


CREATE TABLE token_user(
   Id_token_user SERIAL,
   date_creation TIMESTAMP NOT NULL,
   Id_utilisateur INTEGER NOT NULL,
   PRIMARY KEY(Id_token_user),
   UNIQUE(Id_utilisateur),
   FOREIGN KEY(Id_utilisateur) REFERENCES utilisateur(Id_utilisateur) ON DELETE CASCADE
);

ALTER TABLE token_user ADD COLUMN token VARCHAR(255);
ALTER TABLE token_user ADD COLUMN refresh_token VARCHAR(255);


CREATE TABLE pre_inscription(
   Id_pre_inscription SERIAL,
   nom VARCHAR(50)  NOT NULL,
   email VARCHAR(50)  NOT NULL,
   mot_de_passe VARCHAR(255)  NOT NULL,
   Id_statut INTEGER NOT NULL,
   PRIMARY KEY(Id_pre_inscription),
   FOREIGN KEY(Id_statut) REFERENCES statut(Id_statut)
);


CREATE TABLE config(
   id_config SERIAL,
   duree INTEGER 
);


