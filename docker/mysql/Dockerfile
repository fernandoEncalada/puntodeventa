FROM mysql:8.0

COPY init/init.sql /docker-entrypoint-initdb.d/

ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=bd_sistema_facturacion
ENV MYSQL_USER=user
ENV MYSQL_PASSWORD=password