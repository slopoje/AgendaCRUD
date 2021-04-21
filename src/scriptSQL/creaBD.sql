/*En este script se pueden incluir los comandos SQL para crear la base de datos*/

DROP TABLE IF EXISTS CONTACTO;
 
CREATE TABLE CONTACTO(
    NOMBRE   VARCHAR(30) NOT NULL PRIMARY KEY,
    TELEFONO VARCHAR(15) NOT NULL
);
