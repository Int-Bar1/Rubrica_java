CREATE DATABASE IF NOT EXISTS rubrica CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE rubrica;

CREATE TABLE IF NOT EXISTS persone (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(80) NOT NULL,
  cognome VARCHAR(80) NOT NULL,
  indirizzo VARCHAR(255),
  telefono VARCHAR(40) UNIQUE,
  eta INT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS utenti (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(64) UNIQUE,
  password_hash VARCHAR(128)
);

INSERT IGNORE INTO utenti(username, password_hash)
VALUES ('admin', HEX(SHA2('admin', 256)));
