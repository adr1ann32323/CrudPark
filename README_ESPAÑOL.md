# CrudPark — Sistema de Gestión de Parqueaderos

Aplicación de escritorio desarrollada en **Java (Swing + JDBC)** para la gestión de ingresos, salidas y cobros de vehículos en un parqueadero.
Incluye registro de usuarios, control de membresías, generación de tickets, cálculos automáticos de cobros y registro de operaciones en logs.

---

## Objetivo del Proyecto

Desarrollar un sistema funcional de gestión de parqueadero que permita:
- Registrar la **entrada y salida de vehículos**.
- Generar **tickets de ingreso y salida** con subtotal, total y tiempo de estadía.
- Aplicar **membresías o cortesías automáticas** según el tiempo.
- **Registrar operaciones clave** (ingresos, cobros, cierres de turno) en logs locales.
- Permitir a los operadores **cerrar su turno** con un resumen total de ingresos.

El objetivo general es aplicar los principios de arquitectura en capas (DAO → Service → Controller → View), persistencia con JDBC y manejo de errores controlado.

---

## Instrucciones de Instalación y Ejecución

### 1. Clonar el repositorio
```bash
git clone [https://github.com/adr1ann32323/CrudPark.git](https://github.com/tuusuario/CrudPark.git)
cd CrudPark
```
### 2. Compilar el proyecto
Asegúrate de tener instalado Java 17+ y Maven.

```bash

mvn clean install
```
### 3. Ejecutar la aplicación
Desde la carpeta raíz del proyecto:

```bash

mvn exec:java -Dexec.mainClass="com.CrudPark.app.Main"
```
O bien, puedes ejecutarlo desde tu IDE (IntelliJ / Eclipse / NetBeans).

Configuración de la Base de Datos (PostgreSQL)
### 1. Crear la base de datos
```SQL
CREATE DATABASE crudpark_db;
```
### 2. Crear el usuario y otorgar permisos
```SQL

CREATE USER cruduser WITH PASSWORD 'crudpass';
GRANT ALL PRIVILEGES ON DATABASE crudpark_db TO cruduser;
```
### 3. Configurar la conexión
En el archivo DBconfig.java, ajusta las credenciales:

```Java

private static final String URL = "jdbc:postgresql://localhost:5432/crudpark_db";
private static final String USER = "cruduser";
private static final String PASSWORD = "crudpass";
```
### 4. Crear las tablas base
Ejecuta el script schema.sql incluido en el proyecto **./docs/schema.sql**, que contiene las tablas:

vehicles

vehicle_registers

operators

users

logs

## Flujo General de Uso
Inicio del programa: El operador ingresa al sistema con su usuario.

Registro de entrada: Se captura la placa, se guarda la hora de ingreso y se imprime el ticket de entrada.

Registro de salida: Se calcula el subtotal, total y duración; se imprime el ticket de salida.

Logs automáticos: Cada acción (entrada, cobro, cierre de turno) se guarda en el archivo logs/app.log.

Cierre de turno: El operador ejecuta la opción de cierre, el sistema registra el total del turno y resetea los valores.

## Créditos del Equipo
Equipo: Crudzaso Integrantes:

Adrián Arboleda. — Líder técnico, desarrollador principal del sistema.

Registro del equipo: teams.crudzaso.com/equipos/crudpark

## Tecnologías Utilizadas
Java 17

Swing

JDBC

PostgreSQL

Maven

Log System (archivo local)