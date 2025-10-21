package com.CrudPark.app.util;

import com.CrudPark.app.domain.Operator;
import com.CrudPark.app.domain.VehicleRegister;

public class TicketGenerator {

    public static String generateTicket(VehicleRegister register, Operator operator) {
        String ticketNumber = String.format("%06d", register.getId());
        String plate = register.getPlate();
        String type = register.isMembresy() ? "Membresía" : "Invitado";
        // 🔹 Formateador de fecha y hora (solo hasta minutos)
        java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        String entryDate = (register.getEntryDate() != null)
                ? fmt.format(register.getEntryDate().toLocalDateTime())
                : "N/A";

        String operatorName = operator != null ? operator.getName() : "N/A";
        String qrData = "TICKET:" + ticketNumber + "|PLATE:" + plate + "|DATE:" + register.getEntryDate().getTime();

        StringBuilder sb = new StringBuilder();
        sb.append("==============================\n");
        sb.append("     CrudPark - Crudzaso\n");
        sb.append("==============================\n");
        sb.append("Ticket #: ").append(ticketNumber).append("\n");
        sb.append("Placa: ").append(plate).append("\n");
        sb.append("Tipo: ").append(type).append("\n");
        sb.append("Ingreso: ").append(entryDate).append("\n");
        sb.append("Operador: ").append(operatorName).append("\n");
        sb.append("------------------------------\n");
        sb.append("QR: ").append(qrData).append("\n");
        sb.append("------------------------------\n");
        sb.append("Gracias por su visita.\n");
        sb.append("==============================\n");

        //ticket en la impresora
        PrinterUtil.printTicket(sb.toString());

        return sb.toString();
    }

    public static String generateExitTicket(VehicleRegister reg) {
        StringBuilder sb = new StringBuilder();
        String type = reg.isMembresy() ? "Mensualidad" : "Invitado";

        // 🔹 Formateadores
        java.text.DecimalFormat moneyFormat = new java.text.DecimalFormat("#,###.00");
        java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        sb.append("==============================\n");
        sb.append("     CrudPark - Crudzaso\n");
        sb.append("==============================\n");
        sb.append("Ticket #: ").append(String.format("%06d", reg.getId())).append("\n");
        sb.append("Placa: ").append(reg.getPlate()).append("\n");
        sb.append("Tipo: ").append(type).append("\n");
        sb.append("Ingreso: ").append(fmt.format(reg.getEntryDate().toLocalDateTime())).append("\n");
        sb.append("Salida: ").append(fmt.format(reg.getOutDate().toLocalDateTime())).append("\n");

        // 🔹 Duración
        long minutes = java.time.Duration.between(
                reg.getEntryDate().toLocalDateTime(),
                reg.getOutDate().toLocalDateTime()
        ).toMinutes();

        long hours = minutes / 60;
        long remaining = minutes % 60;
        String durationText = (hours > 0)
                ? String.format("%dh %02dmin", hours, remaining)
                : remaining + " min";

        sb.append("Tiempo: ").append(durationText).append("\n");
        sb.append("------------------------------\n");

        // 🔹 Subtotal y Total
        sb.append("Subtotal: $ ").append(moneyFormat.format(reg.getSubtotal())).append("\n");

        if (reg.isMembresy()) {
            sb.append("Mensualidad activa - Sin cobro\n");
        } else if (reg.getTotal() == 0) {
            sb.append("Cortesía 30min - Sin cobro\n");
        } else {
            sb.append("Total: $ ").append(moneyFormat.format(reg.getTotal())).append("\n");
        }

        sb.append("------------------------------\n");
        sb.append("Gracias por su visita.\n");
        sb.append("==============================\n\n");

        PrinterUtil.printTicket(sb.toString());
        return sb.toString();
    }



}
