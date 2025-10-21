# CrudPark — Parking Management System

Desktop application developed in **Java (Swing + JDBC)** for managing vehicle entries, exits, and payments in a parking facility.
It includes user registration, membership control, ticket generation, automatic billing, and operation logging.

---

## Project Objective

Develop a functional parking management system that allows:
- Registering **vehicle entries and exits**.
- Generating **entry and exit tickets** with subtotal, total, and parking duration.
- Applying **automatic memberships or courtesy time** based on stay duration.
- **Logging key operations** (entries, payments, shift closures) to local files.
- Allowing operators to **close their shift** with a total income summary.

The main goal is to apply layered architecture principles (DAO → Service → Controller → View), persistence with JDBC, and controlled error handling.

---

## Installation and Execution Instructions

### 1. Clone the repository
```bash
git clone https://github.com/adr1ann32323/CrudPark.git
cd CrudPark
```
### 2. Build the project
Make sure you have Java 17+ and Maven installed.

```bash
mvn clean install
```
### 3. Run the application
From the project root folder:

```bash
mvn exec:java -Dexec.mainClass="com.CrudPark.app.Main"
```
Or you can run it from your IDE (IntelliJ / Eclipse / NetBeans).

Database Configuration (PostgreSQL)
### 1. Create the database
```SQL
CREATE DATABASE crudpark_db;
```
### 2. Create the user and grant permissions
```SQL
CREATE USER cruduser WITH PASSWORD 'crudpass';
GRANT ALL PRIVILEGES ON DATABASE crudpark_db TO cruduser;
```
### 3. Configure the connection
In the DBconfig.java file, adjust the credentials:

```Java
private static final String URL = "jdbc:postgresql://localhost:5432/crudpark_db";
private static final String USER = "cruduser";
private static final String PASSWORD = "crudpass";
```
### 4. Create the base tables
Run the schema.sql script included in the project **./docs/schema.sql**, which contains the tables:

vehicles

vehicle_registers

operators

users

logs

## General Usage Flow
Program start: The operator logs into the system.

Entry registration: The license plate is captured, the entry time is saved, and the entry ticket is printed.

Exit registration: The subtotal, total, and duration are calculated; the exit ticket is printed.

Automatic logs: Each action (entry, payment, shift closure) is saved in the logs/app.log file.

Shift closure: The operator runs the closing option, the system records the total income for the shift, and resets values.

## Team Credits
Team: Crudzaso Members:

Adrián Arboleda — Technical leader, main system developer.

Team registration: teams.crudzaso.com/equipos/crudpark

## Technologies Used
Java 17

Swing

JDBC

PostgreSQL

Maven

Log System (local file)# CrudPark
