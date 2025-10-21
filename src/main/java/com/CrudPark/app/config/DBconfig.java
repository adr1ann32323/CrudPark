package com.CrudPark.app.config;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBconfig {

    private static String url;
    private static String user;
    private static String password;

    static {
        try (InputStream input = DBconfig.class.getClassLoader()
                .getResourceAsStream("config/application.properties")) {
            Properties props = new Properties();
            if (input == null) {
                throw new RuntimeException("Archivo application.properties no encontrado");
            }
            props.load(input);

            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");

            Class.forName(props.getProperty("db.driver"));
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error cargando configuración de BD", e);
        }
    }

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}