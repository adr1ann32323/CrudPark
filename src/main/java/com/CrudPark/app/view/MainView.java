package com.CrudPark.app.view;

import com.CrudPark.app.dao.jdbc.RateDao;
import com.CrudPark.app.domain.Operator;
import com.CrudPark.app.controller.VehicleRegisterController;
import com.CrudPark.app.service.VehicleRegisterService;
import com.CrudPark.app.dao.jdbc.VehicleRegisterDaoJdbc;

import javax.swing.*;

public class MainView {

    public static void start() {
        Operator operador = LoginView.showLogin();

        if (operador == null) {
            JOptionPane.showMessageDialog(null, "❌ Sesión no iniciada. Cerrando aplicación.");
            return;
        }

        // 🔧 Crear dependencias una sola vez
        VehicleRegisterDaoJdbc dao = new VehicleRegisterDaoJdbc();
        RateDao rateDao = new RateDao();
        VehicleRegisterService service = new VehicleRegisterService(dao, rateDao);
        VehicleRegisterController controller = new VehicleRegisterController(service);

        while (true) {
            String[] opciones = {
                    "Registrar ingreso de vehículo",
                    "Registrar salida",
                    "Ver historial",
                    "Ver vehículos sin salida",
                    "Salir"
            };

            int opcion = JOptionPane.showOptionDialog(
                    null,
                    "Seleccione una opción:",
                    "Menú principal - Operador: " + operador.getName(),
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            switch (opcion) {
                case 0 -> RegisterEntryView.show(operador, controller);
                case 1 -> RegisterExitView.show(operador, controller);
                case 2 -> ListsView.listAll(controller);
                case 3 -> ListsView.listAllActive(controller);
                case 4, JOptionPane.CLOSED_OPTION -> {
                    JOptionPane.showMessageDialog(null, "👋 Hasta luego, " + operador.getName());
                    return;
                }
                default -> JOptionPane.showMessageDialog(null, "⚠️ Opción inválida.");
            }
        }
    }
}
