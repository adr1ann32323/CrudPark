package com.CrudPark.app.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class PrinterUtil {

    // 📍 Ruta donde Ubuntu monta las impresoras térmicas USB
    private static final String PRINTER_PATH = "/dev/usb/lp1";

    /**
     * Envía un ticket en formato de texto a la impresora térmica.
     * @param text Contenido del ticket generado
     */
    public static void printTicket(String text) {
        File printer = new File(PRINTER_PATH);

        // 🔍 Verificar si la impresora está conectada
        if (!printer.exists()) {
            System.out.println("⚠️ No se encontró la impresora en " + PRINTER_PATH);
            System.out.println("💡 Verifica conexión o permisos con: sudo chmod 666 " + PRINTER_PATH);
            return;
        }

        try (OutputStream out = new FileOutputStream(PRINTER_PATH)) {
            // 🧩 Comandos básicos ESC/POS
            byte[] init = {0x1B, 0x40};           // Inicializa impresora
            byte[] center = {0x1B, 0x61, 0x01};   // Centra texto
            byte[] left = {0x1B, 0x61, 0x00};     // Alinea a la izquierda
            byte[] cut = {0x1D, 0x56, 0x00};      // Cortar papel

            // 🖨️ Inicializar y centrar
            out.write(init);
            out.write(center);

            // ✉️ Enviar texto del ticket
            out.write(text.getBytes(StandardCharsets.UTF_8));

            // 🧾 Espacio antes del corte
            out.write("\n\n\n".getBytes(StandardCharsets.UTF_8));

            // 🧾 Cortar papel
            out.write(left);
            out.write(cut);
            out.flush();

            System.out.println("🖨️ Ticket enviado a la impresora con éxito.");

        } catch (Exception e) {
            System.out.println("⚠️ Error al imprimir ticket: " + e.getMessage());
        }
    }
}
