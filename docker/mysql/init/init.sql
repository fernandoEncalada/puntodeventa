-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS bd_sistema_facturacion;
USE bd_sistema_facturacion;

-- Crear tabla clasificacion
CREATE TABLE clasificacion (
    id_clasificacion BIGINT PRIMARY KEY AUTO_INCREMENT,
    grupo VARCHAR(100) NOT NULL
);

-- Crear tabla proveedores
CREATE TABLE proveedores (
    id_proveedor BIGINT PRIMARY KEY AUTO_INCREMENT,
    ruc VARCHAR(20) NOT NULL,
    telefono VARCHAR(15),
    pais VARCHAR(50),
    correo VARCHAR(100),
    moneda VARCHAR(10)
);

-- Crear tabla producto
CREATE TABLE producto (
    id_producto BIGINT PRIMARY KEY AUTO_INCREMENT,
    stock INTEGER NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    unidad VARCHAR(20),
    id_clasificacion BIGINT,
    id_proveedor BIGINT,
    iva BOOLEAN,
    FOREIGN KEY (id_clasificacion) REFERENCES clasificacion(id_clasificacion),
    FOREIGN KEY (id_proveedor) REFERENCES proveedores(id_proveedor)
);

-- Crear tabla persona
CREATE TABLE persona (
    id_persona BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    dni VARCHAR(20),
    celular VARCHAR(15),
    correo VARCHAR(100)
);

-- Crear tabla rol
CREATE TABLE rol (
    id_rol BIGINT PRIMARY KEY AUTO_INCREMENT,
    rol VARCHAR(50) NOT NULL,
    estado BOOLEAN DEFAULT TRUE
);

-- Crear tabla usuario
CREATE TABLE usuario (
    id_usuario BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_persona BIGINT,
    user VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    FOREIGN KEY (id_persona) REFERENCES persona(id_persona)
);

-- Crear tabla usuario_rol (nueva tabla intermedia)
CREATE TABLE usuario_rol (
    id_usuario BIGINT,
    id_rol BIGINT,
    PRIMARY KEY (id_usuario, id_rol),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    FOREIGN KEY (id_rol) REFERENCES rol(id_rol)
);

-- Crear tabla tipo_pago
CREATE TABLE tipo_pago (
    id_tipo_pago BIGINT PRIMARY KEY AUTO_INCREMENT,
    tipo VARCHAR(50) NOT NULL,
    descripcion VARCHAR(200)
);

-- Crear tabla factura
CREATE TABLE factura (
    id_factura BIGINT PRIMARY KEY AUTO_INCREMENT,
    ruc VARCHAR(20),
    id_persona BIGINT,
    id_tipo_pago BIGINT,
    fecha DATE NOT NULL,
    descuento DECIMAL(10,2),
    total DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_persona) REFERENCES persona(id_persona),
    FOREIGN KEY (id_tipo_pago) REFERENCES tipo_pago(id_tipo_pago)
);

-- Crear tabla item_factura
CREATE TABLE item_factura (
    id_item_factura BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_factura BIGINT,
    id_producto BIGINT,
    cantidad INTEGER NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_factura) REFERENCES factura(id_factura),
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

-- Crear tabla competencia
CREATE TABLE competencia (
    id_competencia BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(200)
);

-- Crear tabla rol_competencia
CREATE TABLE rol_competencia (
    id_rol BIGINT,
    id_competencia BIGINT,
    PRIMARY KEY (id_rol, id_competencia),
    FOREIGN KEY (id_rol) REFERENCES rol(id_rol),
    FOREIGN KEY (id_competencia) REFERENCES competencia(id_competencia)
);

-- Insertar datos de ejemplo en clasificacion
INSERT INTO clasificacion (grupo) VALUES 
('Abarrotes'),
('Bebidas'),
('Limpieza'),
('Snacks'),
('Lácteos');

-- Insertar datos de ejemplo en proveedores
INSERT INTO proveedores (ruc, telefono, pais, correo, moneda) VALUES 
('20100190125', '999888777', 'Perú', 'proveedor1@email.com', 'PEN'),
('20304050607', '998877665', 'Chile', 'proveedor2@email.com', 'CLP'),
('20708090100', '997766554', 'Colombia', 'proveedor3@email.com', 'COP');

-- Insertar datos de ejemplo en producto
INSERT INTO producto (stock, precio_unitario, unidad, id_clasificacion, id_proveedor, iva) VALUES 
(100, 2.50, 'UND', 1, 1, true),
(50, 1.80, 'UND', 2, 1, true),
(75, 3.20, 'UND', 3, 2, true),
(200, 0.50, 'UND', 4, 3, true);

-- Insertar datos de ejemplo en persona
INSERT INTO persona (nombre, apellido, dni, celular, correo) VALUES 
('Juan', 'Pérez', '12345678', '999111222', 'juan@email.com'),
('María', 'García', '87654321', '999333444', 'maria@email.com'),
('Pedro', 'López', '45678912', '999555666', 'pedro@email.com');

-- Insertar datos de ejemplo en rol
INSERT INTO rol (rol, estado) VALUES 
('Administrador', true),
('Vendedor', true),
('Almacenero', true);

-- Insertar datos de ejemplo en usuario
INSERT INTO usuario (id_persona, user, password) VALUES 
(1, 'jperez', 'jperez123'),
(2, 'mgarcia', 'garcia123');

-- Insertar datos de ejemplo en usuario_rol
INSERT INTO usuario_rol (id_usuario, id_rol) VALUES 
(1, 1),  -- Juan Pérez es Administrador
(1, 2),  -- Juan Pérez también es Vendedor
(2, 2);  -- María García es Vendedor

-- Insertar datos de ejemplo en tipo_pago
INSERT INTO tipo_pago (tipo, descripcion) VALUES 
('Efectivo', 'Pago en efectivo'),
('Tarjeta', 'Pago con tarjeta de crédito/débito'),
('Transferencia', 'Transferencia bancaria');

-- Insertar datos de ejemplo en competencia
INSERT INTO competencia (nombre, descripcion) VALUES 
('Ventas', 'Gestión de ventas'),
('Inventario', 'Control de inventario'),
('Reportes', 'Generación de reportes');

-- Insertar datos de ejemplo en rol_competencia
INSERT INTO rol_competencia (id_rol, id_competencia) VALUES 
(1, 1), (1, 2), (1, 3),  -- Administrador tiene todas las competencias
(2, 1),                   -- Vendedor solo tiene competencia de ventas
(3, 2);                   -- Almacenero solo tiene competencia de inventario

-- Insertar datos de ejemplo en factura
INSERT INTO factura (ruc, id_persona, id_tipo_pago, fecha, descuento, total) VALUES 
('20123456789', 1, 1, '2024-01-15', 0.00, 100.00),
('20987654321', 2, 2, '2024-01-16', 5.00, 95.00);

-- Insertar datos de ejemplo en item_factura
INSERT INTO item_factura (id_factura, id_producto, cantidad, precio, subtotal) VALUES 
(1, 1, 2, 2.50, 5.00),
(1, 2, 3, 1.80, 5.40),
(2, 3, 1, 3.20, 3.20);