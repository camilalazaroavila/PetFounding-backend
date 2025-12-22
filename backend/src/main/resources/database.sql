

DROP DATABASE IF EXISTS petfounding_db;
CREATE DATABASE petfounding_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE petfounding_db;


CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    direccion VARCHAR(255),
    fecha_registro DATE NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    INDEX idx_email (email),
    INDEX idx_activo (activo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE shelter (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_refugio VARCHAR(255) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    INDEX idx_nombre_refugio (nombre_refugio)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE pet (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    raza VARCHAR(100),
    edad INTEGER,
    descripcion TEXT,
    fecha_ingreso DATE NOT NULL,
    sexo VARCHAR(20) NOT NULL,
    tamano VARCHAR(20) NOT NULL,
    estado_adopcion VARCHAR(30) NOT NULL DEFAULT 'DISPONIBLE',
    esterilizado BOOLEAN DEFAULT FALSE,
    vacunado BOOLEAN DEFAULT FALSE,
    id_refugio BIGINT,
    FOREIGN KEY (id_refugio) REFERENCES shelter(id) ON DELETE SET NULL,
    INDEX idx_estado_adopcion (estado_adopcion),
    INDEX idx_refugio (id_refugio),
    INDEX idx_nombre (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE adoption_application (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_solicitud DATE NOT NULL,
    motivacion TEXT,
    experiencia_previa TEXT,
    condiciones_vivienda TEXT,
    fecha_rta DATE,
    comentario_refugio TEXT,
    id_adoptante BIGINT,
    id_mascota BIGINT,
    FOREIGN KEY (id_adoptante) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (id_mascota) REFERENCES pet(id) ON DELETE CASCADE,
    INDEX idx_fecha_solicitud (fecha_solicitud),
    INDEX idx_adoptante (id_adoptante),
    INDEX idx_mascota (id_mascota)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE donation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    monto DECIMAL(10, 2) NOT NULL,
    fecha DATE NOT NULL,
    transaccion_id VARCHAR(255),
    metodo_pago VARCHAR(50),
    comentario TEXT,
    id_usuario BIGINT,
    id_refugio BIGINT,
    FOREIGN KEY (id_usuario) REFERENCES user(id) ON DELETE SET NULL,
    FOREIGN KEY (id_refugio) REFERENCES shelter(id) ON DELETE SET NULL,
    INDEX idx_fecha (fecha),
    INDEX idx_usuario (id_usuario),
    INDEX idx_refugio (id_refugio),
    INDEX idx_transaccion (transaccion_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE payment_gateway (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    mercado_pago_token VARCHAR(255),
    payment_id VARCHAR(255),
    id_de VARCHAR(255),
    id_donacion BIGINT,
    FOREIGN KEY (id_donacion) REFERENCES donation(id) ON DELETE CASCADE,
    INDEX idx_payment_id (payment_id),
    INDEX idx_donacion (id_donacion)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE adoption_report (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    periodo DATE NOT NULL,
    mascotas_adoptadas INTEGER DEFAULT 0,
    solicitudes_pendientes INTEGER DEFAULT 0,
    solicitudes_aprobadas INTEGER DEFAULT 0,
    solicitudes_rechazadas INTEGER DEFAULT 0,
    id_refugio BIGINT,
    FOREIGN KEY (id_refugio) REFERENCES shelter(id) ON DELETE CASCADE,
    INDEX idx_periodo (periodo),
    INDEX idx_refugio (id_refugio)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE financial_report (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    total_recaudado DECIMAL(12, 2) DEFAULT 0.00,
    cantidad_donaciones INTEGER DEFAULT 0,
    id_refugio BIGINT,
    FOREIGN KEY (id_refugio) REFERENCES shelter(id) ON DELETE CASCADE,
    INDEX idx_fecha_inicio (fecha_inicio),
    INDEX idx_fecha_fin (fecha_fin),
    INDEX idx_refugio (id_refugio)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


INSERT INTO shelter (nombre_refugio, direccion) VALUES
('Refugio Esperanza', 'Av. Libertador 1234, Buenos Aires'),
('Hogar de Patitas', 'Calle 50 N° 456, La Plata');

INSERT INTO user (email, password, nombre, apellido, telefono, direccion, fecha_registro, activo) VALUES
('juan.perez@example.com', 'password123', 'Juan', 'Pérez', '1234567890', 'Calle Falsa 123', '2024-01-15', TRUE),
('maria.gonzalez@example.com', 'password456', 'María', 'González', '0987654321', 'Av. Siempre Viva 456', '2024-02-20', TRUE);

INSERT INTO pet (nombre, raza, edad, descripcion, fecha_ingreso, sexo, tamano, estado_adopcion, esterilizado, vacunado, id_refugio) VALUES
('Max', 'Labrador', 3, 'Perro muy amigable y juguetón', '2024-01-10', 'MACHO', 'GRANDE', 'DISPONIBLE', TRUE, TRUE, 1),
('Luna', 'Mestiza', 2, 'Gata tranquila y cariñosa', '2024-02-05', 'HEMBRA', 'PEQUENO', 'DISPONIBLE', TRUE, TRUE, 1),
('Rocky', 'Pastor Alemán', 5, 'Perro guardián, muy leal', '2024-03-15', 'MACHO', 'GRANDE', 'DISPONIBLE', FALSE, TRUE, 2);
