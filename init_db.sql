-- Script d'initialisation de la base de données gestusers
-- À exécuter une seule fois : mysql -u root -p < init_db.sql

CREATE DATABASE IF NOT EXISTS gestusers
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE gestusers;

CREATE TABLE IF NOT EXISTS utilisateurs (
    id       INT AUTO_INCREMENT PRIMARY KEY,
    nom      VARCHAR(100) NOT NULL,
    prenom   VARCHAR(100) NOT NULL,
    login    VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Données de test (optionnel)
INSERT IGNORE INTO utilisateurs (nom, prenom, login, password) VALUES
    ('Dupont',  'Alice',  'alice',  'alice123'),
    ('Martin',  'Bob',    'bob',    'bob456'),
    ('Bernard', 'Claire', 'claire', 'claire789');
