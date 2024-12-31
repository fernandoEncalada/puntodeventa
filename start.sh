#!/bin/bash

# Iniciar MySQL
service mysql start

# Esperar a que MySQL esté listo
while ! mysqladmin ping -h"localhost" --silent; do
    sleep 1
done

# Inicializar la base de datos
mysql -u root -p$MYSQL_ROOT_PASSWORD < /docker-entrypoint-initdb.d/init.sql

# Iniciar la aplicación Spring Boot
java -jar app.jar 