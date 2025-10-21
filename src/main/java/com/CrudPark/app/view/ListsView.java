package com.CrudPark.app.view;

import com.CrudPark.app.controller.VehicleRegisterController;
import com.CrudPark.app.domain.VehicleRegister;
import com.CrudPark.app.errors.DataAccessException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListsView {
    private final VehicleRegisterController vehicleRegisterController;

    public ListsView(VehicleRegisterController vehicleRegisterController) {
        this.vehicleRegisterController = vehicleRegisterController;
    }

    // Método genérico para mostrar los datos en una tabla
    private static void showVehicleTable(List<VehicleRegister> vehicles, String title) {
        // Definir nombres de columnas
        String[] columnNames = {
                "ID", "Placa", "Operador ID", "Documento Usuario",
                "Entrada", "Salida", "Membresía", "Ticket",
                "Subtotal", "Total"
        };

        // Crear modelo de tabla
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Llenar el modelo con los datos
        for (VehicleRegister v : vehicles) {
            Object[] row = {
                    v.getId(),
                    v.getPlate(),
                    v.getOperatorId(),
                    v.getDocumentUser(),
                    v.getEntryDate(),
                    v.getOutDate() != null ? v.getOutDate() : "—",
                    v.isMembresy() ? "Sí" : "No",
                    v.getTicket(),
                    v.getSubtotal(),
                    v.getTotal()
            };
            model.addRow(row);
        }

        // Crear la tabla con el modelo
        JTable table = new JTable(model);
        table.setEnabled(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(25);

        // Ajuste visual: fuente y colores
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));

        // Añadir la tabla a un JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 300));

        // Mostrar en un JOptionPane
        JOptionPane.showMessageDialog(null, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void listAll(VehicleRegisterController controller) {
        try {
            List<VehicleRegister> vehicles = controller.listAll();
            if (vehicles.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay vehículos registrados.");
                return;
            }
            showVehicleTable(vehicles, "Listado de Vehículos");
        } catch (DataAccessException e) {
            JOptionPane.showMessageDialog(null, "Error al listar vehículos: " + e.getMessage());
        }
    }

    public static void listAllActive(VehicleRegisterController controller) {
        try {
            List<VehicleRegister> vehicles = controller.findAllActive();
            if (vehicles.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay vehículos activos (sin salida).");
                return;
            }
            showVehicleTable(vehicles, "Vehículos sin salida");
        } catch (DataAccessException e) {
            JOptionPane.showMessageDialog(null, "Error al listar vehículos activos: " + e.getMessage());
        }
    }
}
