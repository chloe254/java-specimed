-- Schéma de la base pj_java, reconstitué à partir des requêtes des classes DAO
-- (le script de création d'origine n'est pas dans le projet).

CREATE DATABASE IF NOT EXISTS pj_java;
USE pj_java;

CREATE TABLE admin (
  id           INT AUTO_INCREMENT PRIMARY KEY,
  nom          VARCHAR(100),
  email        VARCHAR(150) NOT NULL UNIQUE,
  mot_de_passe VARCHAR(255) NOT NULL
);

CREATE TABLE patient (
  id           INT AUTO_INCREMENT PRIMARY KEY,
  nom          VARCHAR(100),
  email        VARCHAR(150) NOT NULL UNIQUE,
  mot_de_passe VARCHAR(255) NOT NULL,
  type_patient ENUM('NOUVEAU', 'ANCIEN') DEFAULT 'NOUVEAU'
);

CREATE TABLE specialiste (
  id               INT AUTO_INCREMENT PRIMARY KEY,
  nom              VARCHAR(100),
  email            VARCHAR(150) NOT NULL UNIQUE,
  motDePasse       VARCHAR(255) NOT NULL,
  specialite       VARCHAR(100),
  numeroLicence    VARCHAR(50),
  anneesExperience INT
);

CREATE TABLE agenda (
  id             INT AUTO_INCREMENT PRIMARY KEY,
  id_specialiste INT NOT NULL,
  date_heure     DATETIME NOT NULL,
  disponible     BOOLEAN DEFAULT TRUE,
  FOREIGN KEY (id_specialiste) REFERENCES specialiste(id) ON DELETE CASCADE
);

CREATE TABLE rendezvous (
  id             INT AUTO_INCREMENT PRIMARY KEY,
  dateHeure      DATETIME NOT NULL,
  lieu           VARCHAR(150),
  id_patient     INT NOT NULL,
  id_specialiste INT NOT NULL,
  statut         VARCHAR(20) DEFAULT 'Programmé',
  note           VARCHAR(255),
  FOREIGN KEY (id_patient)     REFERENCES patient(id)     ON DELETE CASCADE,
  FOREIGN KEY (id_specialiste) REFERENCES specialiste(id) ON DELETE CASCADE
);
