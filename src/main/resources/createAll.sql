Drop SCHEMA IF EXISTS bichomon_go_jdbc;
CREATE SCHEMA bichomon_go_jdbc;

USE bichomon_go_jdbc;

CREATE TABLE specie(
    id int NOT NULL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL UNIQUE,
    peso int NOT NULL,
    altura int NOT NULL,
    tipo_Bicho VARCHAR(255),
    cantidad_Bichos int NOT NULL
)
ENGINE = InnoDB;

Drop SCHEMA IF EXISTS epers_bichomon_hibernate;
CREATE SCHEMA epers_bichomon_hibernate;