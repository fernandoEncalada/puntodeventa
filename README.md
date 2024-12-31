# Sistema de Punto de Venta

## Descripción
Sistema de punto de venta desarrollado con Spring Boot que permite gestionar facturas, productos, proveedores, clientes y facturación. El sistema está diseñado con una arquitectura REST y utiliza MySQL como base de datos.

## Características Principales
- Gestión de productos y stock
- Gestión de proveedores
- Facturación
- Control de usuarios y roles
- Gestión de clientes
- Sistema de clasificación de productos
- Control de pagos

## Tecnologías Utilizadas

### Backend
- **Spring Boot 3.4.1**: Framework principal para el desarrollo de la aplicación
- **Spring Data JPA**: Para la persistencia y manipulación de datos
- **MySQL**: Sistema de gestión de base de datos
- **Lombok**: Reduce el código boilerplate en las clases Java
- **SpringDoc OpenAPI**: Documentación automática de la API REST
- **Jakarta Validation**: Para validación de datos
- **Maven**: Gestión de dependencias y construcción del proyecto

### Contenedores
- **Docker**: Containerización de la aplicación
- **Docker Compose**: Orquestación de contenedores

## Estructura del Proyecto
El proyecto sigue una arquitectura en capas:
- Controllers: Manejo de endpoints REST
- Services: Lógica de negocio
- Repositories: Acceso a datos
- Models: Entidades de la base de datos
- DTOs: Objetos de transferencia de datos

## Requisitos Previos
- Docker
- Docker Compose

## Configuración y Despliegue

### 1. Clonar el Repositorio
```bash
git clone https://github.com/fernandoEncalada/puntodeventa.git
```

### 2. Iniciar el contenedor de MySQL
```bash
docker-compose up -d
```

Este comando:
- Descarga las imágenes necesarias
- Crea y configura la base de datos MySQL
- Inicia la aplicación Spring Boot
- Configura la red entre contenedores

### 3. Verificar el Despliegue
- API REST: http://localhost:9898
- Swagger UI: http://localhost:9898/swagger-ui.html
- Base de datos MySQL: localhost:3306

## Estructura de la Base de Datos
El sistema utiliza varias tablas interrelacionadas:
- Productos
- Proveedores
- Facturas
- Clientes (Personas)
- Usuarios
- Roles
- Clasificaciones

## API Endpoints Principales
- `/producto`: Gestión de productos
- `/proveedor`: Gestión de proveedores
- `/factura`: Gestión de facturas
- `/persona`: Gestión de clientes
- `/usuario`: Gestión de usuarios

## Variables de Entorno
Configurables en el docker-compose.yml:
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `SERVER_PORT`