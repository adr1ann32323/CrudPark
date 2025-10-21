package com.CrudPark.app.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogUtil {

    private static final String LOG_FILE = "crudpark_logs.txt"; // 📁 Archivo en la raíz del proyecto

    // 🔹 Formato de fecha y hora
    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Registra un evento en el archivo de logs.
     * @param operatorName Nombre del operador que ejecuta la acción
     * @param action Tipo de acción (Ingreso, Salida, Cierre de turno, etc.)
     * @param message Descripción adicional o detalle
     */
    public static void log(String operatorName, String action, String message) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             PrintWriter pw = new PrintWriter(fw)) {

            String timestamp = LocalDateTime.now().format(fmt);
            pw.printf("[%s] [%s] [%s] %s%n", timestamp, operatorName, action.toUpperCase(), message);

        } catch (IOException e) {
            System.out.println("Error al escribir en el log: " + e.getMessage());
        }
    }
}
