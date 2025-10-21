package com.CrudPark.app.view;

import com.CrudPark.app.controller.VehicleRegisterController;
import com.CrudPark.app.domain.Operator;

import javax.swing.*;
import java.util.Map;

public class RegisterExitView {
    public static void show(Operator operator, VehicleRegisterController controller) {
        String plate = JOptionPane.showInputDialog("Ingrese la placa del vehículo:");

        if (plate == null || plate.isBlank()) return;

        Map<String, Object> response = controller.registerVehicleExit(plate.toUpperCase(), operator.getId());

        int status = (int) response.get("status");
        if (status == 200) {
            JOptionPane.showMessageDialog(null, "Salida registrada correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "Error: " + response.get("error"),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}