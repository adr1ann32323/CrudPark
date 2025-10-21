-- =====================================
-- Base de datos: crudpark_db
-- Script de creación de tablas PostgreSQL
-- =====================================

-- 1. Tabla de operadores
CREATE TABLE operators (
    id SERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    document VARCHAR(200) NOT NULL,
    password VARCHAR(200) NOT NULL,
    email VARCHAR(200) UNIQUE NOT NULL,
    is_active BOOLEAN DEFAULT TRUE
);

-- 2. Tabla de usuarios
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    document VARCHAR(200) UNIQUE NOT NULL,
    email VARCHAR(200) UNIQUE NOT NULL,
    date_membership_start TIMESTAMP,
    date_membership_expire TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);

-- 3. Tabla de relación usuarios-vehículos
CREATE TABLE users_vehicles (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    plate VARCHAR(200) NOT NULL,
    type_vehicle VARCHAR(200) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- 4. Tabla de tarifas
CREATE TABLE rates (
    id SERIAL PRIMARY KEY,
    amount_hour INTEGER NOT NULL,
    type_vehicle VARCHAR(200) NOT NULL,
    amount_fraction INTEGER NOT NULL
);

-- 5. Tabla de registros de vehículos (entradas/salidas)
CREATE TABLE registers_vehicles (
    id SERIAL PRIMARY KEY,
    plate VARCHAR(30) NOT NULL,
    operator_id INTEGER NOT NULL,
    entry_date TIMESTAMPTZ DEFAULT NOW(),
    out_date TIMESTAMPTZ,
    membresy BOOLEAN DEFAULT FALSE,
    ticket TEXT,
    document_user VARCHAR(200),
    subtotal INTEGER,
    total INTEGER,
    FOREIGN KEY (operator_id) REFERENCES operators (id) ON DELETE SET NULL
);



-- =====================================
-- DATOS DE EJEMPLO
-- =====================================

-- Operadores
INSERT INTO operators (name, document, password, email, is_active)
VALUES
('Adrian Arboleda', '1001234567', 'adrian123', 'Adrian@gmail.com', TRUE),
('Alesis', '1009876543', 'abcd', 'alesis@parking.com', FALSE),
('Isa Pulgarin', '1005678912', 'isa', 'isa@gmail.com', TRUE);

-- Usuarios
INSERT INTO users (name, document, email, date_membership_start, date_membership_expire, is_active)
VALUES
('Alesis Calle', '1100456789', 'alesisCalle@gmail.com', '2025-01-01', '2025-12-31', TRUE),
('Andrés Pérez', '1100678945', 'andres@gmail.com', '2025-02-01', '2025-08-01', FALSE),
('Camila Duarte', '1100789456', 'camila@gmail.com', '2025-03-15', '2026-03-15', TRUE);

-- Relación usuarios-vehículos
INSERT INTO users_vehicles (user_id, plate, type_vehicle)
VALUES
(1, 'RJP78H', 'Moto'),
(2, 'XYZ987', 'Moto'),
(3, 'LMN456', 'Carro');

-- Tarifas
INSERT INTO rates (amount_hour, type_vehicle, amount_fraction)
VALUES
(3000, 'Carro', 1000),
(1500, 'Moto', 500);

-- Registros de vehículos
INSERT INTO registers_vehicles (plate, operator_id, entry_date, out_date, membresy, ticket, document_user, subtotal, total)
VALUES
('RJP78F', 1, '2025-10-19 08:00:00', '2025-10-19 10:00:00', TRUE, 'TCK-001', '1100456789', 5000, 6000),
('XYZ987', 2, '2025-10-19 09:00:00', NULL, FALSE, 'TCK-002', '1100678945', NULL, NULL),
('LMN456', 3, '2025-10-19 07:30:00', '2025-10-19 09:00:00', TRUE, 'TCK-003', '1100789456', 3000, 4500);


select * from registers_vehicles;

