create database PRUEBA

USE PRUEBA

CREATE TABLE PERSONA(
Cedula numeric(10) not null,
Nombre varchar(20) not null,
Musica varchar(20) not null
)

CREATE TABLE MUSICA(
Musica varchar(20) not null
)

INSERT INTO MUSICA VALUES ("REGGETON")
INSERT INTO MUSICA VALUES ("SALSA")
INSERT INTO MUSICA VALUES ("CUMBIA")
INSERT INTO MUSICA VALUES ("BACHATA")