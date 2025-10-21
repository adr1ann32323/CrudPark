package com.CrudPark.app.view;

import com.CrudPark.app.controller.VehicleRegisterController;
import com.CrudPark.app.domain.Operator;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class RegisterEntryView {

    public static void show(Operator operador, VehicleRegisterController controller) {
        String plate = JOptionPane.showInputDialog("Ingrese la placa del vehículo:");
        if (plate == null || plate.isBlank()) {
            JOptionPane.showMessageDialog(null, "Placa no puede estar vacía.");
            return;
        }

        Map<String, String> data = new HashMap<>();
        data.put("plate", plate.toUpperCase());
        data.put("operatorId", String.valueOf(operador.getId()));

        Map<String, Object> response = controller.registerVehicleEntry(data, operador);

        if ((int) response.get("status") == 200) {
            JOptionPane.showMessageDialog(null, "Ingreso registrado correctamente:\n" + response.get("data"));
        } else {
            JOptionPane.showMessageDialog(null, "Error: " + response.get("error"));
        }
    }
}
