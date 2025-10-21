package com.CrudPark.app;

import com.CrudPark.app.config.DBconfig;
import com.CrudPark.app.view.MainView;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando aplicación...");

        try (Connection conn = DBconfig.connect()) {
            System.out.println("Conexión establecida correctamente con la base de datos.");
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
        MainView.start();
        System.out.println("Cerrando aplicación...");
    }
}
